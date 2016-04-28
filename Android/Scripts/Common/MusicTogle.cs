using UnityEngine;
using System.Collections;

public class MusicTogle : MonoBehaviour {

    public void Togle()
    {
        AudioSource audio = GameObject.Find("MusicPlayer").GetComponent<AudioSource>();

        if (audio == null)
            return;

        if (audio.isPlaying)
            audio.Stop();
        else
            audio.Play();
    }
}
