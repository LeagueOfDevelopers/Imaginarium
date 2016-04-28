using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class CountOfPlayersButton : MonoBehaviour {

    private int count = 4;
    public int minValue = 4;
    public int maxValue = 7;
    public GameObject label;



	// Update is called once per frame
	void Update () {
        label.GetComponent<Text>().text = count.ToString();
	}

    public void PlusPlayer()
    {
        if(count<maxValue)
        count++;
    }

    public void MinusPlayer()
    {
        if (count > minValue)
            count--;
    }

    public int GetCount()
    {
        return count;
    }
}
