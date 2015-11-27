using UnityEngine;
using System.Collections;

public class MenuHandler : MonoBehaviour
{

    public int yMenu = -5;
    public int xSearch = -5;
    public float MenuReplaceTime = 8;
    public float menuSpeed = 3;

    private float Timer;

    bool menuVisible = false;
    bool searchGame = false;

    void Update()
    {
        if (searchGame)
        {
            SearchLoop();
        }
        else
        {
            if (menuVisible)
            {
                MenuLoop();
            }
            else
            {
                TitleLoop();
            }
        }
    }


    void moveY(float pos)
    {
        gameObject.transform.position = Vector2.Lerp(gameObject.transform.position,
            new Vector2(gameObject.transform.position.x, pos), Time.deltaTime * menuSpeed);
    }

    void moveX(float pos)
    {
        gameObject.transform.FindChild("Menu").transform.position = Vector2.Lerp(
            gameObject.transform.FindChild("Menu").transform.position,
            new Vector2(pos, gameObject.transform.FindChild("Menu").transform.position.y),
            Time.deltaTime * menuSpeed);
    }

    void MenuLoop()
    {
        moveX(0);
        moveY(yMenu);
        Timer -= Time.deltaTime;

        if (Timer < 0)
        {
            Timer = MenuReplaceTime;
            menuVisible = false;
        }
    }

    void TitleLoop()
    {
        if (Input.touchCount > 0)
        {
            menuVisible = true;
            Timer = MenuReplaceTime;
        }
        else
            moveY(0);
    }


    void SearchLoop()
    {
        moveX(xSearch);
    }


    void checkTouch(Vector2 pos)
    {
        Vector3 wp = Camera.main.ScreenToWorldPoint(pos);
        Vector2 touchPos = new Vector2(wp.x, wp.y);
        var hit = Physics2D.OverlapPoint(touchPos);

        if (hit)
        {
            Debug.Log(hit.transform.gameObject.name);
        }
    }
}
