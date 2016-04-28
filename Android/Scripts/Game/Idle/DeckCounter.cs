using UnityEngine;
using UnityEngine.UI;

public class DeckCounter : MonoBehaviour {

    private int[] maxArr = { 96, 75, 72, 98 };
    private int max;
    private Prefs prefs = new Prefs();

    void Start()
    {
        max = maxArr[prefs.getSize() - 4];
    }

    void Update()
    {
        GetComponent<Text>().text = prefs.GetAmountOfCards().ToString() + '/' + max.ToString();
    }

}
