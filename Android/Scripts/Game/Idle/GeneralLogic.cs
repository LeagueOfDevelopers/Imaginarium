using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using System.Collections.Generic;
using System;
using System.Collections;

public class GeneralLogic : MonoBehaviour {

    public enum State {Idle, First, Second, Third, Fourth };
    public State curentState = State.Idle;
    private MenuOpener windows;


    private int chosenCardForStage = 0;
    private CardsJson[] cardsForFourth;
    private int countOfCardsForStage = 6;

    private Prefs prefs = new Prefs();

    public GameObject GameField;
    public GameObject CardPanel;
    public GameObject ReadyPlayersPanel;
    public GameObject chosenCardObject;

    void Start () {
        StartCoroutine(IdleCoroutine());
        if(prefs.getCards()!=null)
            CardPanel.GetComponent<CardNamer>().SetCards(prefs.getCards());
        windows = GameField.GetComponent<MenuOpener>();
        //windows.OpenScoreboard();
	}
	

	void Update () {

        if (Input.GetKeyDown(KeyCode.Escape))
            ReturnToMenu();

        CardPanel.transform.FindChild("Player").GetComponent<PlayerSetter>().SetPlayer(new Player(prefs.getToken(), prefs.getScore()),0);

        switch (curentState)
            {
                case State.First:
                    FirstStage(chosenCardForStage);
                    break;
                case State.Second:
                    SecondStage(chosenCardForStage);
                    break;
                case State.Third:
                    ThirdStage(chosenCardForStage);
                    break;
                case State.Fourth:
                    FourthStage(chosenCardForStage);
                    break;
            }

        
	}
    
    //Обновление данных на игровом столе
    void FirstStage(int chosenCard)
    {
        GameObject curentScreen = GameField.transform.FindChild("FirstStageScreen").gameObject;
        curentScreen.SetActive(true);
        curentScreen.transform.FindChild("CardOnField").GetComponent<CardSpriteHandler>().setCard(prefs.getCards().cards[chosenCard]);
    }

    void SecondStage(int chosenCard)
    {
        GameObject curentScreen = GameField.transform.FindChild("SecondStageScreen").gameObject;
        curentScreen.SetActive(true);
        curentScreen.transform.FindChild("Text").GetComponent<Text>().text = prefs.getText();
        curentScreen.transform.FindChild("CardOnField").GetComponent<CardSpriteHandler>().setCard(prefs.getCards().cards[chosenCard]);
    }

    void ThirdStage(int chosenCard)
    {
        GameObject curentScreen = GameField.transform.FindChild("ThirdStageScreen").gameObject;
        curentScreen.SetActive(true);
        curentScreen.transform.FindChild("Text").GetComponent<Text>().text = prefs.getText();
        curentScreen.transform.FindChild("CardOnField").GetComponent<CardSpriteHandler>().setCard(prefs.getChosenCards().cards[chosenCard]);
    }

    void FourthStage(int chosenCard)
    {
        GameObject curentScreen = GameField.transform.FindChild("FourthStageScreen").gameObject;
        curentScreen.SetActive(true);
        curentScreen.transform.FindChild("Text").GetComponent<Text>().text = prefs.getText();
        GameObject cardOnField = curentScreen.transform.FindChild("CardOnField").gameObject;
        cardOnField.GetComponent<CardSpriteHandler>().setCard(cardsForFourth[chosenCard].id);
        cardsForFourth[chosenCard].owner.OpenPlayerInfo();
        cardOnField.transform.Find("CardOwner").GetComponent<PlayerSetter>().SetPlayer(cardsForFourth[chosenCard].owner, 0) ;
        foreach (Player player in cardsForFourth[chosenCard].voters)
            player.OpenPlayerInfo();
        cardOnField.transform.FindChild("VotedPlayers").GetComponent<PlayersPanel>().SetPlayerList(cardsForFourth[chosenCard].voters);
    }

    void ReturnToMenu()
    {
        StopAllCoroutines();
        SceneManager.LoadScene(0);
    }


    void EndStage()
    {
        curentState = State.Idle;
        chosenCardForStage = 0;
        //CardPanel.GetComponent<CardNamer>().SetCards(prefs.getCards());
        StartCoroutine(IdleCoroutine());
    }

