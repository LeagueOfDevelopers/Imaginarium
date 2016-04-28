using UnityEngine;
using System;
using System.Collections;

[Serializable]
public class GetGameStatusRequest
{
    public string token;

    public GetGameStatusRequest()
    {
        Prefs prefs = new Prefs();
        token = prefs.getToken();
    }
}

