using UnityEngine;
using System.Collections;

public class InfoPanelHandler : MonoBehaviour {

	void Start () {

        GameObject fast = transform.FindChild("Fast").gameObject;
        GameObject slow = transform.FindChild("Slow").gameObject;

        Prefs prefs = new Prefs();
        if (prefs.GetIsFastGame())
        {
            fast.SetActive(true);
            slow.SetActive(false);
        }
        else
        {
            fast.SetActive(false);
            slow.SetActive(true);
        }
	}
	
	
}
