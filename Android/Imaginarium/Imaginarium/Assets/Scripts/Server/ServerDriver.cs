using System.Collections.Generic;
using UnityEngine.UI;
using UnityEngine;

public class ServerDriver {

    private string url = "http://185.87.49.245:8080/ImagServerV2_war";         ///TODO ВЫНЕСТИ В КОНФИГ!
    private WWW www;

    public bool isDone() {
        return www.isDone;
    }

    public Dictionary<string,string> getResponse() {
        JSONObject json = new JSONObject(www.text);
        Debug.Log(www.text);
        return json.ToDictionary();
    }

    public string text() {
        return www.text.ToString();
    }

    public void JoinLobby() {
        sendRequest(ServerAPI.RequestType.JoinLobby, new JSONObject());
    }

    public void UpdateLobby() {
        JSONObject data = new JSONObject();
        Prefs pref = new Prefs();
        data.AddField("token", pref.getToken());
        Debug.Log(data.ToString());
        sendRequest(ServerAPI.RequestType.UpdateLobby, data);
    }


    public void GetRoomStatus() {
        JSONObject data = new JSONObject();
        Prefs pref = new Prefs();
        data.AddField("token", pref.getToken());
        sendRequest(ServerAPI.RequestType.GetGameStatus, data);
    }

    public void UpdateRoomStatus(JSONObject data) {
        Prefs pref = new Prefs();
        data.AddField("token", pref.getToken());
        data.AddField("stage", pref.getStage());
        sendRequest(ServerAPI.RequestType.SetGameStatus, data);
    }

    public void TestRequest() {
        www = new WWW(url + "/Test");
    }

    public void TestRequest(JSONObject data)
    {
        Prefs prefs = new Prefs();
        data.AddField("token", prefs.getToken());
        sendRequest(ServerAPI.RequestType.Test, data);
    }

    private void sendRequest(ServerAPI.RequestType type, JSONObject dataObject) 
    {
        Debug.Log(dataObject.ToString());
        byte[] data = System.Text.Encoding.UTF8.GetBytes(dataObject.ToString());
        Debug.Log(url + '/' + type.ToString());
        www = new WWW(url+'/' +type.ToString(), data);
    }
}
