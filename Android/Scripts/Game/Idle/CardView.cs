using UnityEngine;
using System.Collections;

public class CardView : MonoBehaviour {

    private bool isViewActive = false;
    private bool isPressed = false;
    private float countdown;

    public float countdownBase = 1;
    public  string animName = "Card";



	void Update () {
        if (isPressed && !isViewActive)
        {
            countdown -= Time.deltaTime;
            if (countdown < 0)
            {
                transform.SetAsLastSibling();
                GetComponent<Animation>()[animName].speed = 1;
                GetComponent<Animation>().Play(animName);
                countdown = countdownBase;
                StartCoroutine(WaitForEndOfTouch());
            }
        }

        if (isViewActive && (Input.touchCount > 0 || Input.GetMouseButton(0)))
        {
            transform.position = new Vector3(transform.position.x, transform.position.y, 0);
            isViewActive = false;
            GetComponent<Animation>()[animName].speed = -1;
            GetComponent<Animation>()[animName].time = GetComponent<Animation>()[animName].length;
            GetComponent<Animation>().Play(animName);
        }
	}


    public void OpenViewDown()
    {
        countdown = countdownBase;
        isPressed = true;
    }

    public void OpenViewUp()
    {
        isPressed = false;
    }

    IEnumerator WaitForEndOfTouch()
    {
        yield return new WaitForSeconds(1.5f);
        isViewActive = true;
    }
}
