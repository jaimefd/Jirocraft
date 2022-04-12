package me.jiroscopio.jirocraftplugin.helpers;

import java.util.ArrayList;
import java.util.Random;

public class ZoneGenerator {

    private final int[][] zones;
    private final int size;
    private final int difficulty;

    public ZoneGenerator(int size, int difficulty) {
        this.zones = new int[size][size]; // which will be 32x32
        this.size = size;
        this.difficulty = difficulty; // max difficulty, will be 7 for now

        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++)
                this.zones[i][j] = 1;
    }

    public int getDistance(int x, int z) {
        int i, j;
        int middle = this.size/2;
        if (this.size%2 == 0) {
            if (x < middle) i = middle - x - 1;
            else i = x - middle;
            if (z < middle) j = middle - z - 1;
            else j = z - middle;
        } else {
            i = Math.abs(x - middle);
            j = Math.abs(z - middle);
        }
        return Math.max(i,j);
    }

    public void increaseZone(int x, int z, int[][] oldZones) {
        boolean increase = false;
        int neighbours = 0; int count = 0; int average;
        int radius = (this.size+1)/2 - 1;

        if (radius == 0) return;

        if (x == 0) {
            neighbours += oldZones[1][z];
            count++;
        } else if (x == this.size-1) {
            neighbours += oldZones[this.size-2][z];
            count++;
        } else {
            neighbours += oldZones[x+1][z] + oldZones [x-1][z];
            count += 2;
        }

        if (z == 0) {
            neighbours += oldZones[x][1];
            count++;
        } else if (z == this.size-1) {
            neighbours += oldZones[x][this.size-2];
            count++;
        } else {
            neighbours += oldZones[x][z+1] + oldZones [x][z-1];
            count += 2;
        }

        average = Math.round((float)neighbours / count);
        int distance = getDistance(x,z);

        int increasePercentage = Math.round(((float)Math.pow(distance,2)/(int)Math.pow(radius,2))*90) + (average - oldZones[x][z])*10;
        Random random = new Random();
        if (random.nextInt(100) < increasePercentage) this.zones[x][z]++;
    }

    public void iterateDifficulty() {
        // from -16,-16 to 15,15
        int[][] oldZones = new int[this.size][this.size];
        for (int i = 0; i < this.size; i++)
            System.arraycopy(this.zones[i], 0, oldZones[i], 0, this.size);

        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++)
                increaseZone(i,j, oldZones);
    }

    public void generateZones() {
        for (int i = 0; i < this.difficulty - 1; i++)
            iterateDifficulty();
    }

    public void printZones() {
        int[] amounts = new int[difficulty];
        for (int i = 0; i < difficulty; i++)
            amounts[i] = 0;

        for (int i = 0; i < this.size - 1; i++) {
            for (int j = 1; j < this.size - 1; j++) {
                System.out.print(this.zones[i][j] + " ");
                amounts[this.zones[i][j]-1]++;
            }
            System.out.print(this.zones[i][this.size - 1] + "\n");
            amounts[this.zones[i][this.size - 1]-1]++;
        }

        for (int j = 1; j < this.size - 1; j++) {
            System.out.print(this.zones[this.size-1][j] + " ");
            amounts[this.zones[this.size-1][j]-1]++;
        }
        System.out.print(this.zones[this.size-1][this.size - 1] + "\n");
        amounts[this.zones[this.size-1][this.size - 1]-1]++;

        for (int i = 0; i < difficulty; i++)
            System.out.println("Difficulty " + (i+1) + " zones: " + amounts[i]);
    }

    public void resetZones() {
        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++)
                this.zones[i][j] = 1;

        this.generateZones();
    }

}
