package com.company;

import Objects.RenderObject;
import Renderer.LevelData;

import java.awt.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Color[][] texture = new Color[1][1];
        texture[0][0] = Color.yellow;

        ArrayList<RenderObject> objects = new ArrayList<>();

        // add face
        int faceHeight = 3;
        objects.add(new RenderObject(0, 0, texture));
        objects.add(new RenderObject(3, 0, texture));
        objects.add(new RenderObject(0, faceHeight, texture));
        objects.add(new RenderObject(1, faceHeight, texture));
        objects.add(new RenderObject(2, faceHeight, texture));
        objects.add(new RenderObject(3, faceHeight, texture));
        objects.add(new RenderObject(0, faceHeight, texture));

        // Start up the display
            // Note: I converted the arraylist within the constructor here,
            // TODO: Make an init() overload which accepts the same arguments, just with an Arraylist instead of an Array.
        LevelData.init(objects.toArray(new RenderObject[0]), new boolean[10][10], Color.black, 60, 30);
    }
}
