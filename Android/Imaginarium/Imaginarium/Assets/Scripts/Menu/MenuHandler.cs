using UnityEngine;
using System.Collections;

public class MenuHandler : MonoBehaviour {

    public enum ScreenState { Title, StartButton, SearchButton }
    public static ScreenState CurrentScreen;

    private MenuElement Title;
    private MenuElement StartButton;
    private MenuElement SearchButton;
    private Vector3 CenterPos;

    // Use this for initialization
    protected void Start () {
        Title = new MenuElement(gameObject.transform.FindChild("Title").gameObject);
        StartButton = new MenuElement(gameObject.transform.FindChild("StartButton").gameObject);
        SearchButton = new MenuElement(gameObject.transform.FindChild("SearchButton").gameObject);
        CenterPos = gameObject.transform.FindChild("Center").position;

        Debug.Log("Start method");
        OpenTitleScreen();   
    }
	
	// Update is called once per frame
	public virtual void Update () {
        switch (CurrentScreen)
        {
            case ScreenState.Title:
                OpenTitleScreen();
                break;
            case ScreenState.StartButton:
                OpenStartButtonScreen();
                break;
            case ScreenState.SearchButton:
                OpenSearchButtonScreen();
                break;
            default:
                CurrentScreen = ScreenState.Title;      //Невозможный исход
                break;
        }

        Title.Update();
        StartButton.Update();
        SearchButton.Update();
    }

    protected void OpenStartButtonScreen() {
        CurrentScreen = ScreenState.StartButton;
        Title.MoveToStart();
        SearchButton.MoveToStart();
        StartButton.MoveTo(CenterPos);
    }

    protected void OpenSearchButtonScreen()
    {
        
        CurrentScreen = ScreenState.SearchButton;
        Title.MoveToStart();
        StartButton.MoveToStart();
        SearchButton.MoveTo(CenterPos);
    }

    protected void OpenTitleScreen()
    {
        CurrentScreen = ScreenState.Title;
        SearchButton.MoveToStart();
        StartButton.MoveToStart();
        Title.MoveTo(CenterPos);
    }

}
