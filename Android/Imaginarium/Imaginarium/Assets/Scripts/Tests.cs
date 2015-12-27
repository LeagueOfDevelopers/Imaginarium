using UnityEngine;
using System.Collections;

public class Tests : MonoBehaviour {
    ServerDriver driver;
    // Use this for initialization
    void Start () {
        TestGetRoomStatus();
	}
	
	// Update is called once per frame
	void Update () {
        Debug.Log(driver.ToString());
    }


    public void AddMOOOOOOREPlayers() {
        driver = new ServerDriver();
        for (int i = 0; i < 6; i++)
        {
            driver.JoinLobby();
            Debug.Log("Player №" + (i + 1) + " Connected");
        }
    }

    public void TestGetRoomStatus() {
        Prefs prefs = new Prefs();
        driver.GetRoomStatus(prefs.getToken());
    }
}
