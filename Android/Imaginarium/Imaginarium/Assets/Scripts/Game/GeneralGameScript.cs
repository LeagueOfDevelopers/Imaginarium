using UnityEngine;
using UnityEngine.SceneManagement;
using System;
using System.Collections.Generic;

public class GeneralGameScript : MonoBehaviour {
    
    public float cooldownResetValue = 5;

    private int countOfDonePlayers = 0;
    float cooldown = 0;
    bool isRequestHasReaden = true; 
    ServerDriver driver = new ServerDriver();
    Prefs prefs = new Prefs();
    // Use this for initialization
	void Start () {
        driver.TestRequest();

	}
	
	// Update is called once per frame
	void Update () {
        try
        {
            if (Input.GetKeyDown(KeyCode.Escape))
                Exit();
            if (driver.isDone())
            {
                if (isRequestHasReaden)
                {
                    //Отправка реквеста
                    cooldown -= Time.deltaTime;
                    if (cooldown < 0)
                    {
                        cooldown = cooldownResetValue;
                        isRequestHasReaden = false;
                        driver.GetRoomStatus(); //Для тестов вырубаем стандартную отправку.
                        //driver.TestRequest(GetComponent<UnitTestButton>().StartTest()); //Тестовый запрос
                    }
                }
                else
                {
                    //Обработка реквеста
                    isRequestHasReaden = true;
                    redirectHandler(driver.getResponse());

                }
            }
        }
        catch (System.Exception e)
        {
            GameObject errorMessage = new GameObject("errorMessage");
            errorMessage.AddComponent<ErrorHandler>();
            Destroy(errorMessage, 3);
            Debug.LogError(e.Message);
        }
	}


    private void redirectHandler(Dictionary<string, string> response)
    {
        
            int stage = Convert.ToInt32(response["stage"]);
            switch (stage)
            {
                case 1:
                    
                    prefs.setStage(1);
                    if (response["currentHead"] != prefs.getToken())
                        prefs.setIsHead(0);
                    else
                        prefs.setIsHead(1);

                prefs.SetHeadToken(response["currentHead"]);
                    int[] cards = new int[prefs.getSize()];
                    for (int i = 0; i < prefs.getSize(); i++)
                        cards[i] = Convert.ToInt32(response[("card#" + i)]);
                    prefs.setCards(cards);


                    if (prefs.getIsHead() && response["isDone"] == "false")
                        SceneManager.LoadSceneAsync("FirstStage");
                    break;
                case 2:
                    prefs.setStage(2);
                    prefs.setText(response["text"]);
                    if (!prefs.getHead() && response["isDone"] == "false")
                        SceneManager.LoadSceneAsync("SecondStage");
                    break;
                case 3:
                    prefs.setStage(3);
                    int[] chosenCards = new int[prefs.getSize()-1];
                    for (int i = 0; i < (prefs.getSize()-1); i++)
                        chosenCards[i] = Convert.ToInt32(response[("card#" + i)]);
                    prefs.setChosenCards(chosenCards);
                    if (!prefs.getHead() && response["isDone"] == "false")
                        SceneManager.LoadSceneAsync("ThirdStage");
                    break;
                case 4:
                    prefs.setStage(4);
                    int[] VoteForCards = new int[prefs.getSize()];
                    int[] CardsForVote = new int[prefs.getSize()];
                    string[] PlayersForVote = new string[prefs.getSize()];
                for (int i = 0; i < prefs.getSize(); i++)
                {
                    PlayersForVote[i] = response[("player#" + i)];
                    VoteForCards[i] = Convert.ToInt32(response[("vote#" + i)]);
                    CardsForVote[i] = Convert.ToInt32(response[("card#" + i)]);
                }

                    prefs.setPlayersForVote(PlayersForVote);
                    prefs.setVoteForCards(VoteForCards);
                    prefs.setCardsForVote(CardsForVote);

                if (response["isDone"] == "false")
                    SceneManager.LoadSceneAsync("FourthStage");

                    break;
                case 0:
                    prefs.deleteAll();
                    SceneManager.LoadSceneAsync("Menu");
                    break;
                }

            
                countOfDonePlayers = Convert.ToInt32(response["countOfPlayers"]);   //Изменение кол-ва игроков для лэйбла.
            

        }



    public int getCountOfPlayers() {
        return countOfDonePlayers;
    }


    public void Exit() {
        Debug.Log("exit");
        SceneManager.LoadSceneAsync("Menu");
    }

    public void Scoreboard()
    {
        Debug.Log("ScoreBoardClick!");
        SceneManager.LoadSceneAsync("Scoreboard");
    }
}

