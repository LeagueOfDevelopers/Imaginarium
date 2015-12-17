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

            return new JSONObject(www.text.ToString()).ToDictionary();
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
        sendRequest(ServerAPI.RequestType.GetRoomStatus, data);
    }

    public void UpdateRoomStatus(string token, JSONObject data) {
        data.AddField("token", token);
        sendRequest(ServerAPI.RequestType.UpdateRoomStatus, data);
    }

    public void TestRequest() {
        www = new WWW(url + "/Test");
    }

    private void sendRequest(ServerAPI.RequestType type, JSONObject dataObject) 
    {
        Debug.Log(dataObject.ToString());
        byte[] data = System.Text.Encoding.UTF8.GetBytes(dataObject.ToString());
        www = new WWW(url+'/'+type.ToString(), data);
    }
}
