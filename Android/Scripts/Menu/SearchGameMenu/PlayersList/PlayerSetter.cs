using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class PlayerSetter : MonoBehaviour {


    public void SetPlayer(Player player, int num)
    {
        if (gameObject.transform.FindChild("Name"))
            gameObject.transform.FindChild("Name").GetComponent<Text>().text = player.Name;

        gameObject.transform.FindChild("Img").GetComponent<Image>().color = player.Color;

        if (gameObject.transform.FindChild("Num"))
            gameObject.transform.FindChild("Num").GetComponent<Text>().text = num.ToString() + '.';

        if (gameObject.transform.FindChild("Score"))
            gameObject.transform.FindChild("Score").GetComponent<Text>().text = player.GetScore().ToString();

        if (gameObject.transform.FindChild("DeltaScore"))
        {
            Prefs prefs = new Prefs();
            int delta = prefs.GetPlayerDeltaScore(player.token);
            string result;
            if (delta <= 0)
                result = "(" + delta.ToString() + ")";
            else
                result = "(+" + delta.ToString() + ")";
            gameObject.transform.FindChild("DeltaScore").GetComponent<Text>().text = result;

        }

        if (gameObject.transform.FindChild("Pos"))  //ONLY for OwnPlayerLabel
        {
            Prefs prefs = new Prefs();
            gameObject.transform.FindChild("Pos").GetComponent<Text>().text = prefs.GetPositionInScoreboard().ToString() + '/' + prefs.getSize().ToString();
        }
    }

   
}
