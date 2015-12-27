using UnityEngine;
using UnityEngine.SceneManagement;
using System;
using System.Collections.Generic;

public class GeneralGameScript : MonoBehaviour {

    public float cooldownResetValue = 5;
    float cooldown = 0;
    bool isRequestHasReaden = false; 
    ServerDriver driver = new ServerDriver();
    Prefs prefs = new Prefs();
    // Use this for initialization
	void Start () {
        driver.GetRoomStatus(prefs.getToken());

	}
	
	// Update is called once per frame
	void Update () {
        if (Input.GetKeyDown(KeyCode.Escape))
            Exit();
        if (driver.isDone())
        {
            if (isRequestHasReaden)
            {
                //Отправка реквеста
                cooldown -= Time.deltaTime;
                if (cooldown < 0)
                {
                    cooldown = cooldownResetValue;
                    isRequestHasReaden = false;
                    driver.GetRoomStatus(prefs.getToken());
                }
            }
            else
            {
                //Обработка реквеста
                isRequestHasReaden = true;
                redirectHandler(driver.getResponse());

            }
        }
	}


    private void redirectHandler(Dictionary<string, string> response)
    {
        if (response["status"] == "OK")
        {
            int stage = Convert.ToInt32(response["stage"]);

            switch (stage)
                {
                case 1:
                    int[] cards = new int[6];
                    for (int i = 0; i < 6; i++)
                        cards[i] = Convert.ToInt32(response[("card#" + i)]);
                    prefs.setCards(cards);
                    prefs.setScore(Convert.ToInt32(response["score"]));
                    if (Convert.ToBoolean(response["is_head"]))
                        SceneManager.LoadSceneAsync("FirstStage");
                    break;
                case 2:
                    prefs.setText(response["text"]);
                    if (!prefs.getHead())
                        SceneManager.LoadSceneAsync("SecondStage");
                    break;
                case 3:
                    int[] chosenCards = new int[6];
                    for (int i = 0; i < 6; i++)
                        chosenCards[i] = Convert.ToInt32(response[("card#" + i)]);
                    prefs.setChosenCards(chosenCards);
                    if (!prefs.getHead())
                        SceneManager.LoadSceneAsync("ThirdStage");
                    break;
                case 4:
                    int[] VoteForCards = new int[6];
                    for (int i = 0; i < 6; i++)
                        VoteForCards[i] = Convert.ToInt32(response[("vote#" + i)]);
                    prefs.setVoteForCards(VoteForCards);
                    prefs.setHeadCard(Convert.ToInt32(response["cardOfHead"]));
                    SceneManager.LoadSceneAsync("FourthStage");
                    break;
                    

                }

        }
    }

    public void Exit() {
        Debug.Log("exit");
        SceneManager.LoadSceneAsync("Menu");
    }
}

