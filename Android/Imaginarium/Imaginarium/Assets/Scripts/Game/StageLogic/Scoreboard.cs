using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using System.Collections;

public class Scoreboard : MonoBehaviour {

    private ServerDriver driver = new ServerDriver();
    private bool rendered = false;
    private Prefs prefs = new Prefs();

    public GameObject textfield;

	// Use this for initialization
	void Start () {
        driver.GetGameScore();
	}
	

	void Update () {
        if (!rendered && driver.isDone())
        {
            string output = "";
            for (int i = 0; i < prefs.getSize(); i++)
            {
                string name = prefs.GetPlayerName(driver.getResponse()["player#" + i]);
                string score = driver.getResponse()["score#" + i];
                output += name + " - " + score + "\n";
            }

            textfield.GetComponent<Text>().text = output;
        }
	}

    public void OkButtonClick()
    {
        SceneManager.LoadScene("Game");
    }
}
