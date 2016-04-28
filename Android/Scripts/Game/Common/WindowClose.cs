using UnityEngine;
using System.Collections;

public class WindowClose : MonoBehaviour {

    public void Close()
    {
        gameObject.SetActive(false);
    }
}
