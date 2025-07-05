using UnityEngine;
using System.Collections.Generic;

public class ModelSwitcher : MonoBehaviour
{
    public List<GameObject> models = new List<GameObject>();
    private int currentIndex  = 0;

    void Awake()
    {
        foreach (Transform child in transform)
        {
            models.Add(child.gameObject);
        }
    }

    void Start()
    {
        if (models.Count == 0)
        {
            Debug.LogWarning("ModelSwitcher: There is no models for switch.");
            return;
        }

        for (int i = 0; i < models.Count; i++)
            models[i].SetActive(i == 0);

        currentIndex = 0;
    }

    void Update()
    {
        if (models.Count == 0)
            return;

        if (Input.GetKeyDown(KeyCode.Alpha1))
            SwitchTo(0);
        if (Input.GetKeyDown(KeyCode.Alpha2) && models.Count > 1)
            SwitchTo(1);
        if (Input.GetKeyDown(KeyCode.Alpha3) && models.Count > 2)
            SwitchTo(2);

        if (Input.GetKeyDown(KeyCode.Space))
        {
            int next = (currentIndex + 1) % models.Count;
            SwitchTo(next);
        }
    }

    private void SwitchTo(int idx)
    {
        if (idx < 0 || idx >= models.Count)
            return;

        for (int i = 0; i < models.Count; i++)
            models[i].SetActive(i == idx);

        currentIndex = idx;
        Debug.Log($"Switched to model: {models[idx].name}");
    }
}
