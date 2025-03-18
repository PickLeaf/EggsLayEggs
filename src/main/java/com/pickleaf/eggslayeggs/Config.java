package com.pickleaf.eggslayeggs;

public class Config {
    public static float getChancePerEgg() {
        return chancePerEgg;
    }

    public static void setChancePerEgg(float chancePerEgg) {
        if (Config.chancePerEgg != Float.MIN_VALUE) return;
        Config.chancePerEgg = chancePerEgg;
    }

    private static float chancePerEgg = Float.MIN_VALUE;
}
