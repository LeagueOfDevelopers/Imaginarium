using UnityEngine;
using System;
using System.Collections;

[Serializable]
public class JoinLobbyRequest {

    public int size;
    public string speed = "slow";

    public JoinLobbyRequest(int size)
    {
        this.size = size;
    }
}
