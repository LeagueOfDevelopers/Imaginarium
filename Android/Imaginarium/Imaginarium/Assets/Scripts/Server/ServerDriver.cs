using System.Collections;
using UnityEngine.UI;
using UnityEngine;

public class ServerDriver {

    private string url = "http://185.87.49.245:8080/ImagServerApp_war";
    private WWW www;

    public bool isDone() {
        return www.isDone;
    }

    public JSONObject getResponse() {
        if (isDone())
            return new JSONObject(www.text.ToString());

        return new JSONObject();
    } 

    public void sendRequest(ServerAPI.RequestType type, JSONObject dataObject) 
    {
        byte[] data = System.Text.Encoding.UTF8.GetBytes(dataObject.ToString());
        www = new WWW(url+'/'+type.ToString(), data);
    }

}
