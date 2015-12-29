using System.Collections.Generic;
using UnityEngine.UI;
using UnityEngine;

public class ServerDriver {

    private string url = "http://185.87.49.245:8080/ImagServerApp_war";         ///TODO ВЫНЕСТИ В КОНФИГ!
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

    public void UpdateLobby(string token) {
        JSONObject data = new JSONObject();
        data.AddField("token", token);
        Debug.Log(data.ToString());
        sendRequest(ServerAPI.RequestType.UpdateLobby, data);
    }


    public void GetRoomStatus(string token) {
        JSONObject data = new JSONObject();
        data.AddField("token", token);
        sendRequest(ServerAPI.RequestType.GameStatus, data);
    }

    public void UpdateRoomStatus(string token, JSONObject data) {
        data.AddField("token", token);
        sendRequest(ServerAPI.RequestType.ChangeGameStatus, data);
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