    //Корутин запросов на сервер
    IEnumerator IdleCoroutine()
    {
        ServerDriver driver = new ServerDriver();
        yield return driver.GetRoomStatus();
        try
        {

            Debug.Log(driver.text());
            GetGameStatusResponce responce = driver.GetResponce<GetGameStatusResponce>();

            
                switch (responce.stage)
                {
                    case 1:
                        prefs.setStage(1);
                        if (responce.currentHead.token != prefs.getToken())
                            prefs.setIsHead(0);
                        else
                            prefs.setIsHead(1);

                        prefs.SetAmountOfCards(responce.amountOfCards);

                        prefs.SetHeadToken(responce.currentHead.token);
                        /*Debug.Log("HEAD TOKEN" + responce.currentHead.token);
                        Debug.Log("HEAD NAME" + prefs.GetHeadName());
                        Debug.Log("HEAD TOKEN IN PREFS" + prefs.GetHeadToken());
                        Debug.Log("HEAD NAME BY TOKEN" + prefs.GetPlayerName(responce.currentHead.token)); */

                        CardsJson[] cards = responce.cards;
                        CardArray arrayOfCardsInHand = new CardArray(cards);


                        prefs.setCards(arrayOfCardsInHand);
                        CardPanel.GetComponent<CardNamer>().SetCards(arrayOfCardsInHand);
                        if (prefs.getIsHead() && !responce.isDone())
                        {
                            GameObject curentScreen = GameField.transform.FindChild("FirstStageScreen").gameObject;
                            curentScreen.transform.FindChild("InputField").GetComponent<InputField>().text = String.Empty;
                            ReadyPlayersPanel.SetActive(false);
                            chosenCardObject.SetActive(false);
                            curentState = State.First;
                        }
                        else
                        {
                            ReadyPlayersPanel.SetActive(true);
                            chosenCardObject.SetActive(true);
                            if (responce.donePlayers != null)
                                ReadyPlayersPanel.GetComponent<PlayersPanel>().SetPlayerList(responce.donePlayers);
                            StartCoroutine(RequestCalldown());
                        }

                        break;
                    case 2:
                        prefs.setStage(2);
                        prefs.setText(responce.text);
                        if (!prefs.getIsHead() && !responce.isDone())
                        {
                            ReadyPlayersPanel.SetActive(false);
                            chosenCardObject.SetActive(false);
                        curentState = State.Second;
                        }
                        else
                        {
                            ReadyPlayersPanel.SetActive(true);
                            chosenCardObject.SetActive(true);
                            if (responce.donePlayers != null)
                                ReadyPlayersPanel.GetComponent<PlayersPanel>().SetPlayerList(responce.donePlayers);
                            StartCoroutine(RequestCalldown());
                        }
                    break;
                    case 3:
                        prefs.setStage(3);
                        CardArray chosenCards = new CardArray(responce.cards);
                        prefs.setChosenCards(chosenCards);
                        if (!prefs.getIsHead() && !responce.isDone())
                        {
                            countOfCardsForStage = responce.cards.Length;
                            ReadyPlayersPanel.SetActive(false);
                            chosenCardObject.SetActive(false);
                            curentState = State.Third;
                        }
                        else
                        {
                            chosenCardObject.SetActive(true);
                            ReadyPlayersPanel.SetActive(true);
                            if (responce.donePlayers != null)
                                ReadyPlayersPanel.GetComponent<PlayersPanel>().SetPlayerList(responce.donePlayers);
                            StartCoroutine(RequestCalldown());
                        }
                    break;
                    case 4:
                        prefs.setStage(4);
                        cardsForFourth = responce.cards;

                        if (!responce.isDone())
                        {
                            countOfCardsForStage = responce.cards.Length;
                            ReadyPlayersPanel.SetActive(false);
                            curentState = State.Fourth;
                        }
                        else
                        {
                            ReadyPlayersPanel.SetActive(true);
                            if (responce.donePlayers != null)
                                ReadyPlayersPanel.GetComponent<PlayersPanel>().SetPlayerList(responce.donePlayers);
                            StartCoroutine(RequestCalldown());
                        }
                    break;
                    case 0:
                        prefs.SetEndReason(responce.reason);
                        prefs.SetPlayerLeaveToken(responce.leftPlayer.token);
                        SceneManager.LoadSceneAsync(2);
                        break;

                }
            
        }
        catch (System.Exception e)
        {
            Debug.Log(e.Message);
            ReturnToMenu();

        }
    }

