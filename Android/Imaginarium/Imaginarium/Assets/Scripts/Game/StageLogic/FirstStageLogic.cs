using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class FirstStageLogic : MonoBehaviour {
    public GameObject Cards;
    public GameObject TextBox;
    Prefs prefs = new Prefs();
    ServerDriver driver = new ServerDriver();
	// Use this for initialization
	void Start () {
        driver.TestRequest();
        SetCards();
	}
	
	// Update is called once per frame
	void Update () {
        if (driver.isDone() && driver.text().Length>0)
        {
            SceneManager.LoadScene("Game");
        }
	}

    private void SetCards() {
        Cards.GetComponent<CardNamer>().SetCards(prefs.getCards());
    }

    public void OKButtonClickEventHandler() {
        InputField field = TextBox.GetComponent<InputField>();
        if (field.text.Length != 0)
        {
            int currentCard = gameObject.GetComponent<CardMove>().GetCurrentCard();
            int cardNum = prefs.getCards()[currentCard];
            prefs.setText(field.text);
            prefs.setOwnCard(cardNum);

            JSONObject json = new JSONObject();
            json.AddField("card", cardNum);
            json.AddField("text", field.text);
            driver.UpdateRoomStatus(json);

        }
    }
}
