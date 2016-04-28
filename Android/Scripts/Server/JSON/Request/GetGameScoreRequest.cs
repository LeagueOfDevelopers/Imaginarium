using UnityEngine;
using System.Collections;
using System;

[Serializable]
public class GetGameScoreRequest
{

    public string token;

    public GetGameScoreRequest()
    {
        Prefs prefs = new Prefs();
        token = prefs.getToken();
    }
}
