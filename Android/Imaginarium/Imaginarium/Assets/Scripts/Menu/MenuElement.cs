using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using UnityEngine;


public class MenuElement
{
    public float speed = 3.0f;

    private GameObject _element;
    private Vector3 _pos;
    private Vector3 _startPos;

    public MenuElement(GameObject element) {
    _element = element;
    _startPos = _element.transform.position;
    _pos = _startPos;
    }

    public void MoveTo(Vector3 destination)
    {
        _pos = destination;
    }

    public void MoveToStart() {
        _pos = _startPos;
    }

    public void Update() {
        _element.transform.position = Vector3.Lerp(_element.transform.position, _pos, Time.deltaTime*speed);
    }

}
