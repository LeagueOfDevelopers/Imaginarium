using UnityEngine;
using System.Collections;

public class MenuListener : MenuHandler
{

    public override void Update()
    {
        if (CurrentScreen == ScreenState.Title)
            TitleTouchListener();

        base.Update();
    }

    public void TitleTouchListener()
    {
        if ((Input.touchCount > 0)||(Input.GetMouseButtonDown(0)))
        {
            OpenStartButtonScreen();
        }
    }

    public void StartButtonClickListener() {
        OpenSearchButtonScreen();
    }
    public void SearchButtonClickListener()
    {
        ServerDriver driver = new ServerDriver();
        driver.LeaveLobby();
        OpenTitleScreen();
    }
}
