using UnityEngine;
using UnityEngine.SceneManagement;
using System.Collections;

public class MenuLogic : MonoBehaviour {

    private enum Stage {Idle, TryOldToken, WaitingForToken, SearchingGame }
    private Stage currentStage = Stage.Idle;
    ServerDriver driver = new ServerDriver();
    Prefs prefs = new Prefs();
    string token = "5b952355-1cdf-42eb-aa7a-961021b195c8";

    void Start() {
        driver.TestRequest();
    }

	// Update is called once per frame
	void Update () {
        if (driver.isDone())
        {
            switch (currentStage)
            {
                case Stage.TryOldToken:
                    if (driver.getResponse().GetField("status").ToString() == "ERROR")
                        WaitingForToken();
                    else
                        SearchingGame();
                    break;

                case Stage.WaitingForToken:
                        token = driver.getResponse().GetField("token").ToString();
                        Debug.Log(token);
                        SearchingGame();
                    break;
                case Stage.SearchingGame:
                    Debug.Log(driver.text());          
                        if (driver.getResponse().GetField("token").ToString() != token)
                        {
                            currentStage = Stage.TryOldToken;
                            return;
                        }
                    if (driver.getResponse().GetField("status").ToString() == "READY")
                        StartGame();
                    break;
                default:
                    break;
            }
        }
	}

    private void WaitingForToken() {
        driver.JoinLobby();
        currentStage = Stage.WaitingForToken;
    }

    private void SearchingGame() {
        driver.UpdateLobby(token);
        currentStage = Stage.SearchingGame;
    }

    private void StartGame() {
        prefs.setToken(token);
        SceneManager.LoadScene(1);
    }

    public void SetIdle() {
        currentStage = Stage.Idle;
    }

    public void SetTryOldToken() {
        token = prefs.getToken();
        if (token == string.Empty)
            WaitingForToken();
        else
        {
            driver.UpdateLobby(token);
            currentStage = Stage.TryOldToken;
        }
    }

}
