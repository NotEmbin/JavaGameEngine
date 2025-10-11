package embinmc.javaengine.math;

import java.util.Random;
import java.util.random.RandomGenerator;

public class JeRandom extends Random {
    private static final Random INSTANCE = Random.from(RandomGenerator.getDefault());

    public static int getRandomIntBetween(Random random, int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static int getRandomIntBetween(int min, int max) {
        return JeRandom.getRandomIntBetween(JeRandom.get(), min, max);
    }

    public static Random get() {
        return JeRandom.INSTANCE;
    }
}
