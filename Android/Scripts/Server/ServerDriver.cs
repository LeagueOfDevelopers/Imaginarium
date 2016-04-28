using System.Collections.Generic;
using UnityEngine.UI;
using UnityEngine;

public class ServerDriver {

    private string url = "http://185.87.49.245:8080/ImagServerV2_war";         ///TODO ВЫНЕСТИ В КОНФИГ!
    private WWW www;

    public bool isDone() {
        return www.isDone;
    }

    public string text() {
        return www.text.ToString();
    }

    public WWW JoinLobby(int size) {
        JoinLobbyRequest data = new JoinLobbyRequest(size);      
        www = sendRequest(ServerAPI.RequestType.JoinLobby, data);
        return www; 
    }

    public WWW LeaveLobby()
    {
        LeaveLobbyRequest data = new LeaveLobbyRequest();
        www = sendRequest(ServerAPI.RequestType.LeaveLobby, data);
        return www; 
    }

    public WWW UpdateLobby() {
        UpdateLobbyRequest data = new UpdateLobbyRequest();
        return sendRequest(ServerAPI.RequestType.UpdateLobby, data);
    }


    public WWW GetRoomStatus() {
        GetGameStatusRequest data = new GetGameStatusRequest();
        www = sendRequest(ServerAPI.RequestType.GetGameStatus, data);
        return www;
    }

    public WWW UpdateRoomStatus(SetGameStatusRequest data) {
        www = sendRequest(ServerAPI.RequestType.SetGameStatus, data);
        return www; 
    }

    public WWW GetGameScore()
    {
        GetGameScoreRequest data = new GetGameScoreRequest();
        www = sendRequest(ServerAPI.RequestType.GetGameScore, data);
        return www; 
    }

    public WWW LeaveGame()
    {
        GetGameScoreRequest data = new GetGameScoreRequest();   //Костыль! Поменять!!
        www = sendRequest(ServerAPI.RequestType.LeaveGame, data);
        return www;
    }

    private WWW sendRequest<T>(ServerAPI.RequestType type, T dataObject) 
    {
        string json = JsonUtility.ToJson(dataObject);
        Debug.Log(url + '/' + type.ToString() + " /// " + json);
        byte[] data = System.Text.Encoding.UTF8.GetBytes(json);
        www = new WWW(url+'/' +type.ToString(), data);
        return www;
    }

    public T GetResponce<T>()
    {
        return JsonUtility.FromJson<T>(www.text);
    }
}
