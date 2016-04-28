using UnityEngine;

public class PlayerNamer
{

    private string[] names = { "Асбьёрн", "Гудрёд", "Кьярваль", "Мелькольв", "Рагнлейв","Хьярмод", "Эйвинд" };
    

    public void SetNames(Player[] players)
    {

        Prefs prefs = new Prefs();
        for (int i = 0; i < players.Length; i++)
        {
            prefs.SetPlayerName(players[i].token, names[i]);
            prefs.SetPlayerColor(players[i].token, i);
        }
            
    }

}

