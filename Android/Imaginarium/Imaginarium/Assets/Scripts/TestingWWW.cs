using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class TestingWWW : MonoBehaviour
{
    public string url = "http://185.87.49.245:8080/ImagServerApp_war/JoinLobby";
    IEnumerator Start()
    {
        WWWForm form = new WWWForm();
        form.AddField("fsdfds", "fdsfsdf");
        WWW www = new WWW(url,form);
        yield return www;
         Debug.Log(www.text.ToString());
        JSONObject json = new JSONObject(www.text.ToString());
        Text text = GetComponent<Text>();
        text.text = json.GetField("token").ToString();
    }
}