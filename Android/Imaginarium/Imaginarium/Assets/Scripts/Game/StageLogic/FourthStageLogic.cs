using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class FourthStageLogic : MonoBehaviour {

    private ServerDriver driver = new ServerDriver();
    public GameObject Cards;
    Prefs prefs = new Prefs();

    // Use this for initialization
    void Start () {
        driver.TestRequest();
        SetCards();
        ShowVoteForCards();
    }

    void Update() {
        if (driver.isDone() && driver.text().Length > 0)
        {
            SceneManager.LoadScene("Game");
        }
    }

    private void SetCards()
    {
        Cards.GetComponent<CardNamer>().SetCards(prefs.getCardsForVote());
    }

    public void OKButtonClickEventHandler()
    {
        JSONObject json = new JSONObject();
        driver.UpdateRoomStatus(json);
        SceneManager.LoadScene("Game");
    }

    private void ShowVoteForCards()
    {
        int[] votes = prefs.getVoteForCards();

        int i = 0;
        foreach (int vote in votes)
        {
            GameObject child = Cards.transform.FindChild("Card" + i).gameObject;
            GameObject text = child.transform.GetChild(0).gameObject;
            text.GetComponent<Text>().text = vote.ToString();

            if (child.GetComponent<CardSpriteHandler>().getCard() == prefs.getOwnCard())
                text.GetComponent<Text>().color = Color.green;
            if (child.GetComponent<CardSpriteHandler>().getCard() == prefs.getHeadCard())
                text.GetComponent<Text>().color = Color.red;

            i++;
        }

    }
}
