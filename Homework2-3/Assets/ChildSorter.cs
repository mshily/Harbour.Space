using System.Collections;
using System.Collections.Generic;
using System.Linq;
using UnityEngine;

public class ChildSorter : MonoBehaviour
{
    public List<Transform> children = new List<Transform>();

    public Vector2 randomYRange = new Vector2(0.5f, 3f);

    public Vector3 startPosition = Vector3.zero;

    public float spacing = 2f;

    void Start()
    {
        InitializeChildrenList();
        RandomizeScales();
        SortAndRepositionBubble();
    }

    void InitializeChildrenList()
    {
        if (children == null || children.Count == 0)
        {
            children = GetComponentsInChildren<Transform>()
                       .Where(t => t != this.transform)
                       .ToList();
        }
    }

    void RandomizeScales()
    {
        foreach (var t in children)
        {
            Vector3 s = t.localScale;
            s.y = Random.Range(randomYRange.x, randomYRange.y);
            t.localScale = s;
        }
    }

    void SortAndRepositionBubble()
    {
        int n = children.Count;
        if (n <= 1) return;

        for (int i = 0; i < n - 1; i++)
        {
            for (int j = 0; j < n - 1 - i; j++)
            {
                if (children[j].localScale.y > children[j + 1].localScale.y)
                {
                    Transform tmp = children[j];
                    children[j] = children[j + 1];
                    children[j + 1] = tmp;
                }
            }
        }

        for (int i = 0; i < n; i++)
        {
            Vector3 pos = startPosition + Vector3.right * spacing * i;
            children[i].position = pos;
        }
    }
}
