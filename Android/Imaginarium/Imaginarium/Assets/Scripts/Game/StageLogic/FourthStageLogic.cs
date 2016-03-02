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
        ShowInfoForCards();
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
        SceneManager.LoadScene("Scoreboard");
    }

    private void ShowInfoForCards()
    {
        int[] votes = prefs.getVoteForCards();
        string[] players = prefs.getPlayersForVote();


        for(int i = 0; i< prefs.getSize(); i++)
        {
            GameObject child = Cards.transform.FindChild("Card" + i).gameObject;

            GameObject voteLabel = child.transform.FindChild("ScoreBG").FindChild("vote").gameObject;
            GameObject nameLabel = child.transform.FindChild("NameBG").FindChild("Name").gameObject;

            voteLabel.GetComponent<Text>().text = votes[i].ToString();
            nameLabel.GetComponent<Text>().text = prefs.GetPlayerName(players[i]);

            if (child.GetComponent<CardSpriteHandler>().getCard() == prefs.getOwnCard())
            {
                child.transform.FindChild("ScoreBG").GetComponent<Image>().color = Color.green;
                child.transform.FindChild("NameBG").GetComponent<Image>().color = Color.green;
            }
            if (child.GetComponent<CardSpriteHandler>().getCard() == prefs.getHeadCard())
            {
                child.transform.FindChild("ScoreBG").GetComponent<Image>().color = Color.yellow;
                child.transform.FindChild("NameBG").GetComponent<Image>().color = Color.yellow;
            }
        }

    }

    
}
