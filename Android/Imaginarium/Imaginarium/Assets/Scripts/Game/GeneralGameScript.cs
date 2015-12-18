using UnityEngine;
using UnityEngine.SceneManagement;
using System.Collections;

public class GeneralGameScript : MonoBehaviour {
    // Use this for initialization
	void Start () {
        SceneManager.LoadSceneAsync(2);
	}
	
	// Update is called once per frame
	void Update () {
	
	}
}
