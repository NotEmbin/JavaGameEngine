package embinmc.javaengine.math;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.raylib.Raylib;

import java.util.List;

public class IntVariation {
    public static final Codec<IntVariation> CODEC = Codec.either(
            Codec.INT,
            Codec.INT.listOf(2, 2).xmap(
                    value -> of(value.getFirst(), value.getLast()),
                    intv -> List.of(intv.getNum1(), intv.getNum2())
            )
    ).xmap(either -> either.map(IntVariation::of, v -> v), intv -> intv.canBeSingle() ? Either.left(intv.getRandomValue()) : Either.right(intv));
    public int number1;
    public int number2;

    private IntVariation(int num1, int num2) {
        this.number1 = num1;
        this.number2 = num2;
    }

    public static IntVariation of(int num1, int num2) {
        return new IntVariation(num1, num2);
    }

    public static IntVariation of(int number) {
        return new IntVariation(number, number);
    }

    public int applyVariationToInt(int base) {
        return base + getRandomValue();
    }

    public int getNum1() {
        return this.number1;
    }

    public int getNum2() {
        return this.number2;
    }

    public int getRandomValue() {
        if (this.canBeSingle()) return this.getNum1();
        return Raylib.GetRandomValue(this.number1, this.number2);
    }

    public boolean canBeSingle() {
        return this.getNum1() == this.getNum2();
    }
}
