using UnityEngine;
using System.Collections;

public class Tests : MonoBehaviour {
    ServerDriver driver;
    // Use this for initialization
    void Start () {
	}
	
	// Update is called once per frame
	void Update () {

    }

    /*
    public void AddMOOOOOOREPlayers() {
        driver = new ServerDriver();
        for (int i = 0; i < 6; i++)
        {
            driver.JoinLobby();
            Debug.Log("Player №" + (i + 1) + " Connected");
        }
    }
    */
    public void resetToken() {
        Prefs prefs = new Prefs();
        prefs.setToken("");
    }
}
