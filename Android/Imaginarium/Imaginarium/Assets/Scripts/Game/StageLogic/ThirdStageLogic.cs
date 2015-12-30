using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class ThirdStageLogic : MonoBehaviour {

    public GameObject Cards;
    Prefs prefs = new Prefs();
    ServerDriver driver = new ServerDriver();
    // Use this for initialization
    void Start () {
        driver.TestRequest();
        SetCards();
    }
	
	// Update is called once per frame
	void Update () {
        if (driver.isDone() && driver.text().Length > 0)
        {
            Debug.Log(driver.text());
            prefs.setIsStageComplete(true);
            SceneManager.LoadScene("Game");
        }
    }

    private void SetCards()
    {
        int[] allCards = prefs.getChosenCards();
        int ownCard = prefs.getOwnCard();
        int[] cardsForVote = new int[5];
        int k = 0;
        foreach (int card in allCards)
        {
            if (card != ownCard)
            {
                cardsForVote[k] = card;
                k++;
            }
        }
        Cards.GetComponent<CardNamer>().SetCards(cardsForVote);
    }

    public void OKButtonClickEventHandler()
    {
        int currentCard = gameObject.GetComponent<CardMove>().GetCurrentCard();
        int cardNum = prefs.getCards()[currentCard];

        JSONObject json = new JSONObject();
        json.AddField("card", cardNum);
        driver.UpdateRoomStatus(prefs.getToken(), json);
    }
}
