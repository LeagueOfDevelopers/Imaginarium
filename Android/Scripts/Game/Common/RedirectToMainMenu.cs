using UnityEngine;
using UnityEngine.SceneManagement;

public class RedirectToMainMenu : MonoBehaviour {

    public void ButtonPressed()
    {
        Prefs prefs = new Prefs();
        prefs.deleteAll();
        SceneManager.LoadScene(0);
    }
	
	
}
