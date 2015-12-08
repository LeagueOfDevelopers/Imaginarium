using System.Collections;
using UnityEngine.UI;
using UnityEngine;

public class ServerDriver {

    private string url = "http://185.87.49.245:8080/ImagServerApp_war";         ///TODO ВЫНЕСТИ В КОНФИГ!
    private WWW www;

    public bool isDone() {
        return www.isDone;
    }

    public JSONObject getResponse() {
        if (isDone())
            return new JSONObject(www.text.ToString());

        return new JSONObject();
    }

    public void JoinLobby() {
        sendRequest(ServerAPI.RequestType.JoinLobby, new JSONObject());
    }

    public void UpdateLobby(string token) {
        JSONObject data = new JSONObject();
        data.AddField("token", token);
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

    private void sendRequest(ServerAPI.RequestType type, JSONObject dataObject) 
    {
        byte[] data = System.Text.Encoding.UTF8.GetBytes(dataObject.ToString());
        www = new WWW(url+'/'+type.ToString(), data);
    }

}
