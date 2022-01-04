package com.company;

import Objects.RenderObject;
import Renderer.LevelData;

import java.awt.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Color[][] texture = new Color[1][2];
        texture[0][0] = Color.yellow;
        texture[0][1] = Color.red;

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
            // Note: I used the ArrayList<RenderObject>, boolean[][], color, int, int constructor here.
        LevelData.init(objects, new boolean[60][30], Color.BLUE, 60, 30);

        int reps = 0;
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {;}

            LevelData.apply(new RenderObject(5 + reps, reps + 5, new Color[][]{{Color.red, Color.magenta}, {Color.green, Color.lightGray}}));
            reps++;
        }
    }
}
