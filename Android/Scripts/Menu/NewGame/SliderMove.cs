using UnityEngine;
using UnityEngine.UI;

public class SliderMove : MonoBehaviour {

    public GameObject SliderObject;
    public GameObject SizeLabel;
    private Slider _slider;
    private Text _size;

    void Start()
    {
        _slider = SliderObject.GetComponent<Slider>();
        _size = SizeLabel.GetComponent<Text>();
    }

	// Update is called once per frame
	void Update () {
        _size.text = _slider.value.ToString();
	}

    public int GetCount()
    {
        return (int)_slider.value;
    }
}
