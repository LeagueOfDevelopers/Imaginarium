using UnityEngine;
using System;
using System.Collections;
using UnityEngine.UI;

public class TestingWWW : MonoBehaviour
{
    public string url = "http://185.87.49.245:8080/ImagServerApp_war/Test";
    //public string url = "http://google.com";

    IEnumerator Start()
    {
        JSONObject dataObject = new JSONObject();
        dataObject.AddField("token", "123235e4rgdfhdfhfdgd");
        dataObject.AddField("card1", 123456778);
        byte[] data = System.Text.Encoding.UTF8.GetBytes(dataObject.ToString());
        WWW www = new WWW(url,data);
        yield return www;
         Debug.Log(www.text.ToString());
        JSONObject json = new JSONObject(www.text.ToString());
        Text text = GetComponent<Text>();
        text.text = json.ToString();
    }

    private void request() {
        
    }
}