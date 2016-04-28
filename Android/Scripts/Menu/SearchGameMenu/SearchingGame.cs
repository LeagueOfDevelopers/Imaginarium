using UnityEngine;
using UnityEngine.SceneManagement;
using System.Collections.Generic;
using System;
using System.Collections;

public class SearchingGame : MonoBehaviour {

    public GameObject MainMenu;

    public GameObject PlayersList;

    private Prefs prefs = new Prefs();
	
	void Start () {
        if(prefs.getToken() == string.Empty)
            StartCoroutine(JoinLobby());
        else
            StartCoroutine(UpdateLobby());
    }
	
	void Update () {
        if (Input.GetKeyDown(KeyCode.Escape))
            ReturnToMenu();

    }

    public void ReturnToMenu()
    {
        StopAllCoroutines();
        StartCoroutine(LeaveLobby());
    }

    IEnumerator UpdateLobby() {
        ServerDriver driver = new ServerDriver();

        yield return driver.UpdateLobby();
        Debug.Log(driver.text());
        try
        {
            UpdateLobbyResponce responce = driver.GetResponce<UpdateLobbyResponce>();

            switch (responce.status)
            {
                case "ERROR":
                    StartCoroutine(JoinLobby());
                    break;
                case "WAITING":
                    
                    //Именование игроков
                    
                    PlayerNamer namer = new PlayerNamer();
                    namer.SetNames(responce.players);

                    //Заполнение таблицы игроков
                    foreach (Player player in responce.players)
                        player.OpenPlayerInfo();


                    PlayersList.GetComponent<PlayersPanel>().SetPlayerList(responce.players);
                    StartCoroutine(RequestCalldown());
                    break;
                case "READY":
                    StartCoroutine(PlayerNamer());
                    break;
            }
        }
        catch (System.Exception e)
        {
            Debug.Log(e.Message);
            ReturnToMenu();

        }

    }

    IEnumerator JoinLobby()
    {
        ServerDriver driver = new ServerDriver();

        yield return driver.JoinLobby(prefs.getSize());
        Debug.Log(driver.text());
        try
        {
            JoinLobbyResponce responce = driver.GetResponce<JoinLobbyResponce>();

            prefs.setToken(responce.token);
            Debug.Log(responce.players[0]);
            //Именование игроков
            PlayerNamer namer = new PlayerNamer();
            namer.SetNames(responce.players);

            //Заполнение таблицы игроков
            foreach (Player player in responce.players)
                player.OpenPlayerInfo();

            PlayersList.GetComponent<PlayersPanel>().SetPlayerList(responce.players);

            StartCoroutine(RequestCalldown());
        }
        catch (System.Exception e)
        {
            Debug.Log(e.Message);
            ReturnToMenu();

        }
    }

    IEnumerator RequestCalldown()
    {
        yield return new WaitForSeconds(1);
        StartCoroutine(UpdateLobby());
    }

    IEnumerator PlayerNamer()
    {
        ServerDriver driver = new ServerDriver();
        yield return driver.GetGameScore();

        GetGameScoreResponce responce = driver.GetResponce<GetGameScoreResponce>();
        Debug.Log(responce.players.Length);
        //Именование игроков
        PlayerNamer namer = new PlayerNamer();
        namer.SetNames(responce.players);

        foreach (Player player in responce.players)
        {
            player.OpenPlayerInfo();
        }
        SceneManager.LoadScene(1);
    }

    IEnumerator LeaveLobby()
    {
        ServerDriver driver = new ServerDriver();
        yield return driver.LeaveLobby();

        MainMenu.SetActive(true);
        gameObject.SetActive(false);
    }

}
