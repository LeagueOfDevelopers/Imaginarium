using UnityEngine;
using System.Collections;

public class Tests : MonoBehaviour {
    ServerDriver driver;
    string token = "5b1f3c20-0cc1-470d-a130-64e14bd2280d";
    // Use this for initialization
    void Start () {
        driver = new ServerDriver();
        Debug.Log("123");
        driver.UpdateLobby(token);
       
	}
	
	// Update is called once per frame
	void Update () {
         Debug.Log(driver.text());
    }
}
