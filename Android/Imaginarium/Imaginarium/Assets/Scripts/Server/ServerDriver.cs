using System.Collections;
using UnityEngine;

public class ServerDriver {

    private string url = "http://185.87.49.245:8080/ImagServerApp_war/JoinLobby";

    public ServerDriver() {
        
        
    }

    private IEnumerator request() 
    {

        WWW www = new WWW(url);
        yield return www;
    }

}
