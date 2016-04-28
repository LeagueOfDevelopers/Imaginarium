using UnityEngine;
using System.Collections;

public class MainMenuRedirect : MonoBehaviour {

    public GameObject NewGameMenu;
    public GameObject SettingsScreen;
    public GameObject RulesScreen;
    public GameObject AboutUsScreen;

    public void NewGameClick()
    {
        gameObject.SetActive(false);
        NewGameMenu.SetActive(true);
    }

    public void SettingsClick()
    {
        SettingsScreen.SetActive(true);
    }

    public void RulesClick()
    {
        RulesScreen.SetActive(true);
    }

    public void AboutUsClick()
    {
        AboutUsScreen.SetActive(true);
        Prefs prefs = new Prefs();
        prefs.deleteAll();
    }

}
