using UnityEngine;
using System.Collections;

public class CardPanelMover : MonoBehaviour {

    public GameObject[] CardViews;
    public enum State {Open, Close };
    private State curentState = State.Close;
    

    public void ClosePanel()
    {
        if (curentState == State.Open)
        {
            foreach (GameObject card in CardViews)
            {
                
                card.GetComponent<Animation>().clip = card.GetComponent<Animation>().GetClip("BigCardView");
                card.GetComponent<Animation>().Play();
            }
            GetComponent<Animation>().clip = GetComponent<Animation>().GetClip("ClosePanel");
            GetComponent<Animation>().Play();
            curentState = State.Close;
        }
    }

    public void OpenPanel()
    {
        if (curentState == State.Close)
        {
            foreach (GameObject card in CardViews)
            {
                card.GetComponent<Animation>().clip = card.GetComponent<Animation>().GetClip("SmallCardView");
                card.GetComponent<Animation>().Play();
            }
            GetComponent<Animation>().clip = GetComponent<Animation>().GetClip("OpenPanel");
            GetComponent<Animation>().Play();
            curentState = State.Open;
        }
    }

    public void ToglePanel()
    {
        if (curentState == State.Open)
            ClosePanel();
        else
            OpenPanel();
    }
}
