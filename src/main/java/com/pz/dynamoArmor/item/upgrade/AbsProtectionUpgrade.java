package com.pz.dynamoArmor.item.upgrade;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.function.Supplier;

public abstract class AbsProtectionUpgrade extends Item {
    private final Supplier<ItemAttributeModifiers> defaultModifiers;

    public AbsProtectionUpgrade(Properties properties) {
        super(properties);
        this.defaultModifiers = createModifiers();
    }

    public abstract Supplier<ItemAttributeModifiers> createModifiers();

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        return this.defaultModifiers.get();
    }
}
