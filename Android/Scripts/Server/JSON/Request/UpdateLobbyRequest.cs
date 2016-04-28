using UnityEngine;
using System;
using System.Collections;

[Serializable]
public class UpdateLobbyRequest
{

    public string token;

    public UpdateLobbyRequest()
    {
        Prefs prefs = new Prefs();
        token = prefs.getToken();
    }
}
