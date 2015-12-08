using UnityEngine;
using System.Collections;

public class Prefs {

    public void setToken(string token)
    {
        PlayerPrefs.SetString("token", token);
    }

    public string getToken() {
        return PlayerPrefs.GetString("token", string.Empty);
    }

    public void setText(string text)
    {
        PlayerPrefs.SetString("text", text);
    }

    public string getText()
    {
        return PlayerPrefs.GetString("text", string.Empty);
    }

    public void setCards(int[] cards)
    {
        for (int i = 0; i < 6; i++)
        {
            PlayerPrefs.SetInt("card"+i.ToString(), cards[i]);
        }
    }

    public int[] getCards()
    {
        int[] cards = new int[6];
        for (int i = 0; i < 6; i++)
        {
            cards[i] = PlayerPrefs.GetInt("card"+i.ToString(), -1);
        }
        return cards;
    }

}
