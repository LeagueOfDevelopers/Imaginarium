﻿using System.Collections.Generic;
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
            Debug.Log("Response: " + www.text);
            return json.ToDictionary();
    }

    public string text() {
        return www.text.ToString();
    }

    public void JoinLobby(int size) {
        JSONObject data = new JSONObject();
        data.AddField("size", size);
        Debug.Log("Join Lobby " + data.ToString());
        sendRequest(ServerAPI.RequestType.JoinLobby, data);
    }

    public void LeaveLobby()
    {
        JSONObject data = new JSONObject();
        Prefs pref = new Prefs();
        data.AddField("token", pref.getToken());
        Debug.Log("Leave Lobby " + data.ToString());
        sendRequest(ServerAPI.RequestType.LeaveLobby, data);
    }

    public void UpdateLobby() {
        JSONObject data = new JSONObject();
        Prefs pref = new Prefs();
        data.AddField("token", pref.getToken());
        Debug.Log("Update Lobby" + data.ToString());
        sendRequest(ServerAPI.RequestType.UpdateLobby, data);
    }


    public void GetRoomStatus() {
        JSONObject data = new JSONObject();
        Prefs pref = new Prefs();
        data.AddField("token", pref.getToken());
        Debug.Log("GetRoomStatus" + data.ToString());
        sendRequest(ServerAPI.RequestType.GetGameStatus, data);
    }

    public void UpdateRoomStatus(JSONObject data) {
        Prefs pref = new Prefs();
        data.AddField("token", pref.getToken());
        data.AddField("stage", pref.getStage());
        Debug.Log("UpdateRoomStatus" + data.ToString());
        sendRequest(ServerAPI.RequestType.SetGameStatus, data);
    }

    public void GetGameScore()
    {
        Prefs pref = new Prefs();
        JSONObject data = new JSONObject();
        data.AddField("token", pref.getToken());
        Debug.Log("GetGameScore" + data.ToString());
        sendRequest(ServerAPI.RequestType.GetGameScore, data);
    }

    public void TestRequest() {
        www = new WWW(url + "/Test");
    }

    public void TestRequest(JSONObject data)
    {
        sendRequest(ServerAPI.RequestType.Test, data);
    }

    private void sendRequest(ServerAPI.RequestType type, JSONObject dataObject) 
    {
        Debug.Log(url + '/' + type.ToString() + " /// " + dataObject.ToString());
        byte[] data = System.Text.Encoding.UTF8.GetBytes(dataObject.ToString());
        
        www = new WWW(url+'/' +type.ToString(), data);
    }
}
