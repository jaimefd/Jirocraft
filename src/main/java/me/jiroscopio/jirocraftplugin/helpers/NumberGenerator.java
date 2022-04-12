package me.jiroscopio.jirocraftplugin.helpers;

import java.util.Random;

public record NumberGenerator(String type, int min, int max, int chance) {

    public int generateNumber(Random random) {
        int number = min;

        switch (type) {
            case "binomial":
                for (int i = min; i < max; i++) {
                    if (random.nextInt(100) < chance) number++;
                }
                break;
            case "poisson":
                while (random.nextInt(100) < chance) number++;
                break;
        }

        return number;
    }

}
