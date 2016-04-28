using System;
using UnityEngine;

[Serializable]
public class Player : IComparable
{
    private Color[] _allColors = { Color.red, Color.green, Color.blue, Color.white, Color.black, Color.cyan, Color.magenta };
    private string _name;
    private Color _color;
    private int _colorNum;

    public string token;
    public int score = 0;

    public string Name
    {
        get
        {
            return _name;
        }
    }

    public Color Color
    {
        get
        {
            return _color;
        }
    }

   

    public Player(string token, int score)
    {
        Prefs prefs = new Prefs();
        _name = prefs.GetPlayerName(token);
        _colorNum = prefs.GetPlayerColor(token);
        _color = _allColors[_colorNum];
        this.token = token;
        this.score = score;

    }
    public Player()
    {

    }

    public void OpenPlayerInfo()
    {
        Prefs prefs = new Prefs();
        _name = prefs.GetPlayerName(token);
        _colorNum = prefs.GetPlayerColor(token);
        _color = _allColors[_colorNum];
    }

    public bool isOwnPlayer()
    {
        Prefs prefs = new Prefs();
        return (token == prefs.getToken());
    }

    public int GetColorNum()
    {
        return _colorNum;
    }

    public int GetScore()
    {
        return this.score;
    }

    public override string ToString()
    {
        return _name + " " + _color.ToString();
    }

    public int CompareTo(object obj)
    {
        if (obj == null) return 1;
        Player otherPlayer = obj as Player;

        if (otherPlayer == null)
            throw new Exception();

        if (score > otherPlayer.GetScore())
            return -1;
        if (score < otherPlayer.GetScore())
            return 1;
        return 0;
    }
}
