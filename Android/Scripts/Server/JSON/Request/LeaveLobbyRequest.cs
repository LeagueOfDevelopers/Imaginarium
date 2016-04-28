using System;
using System.Collections;

[Serializable]
public class LeaveLobbyRequest {
    public string token;

    public LeaveLobbyRequest()
    {
        Prefs prefs = new Prefs();
        token = prefs.getToken();
    }
	
}
