using System.Collections;
using UnityEngine.UI;
using UnityEngine;

public class ServerDriver :MonoBehaviour {

    private string url = "http://185.87.49.245:8080/ImagServerApp_war";
    private JSONObject response;

    void Start() {
        JSONObject data = new JSONObject();
        data.AddField("token", "123235e4rgdfhdfhfdgd");
        request(ServerAPI.RequestType.Test, data);
    }


    public IEnumerator request(ServerAPI.RequestType type, JSONObject data) 
    {
        WWW www = new WWW(url+type.ToString(), data);
        yield return www;
        Text text = GetComponent<Text>();
        text.text = www.text;
    }

}
