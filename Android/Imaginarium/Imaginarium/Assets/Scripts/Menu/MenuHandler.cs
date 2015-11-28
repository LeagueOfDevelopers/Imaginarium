using UnityEngine;
using System.Collections;

public class MenuHandler : MonoBehaviour {

    private MenuElement Title;
    private MenuElement StartButton;
    private MenuElement SearchButton;

    private Vector3 CenterPos;

    // Use this for initialization
    void Start () {
        Title = new MenuElement(gameObject.transform.FindChild("Title").gameObject);
        StartButton = new MenuElement(gameObject.transform.FindChild("StartButton").gameObject);
        SearchButton = new MenuElement(gameObject.transform.FindChild("StartButton").gameObject);
        CenterPos = gameObject.transform.FindChild("Center").position;

        Title.MoveTo(CenterPos);
    }
	
	// Update is called once per frame
	void Update () {
        Title.Update();
        StartButton.Update();
        SearchButton.Update();
	}

    public void OpenStartButtonScreen() {
        Title.MoveToStart();
        SearchButton.MoveToStart();
        StartButton.MoveTo(CenterPos);
    }

    public void OpenSearchButtonScreen()
    {
        Title.MoveToStart();
        StartButton.MoveToStart();
        SearchButton.MoveTo(CenterPos);
    }

    public void OpenTitleScreen()
    {
        SearchButton.MoveToStart();
        StartButton.MoveToStart();
        Title.MoveTo(CenterPos);
    }

}
