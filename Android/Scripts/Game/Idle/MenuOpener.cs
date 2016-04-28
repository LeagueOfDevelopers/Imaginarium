using UnityEngine;
using System.Collections;

public class MenuOpener : MonoBehaviour {

    public GameObject Menu;
    public GameObject Scoreboard;

    public void OpenMenu()
    {
        Scoreboard.SetActive(false);
        Menu.SetActive(true);
    }

    public void OpenScoreboard()
    {
        Scoreboard.SetActive(true);
        Menu.SetActive(false);
    }
}
