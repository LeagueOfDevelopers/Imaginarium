using UnityEngine;
using System.Collections;

public class MenuLogic : MonoBehaviour {


    private enum Stage {Idle, WaitingForToken, SerachingGame }
    private Stage currentStage = Stage.Idle;
    ServerDriver driver = new ServerDriver();
    Prefs prefs = new Prefs();

	// Update is called once per frame
	void Update () {
        switch (currentStage)
        {
            case Stage.WaitingForToken:
                if (driver.isDone())
                {
                    string token = driver.getResponse().GetField("token").ToString();
                    Debug.Log(token);
                    currentStage = Stage.SerachingGame;
                }
                break;
            case Stage.SerachingGame:
                
                break;
        }
	}

    public void StartSearch() {
        driver.JoinLobby();
        currentStage = Stage.WaitingForToken;
    }

    public void SetIdle() {
        currentStage = Stage.Idle;
    }
}
