using UnityEngine;
using System.Collections;

public class Test : MonoBehaviour {

    

    void Start()
    {
        Player[] players = new Player[6];
        players[0] = new Player("Mat", 0);
        players[1] = new Player("Dave", 1);
        players[2] = new Player("Gabe", 2);
        players[3] = new Player("Nikolas", 3);
        players[4] = new Player("Zac", 4);
        players[5] = new Player("Mathew", 5);
        
        GetComponent<PlayersPanel>().SetPlayerList(players);
    }
}
