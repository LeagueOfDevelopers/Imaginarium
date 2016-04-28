using UnityEngine;
using UnityEngine.UI;
using System.Collections.Generic;
using System.Collections;

public class PlayersPanel : MonoBehaviour {



    public void SetPlayerList(Player[] list)
    {
        for(int i = 0; i < 7; i++)
        {
            GameObject obj = gameObject.transform.GetChild(i).gameObject;
            if (i< list.Length)
            {
                list[i].OpenPlayerInfo();
                obj.SetActive(true);
                obj.GetComponent<PlayerSetter>().SetPlayer(list[i], i+1);
            }
            else
                obj.SetActive(false);
        }
    }

}
