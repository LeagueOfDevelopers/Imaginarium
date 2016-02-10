using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class FourthStageLogic : MonoBehaviour {

    public GameObject Cards;
    Prefs prefs = new Prefs();

    // Use this for initialization
    void Start () {
        SetCards();
        ShowVoteForCards();
    }

    private void SetCards()
    {
        Cards.GetComponent<CardNamer>().SetCards(prefs.getCardsForVote());
    }

    public void OKButtonClickEventHandler()
    {
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
            if (vote == prefs.getHeadCard())
                text.GetComponent<Text>().color = Color.red;
            if(vote == prefs.getOwnCard())
                text.GetComponent<Text>().color = Color.green;
            i++;
        }
    }
}
