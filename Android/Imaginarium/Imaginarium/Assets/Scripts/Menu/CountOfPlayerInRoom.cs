using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class CountOfPlayerInRoom : MonoBehaviour {

    public GameObject menuLogicObject;
    private MenuLogic menuLogic;
	// Use this for initialization
	void Start () {
        menuLogic = menuLogicObject.GetComponent<MenuLogic>();
	}
	
	// Update is called once per frame
	void Update () {
        GetComponent<Text>().text = menuLogic.GetCountOfPlayers().ToString();
	}
}