    //Задержка между запросами на сервер
    IEnumerator RequestCalldown()
    {
        yield return new WaitForSeconds(1);
        StartCoroutine(IdleCoroutine());
    }

    //Корутин на отправку хода
    IEnumerator StageSendRequest(SetGameStatusRequest data, GameObject curentScreen)
    {
        ServerDriver driver = new ServerDriver();
        Debug.Log(data);
        yield return driver.UpdateRoomStatus(data);
        try
        {
            driver.GetResponce<SetGameStatusResponce>();
            curentScreen.SetActive(false);
            EndStage();
        }
        catch (System.Exception e)
        {
            Debug.Log(e.Message);
            ReturnToMenu();
        }


    }



    //Обработки клила на ОК
    public void FirstStageOK()
    {
        GameObject curentScreen = GameField.transform.FindChild("FirstStageScreen").gameObject;
        InputField field = curentScreen.transform.FindChild("InputField").GetComponent<InputField>();
        if (field.text.Length != 0)
        {
            CardPanel.GetComponent<CardNamer>().ChosenCardTogle(chosenCardForStage);

            
            CardsJson cardNum = prefs.getCards().cards[chosenCardForStage];
            prefs.setText(field.text);
            prefs.setOwnCard(cardNum);
            SetGameStatusRequest data = new SetGameStatusRequest(cardNum, field.text);
            
            chosenCardObject.SetActive(true);
            chosenCardObject.GetComponent<CardSpriteHandler>().setCard(prefs.getCards().cards[chosenCardForStage]);

            StartCoroutine(StageSendRequest(data, curentScreen));
        }
    }

    public void SecondStageOK()
    {
        GameObject curentScreen = GameField.transform.FindChild("SecondStageScreen").gameObject;

        CardPanel.GetComponent<CardNamer>().ChosenCardTogle(chosenCardForStage);

        CardsJson cardNum = prefs.getCards().cards[chosenCardForStage];
        prefs.setOwnCard(cardNum);
        SetGameStatusRequest data = new SetGameStatusRequest(cardNum);

        chosenCardObject.SetActive(true);
        chosenCardObject.GetComponent<CardSpriteHandler>().setCard(prefs.getCards().cards[chosenCardForStage]);

        StartCoroutine(StageSendRequest(data, curentScreen));

    }

    public void ThirdStageOK()
    {
        GameObject curentScreen = GameField.transform.FindChild("ThirdStageScreen").gameObject;

        CardsJson cardNum = prefs.getChosenCards().cards[chosenCardForStage];

        SetGameStatusRequest data = new SetGameStatusRequest(cardNum);

        chosenCardObject.SetActive(true);
        chosenCardObject.GetComponent<CardSpriteHandler>().setCard(prefs.getChosenCards().cards[chosenCardForStage]);

        StartCoroutine(StageSendRequest(data, curentScreen));

    }

    public void FourthStageOK()
    {
        StartCoroutine(DeltaScoreUpdate());
        GameObject curentScreen = GameField.transform.FindChild("FourthStageScreen").gameObject;
        windows.OpenScoreboard();
        SetGameStatusRequest data = new SetGameStatusRequest();
        StartCoroutine(StageSendRequest(data, curentScreen));

    }


    //Методы для изменения данных
    public void SetScreenCard(int card)
    {
        if  (curentState == State.First || curentState == State.Second)
        {
            chosenCardForStage = card;
        }
    }

    public void PlusScreenCard()
    {
        if (chosenCardForStage < countOfCardsForStage - 1)
            chosenCardForStage++;
    }

    public void MinusScreenCard()
    {
        if (chosenCardForStage >0)
            chosenCardForStage--;
    }

    //Метод получения текущего состояний игры
    public State GetState()
    {
        return curentState;
    }

    //Метод выхода из игры
    public void LeaveGame()
    {
        StartCoroutine(Leave());
    }

    IEnumerator Leave()
    {
        ServerDriver driver = new ServerDriver();

        yield return driver.LeaveGame();

        curentState = State.Idle;
        
    }

    IEnumerator DeltaScoreUpdate()
    {
        ServerDriver driver = new ServerDriver();

        yield return driver.GetGameScore();

        GetGameScoreResponce responce = driver.GetResponce<GetGameScoreResponce>();
        Prefs prefs = new Prefs();
        foreach (Player player in responce.players)
        {
            prefs.SetPlayerNewScore(player.token, player.score);
        }

    }
}
