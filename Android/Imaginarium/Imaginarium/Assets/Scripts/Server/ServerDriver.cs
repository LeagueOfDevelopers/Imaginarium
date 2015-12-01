using System.Collections;
using UnityEngine;

public class ServerDriver {

    private string url = "http://185.87.49.245:8080/ImagServerApp_war/JoinLobby";

    public ServerDriver() {
        
        
    }

    public IEnumerator request() {
        WWWForm form = new WWWForm();
        form.AddField("SOMETHING", 1234);
        form.AddField("SomethingElse", "ElseShitOf");

        WWW request = new WWW(url, form);
        Debug.Log("sdfsf");
        yield return request;

        if (!string.IsNullOrEmpty(request.error))
        {
            Debug.Log("Error downloading: " + request.error);
        }
        else
        {
            // show the highscores
            Debug.Log(request.ToString());
        }
    }

}
