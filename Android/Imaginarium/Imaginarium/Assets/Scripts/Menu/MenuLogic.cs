using UnityEngine;
using UnityEngine.SceneManagement;
using System.Collections;

public class MenuLogic : MonoBehaviour
{

    private enum Stage { Idle, JoinLobby, SearchingGame }
    private Stage currentStage = Stage.Idle;
    ServerDriver driver = new ServerDriver();
    Prefs prefs = new Prefs();
    string token = "";
    private bool requesting = false;

    void Start()
    {
        driver.TestRequest();
    }

    // Update is called once per frame
    void Update()
    {
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


                        if (driver.getResponse()["status"].Equals("READY_FOR_GAME"))
                        {
                            GameStart();
                            break;
                        }

                        SearchingGame();

                        break;
                }
            }
        }
    }

    public void LobbyJoining()
    {
        requesting = true;
        TryToken();
    }

    public void MenuIdle()
    {
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
        driver.UpdateLobby(token);
        currentStage = Stage.SearchingGame;
    }

    private void JoinLobby()
    {
        driver.JoinLobby();
        currentStage = Stage.JoinLobby;
    }

    private void GameStart() {
        prefs.setToken(token);
        SceneManager.LoadScene("Game");  
    }
}