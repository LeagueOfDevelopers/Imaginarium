using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using System;
using System.Collections;

public class MenuLogic : MonoBehaviour
{
    public GameObject SizeOfLobbyObject;

    private enum Stage { Idle, JoinLobby, SearchingGame, SetName }
    private int countOfPlayers = 0;
    private int sizeOfLobby = 6;
    private Stage currentStage = Stage.Idle;
    ServerDriver driver = new ServerDriver();
    Prefs prefs = new Prefs();
    string token = "";
    float calldown = 1;
    float basic_calldown = 1;
    private bool requesting = false;
    private Dropdown SizeOfLobbyDropdown;

    void Start()
    {
        driver.TestRequest();
        SizeOfLobbyDropdown = SizeOfLobbyObject.GetComponent<Dropdown>();
    }

    // Update is called once per frame
    void Update()
    {
        try
        {
            if (calldown <= 0)
            {
                calldown = basic_calldown;
                if (requesting)
                {
                    if (driver.isDone())
                    {
                        switch (currentStage)
                        {

                            case Stage.JoinLobby:
                                Debug.Log("JoinLobby");
                                prefs.setToken(driver.getResponse()["token"].ToString());
                                TryToken();
                                break;

                            case Stage.SearchingGame:
                                Debug.Log("SearchingGame" + token);
                                Debug.Log(driver.text().ToString());
                                if (!driver.getResponse()["token"].ToString().Equals(token)
                                    || driver.getResponse()["status"].Equals("ERROR"))
                                {
                                    JoinLobby();
                                    break;
                                }
                                else
                                {
                                    countOfPlayers = Convert.ToInt32(driver.getResponse()["countOfPlayers"]);
                                }


                                if (driver.getResponse()["status"].Equals("READY"))
                                {
                                    driver.GetGameScore();
                                    currentStage = Stage.SetName;
                                    break;
                                }

                                SearchingGame();

                                break;

                            case Stage.SetName:
                                string[] tokens = new string[prefs.getSize()];
                                for (int i = 0; i < prefs.getSize(); i++)
                                    tokens[i] = driver.getResponse()["player#" + i];
                                PlayerNamer namer = new PlayerNamer();
                                namer.SetNames(tokens);
                                GameStart();
                                break;
                        }
                    }
                }
            }
            else
            {
                calldown -= Time.deltaTime;
            }
        }
        catch (System.Exception e)
        {
            GameObject errorMessage = new GameObject("errorMessage");
            errorMessage.AddComponent<ErrorHandler>();
            Destroy(errorMessage, 3);
            Debug.LogError(e.Message);

            MenuIdle();
            FindObjectOfType<Canvas>().GetComponent<MenuListener>().SearchButtonClickListener();
        }
    }


    public void LobbyJoining()
    {
        requesting = true;
        TryToken();
    }

    public void MenuIdle()
    {
        countOfPlayers = 0;
        requesting = false;
        currentStage = Stage.Idle;
    }

    private void TryToken()
    {
        token = prefs.getToken();
        Debug.Log("TryToken" + token);
        if (token.Equals(string.Empty))
            JoinLobby();
        else
            SearchingGame();
    }

    private void SearchingGame()
    {
        driver.UpdateLobby();
        currentStage = Stage.SearchingGame;
    }

    private void JoinLobby()
    {
        ChangeSizeOfLobby(SizeOfLobbyDropdown.value);
        prefs.setSize(sizeOfLobby);
        driver.JoinLobby(sizeOfLobby);
        currentStage = Stage.JoinLobby;
    }

    private void GameStart() {
        prefs.setToken(token);
        SceneManager.LoadScene("Game");  
    }
    public int GetCountOfPlayers() {
        return countOfPlayers;
    }

    public void ChangeSizeOfLobby(int value) {
        switch (value)
        {
            case 0: sizeOfLobby = 4;
                break;
            case 1: sizeOfLobby = 5;
                break;
            case 2: sizeOfLobby = 6;
                break;
            case 3: sizeOfLobby = 7;
                break;
            default: sizeOfLobby = 6;
                break;

        }
        Debug.Log(sizeOfLobby);
    }
}