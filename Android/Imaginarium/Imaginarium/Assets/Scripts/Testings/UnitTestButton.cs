using UnityEngine;
using System.Collections;

public class UnitTestButton : MonoBehaviour {

    //Класс для тестов значений полученных с сервера

    

    public int stage = 1;
    public int countOfPlayers = 0;
    public bool isDone = false;
    public bool isHead = false;
    public int score = 0;
    public int CardOfHead = 5;
    public string Text = "12345";

    public int[] cardsInHand = { 5, 10, 15, 20, 25, 30 };
    public int[] cardsSecondStage = { 5, 10, 15, 20, 25, 30 };
    public int[] cardsFourthStage = { 5, 10, 15, 20, 25, 30 };
    public int[] VoteFouthStage = { 5, 10, 15, 20, 25, 30 };


    public JSONObject StartTest() {
        JSONObject data;
         switch (stage)
        {
            case 1:
                data = FirstStage();
                break;
            case 2:
                data = SecondStage();
                break;
            case 3:
                data = ThirdStage();
                break;
            case 4:
                data = FourtStage();
                break;
            default:
                data = ZeroStage();
                break;
        }

        return sendTestReqest(data);
    }


    private JSONObject sendTestReqest(JSONObject data)
    {
        data.AddField("stage", stage);
        data.AddField("isDone", isDone);
        data.AddField("countOfPlayers", countOfPlayers);

        return data;
    }

    private JSONObject FirstStage()
    {
        JSONObject data = new JSONObject();
        data.AddField("isHead", isHead);
        data.AddField("score", score);

        for (int i = 0; i < 6; i++)
        {
            data.AddField("card#" + i, cardsInHand[i]);
        }

        return data;
    }

    private JSONObject SecondStage()
    {
        JSONObject data = new JSONObject();
        data.AddField("text", Text);

        return data;
    }

    private JSONObject ThirdStage()
    {
        JSONObject data = new JSONObject();

        for (int i = 0; i < 6; i++)
        {
            data.AddField("card#" + i, cardsSecondStage[i]);
        }

        return data;
    }

    private JSONObject FourtStage()
    {
        JSONObject data = new JSONObject();
        data.AddField("cardOfHead", CardOfHead);

        for (int i = 0; i < 6; i++)
        {
            data.AddField("card#" + i, cardsFourthStage[i]);
            data.AddField("vote#" + i, VoteFouthStage[i]);
        }

        return data;
    }

    private JSONObject ZeroStage()
    {
        return new JSONObject();
    }

    
}
