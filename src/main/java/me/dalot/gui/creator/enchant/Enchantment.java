package me.dalot.gui.creator.enchant;

public class Enchantment {
    // Enchantments: [{id:"minecraft:efficiency", lvl: 1s}, {id:"minecraft:blast_protection", lvl: 1s}
    private EnchantmentEnum enchantment;
    private int level;

    public Enchantment(EnchantmentEnum enchantment) {
        this(enchantment, 1);
    }

    public Enchantment(EnchantmentEnum enchantment, int level) {
        this.enchantment = enchantment;
        this.level = level;
    }

    public EnchantmentEnum getEnchantment() {
        return enchantment;
    }

    public int getLevel() {
        return level;
    }

    public String toString() {
        return "{id: \"" + enchantment.getString() + "\", lvl: " + level + "s}";
    }
}
