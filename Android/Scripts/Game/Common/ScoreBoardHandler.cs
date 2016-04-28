using UnityEngine;
using UnityEngine.SceneManagement;
using System;
using System.Collections;
using System.Collections.Generic;

public class ScoreBoardHandler : MonoBehaviour {

    public GameObject PlayersPanel;
    private Prefs prefs = new Prefs();

    void OnEnable()
    {
        StartCoroutine(GetScoreboard());
    }

    IEnumerator GetScoreboard()
    {
        ServerDriver driver = new ServerDriver();
        yield return driver.GetGameScore();
        try
        {
            GetGameScoreResponce responce = driver.GetResponce<GetGameScoreResponce>();
            
            Array.Sort(responce.players);
            UpdatePrefsData(responce.players);
            PlayersPanel.GetComponent<PlayersPanel>().SetPlayerList(responce.players);

        }
        catch (System.Exception e)
        {
            Debug.Log(e.Message);
            SceneManager.LoadScene(0);
        }


    }


    private void UpdatePrefsData(Player[] players)
    {
        for (int i = 0; i < prefs.getSize(); i++)
        {
            if (players[i].token == prefs.getToken())
            {
                prefs.setScore(players[i].GetScore());
                prefs.SetPositionInScoreboard(i + 1);
                return;
            }
        }
    }
}
