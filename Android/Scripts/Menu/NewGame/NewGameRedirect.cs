using UnityEngine;
using System.Collections;

public class NewGameRedirect : MonoBehaviour {

    public GameObject SearchGameMenu;
    public GameObject MainMenu;

    void Update()
    {
        if (Input.GetKeyDown(KeyCode.Escape))
        {
            BackClick();
        }
    }

    public void SearchGameClick(bool isFastGame)
    {
        SearchGameMenu.SetActive(true);
        gameObject.SetActive(false);
        int size;
        if (GetComponent<CountOfPlayersButton>())
            size = GetComponent<CountOfPlayersButton>().GetCount();
        else
            size = GetComponent<SliderMove>().GetCount();

        Prefs prefs = new Prefs();
        prefs.setSize(size);
        prefs.SetIsFastGame(isFastGame);
    }

    public void BackClick()
    {
        MainMenu.SetActive(true);
        gameObject.SetActive(false);
    }
}
