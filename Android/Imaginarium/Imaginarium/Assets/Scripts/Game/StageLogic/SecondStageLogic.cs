using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class SecondStageLogic : MonoBehaviour {

    public GameObject Cards;
    Prefs prefs = new Prefs();
    ServerDriver driver = new ServerDriver();

    // Use this for initialization
    void Start () {
        driver.TestRequest();
        gameObject.GetComponent<CardMove>().SetCountOfCards(6); //Задаём кол-во используемых карт
        SetCards();
    }
	
	// Update is called once per frame
	void Update () {
        if (driver.isDone() && driver.text().Length > 0)
        {
            SceneManager.LoadScene("Game");
        }
    }

    private void SetCards()
    {
        Cards.GetComponent<CardNamer>().SetCards(prefs.getCards());
    }

    public void OKButtonClickEventHandler()
    {
            int currentCard = gameObject.GetComponent<CardMove>().GetCurrentCard();
            int cardNum = prefs.getCards()[currentCard];
            prefs.setOwnCard(cardNum);

            JSONObject json = new JSONObject();
            json.AddField("card", cardNum);
            driver.UpdateRoomStatus(json);
    }
}
