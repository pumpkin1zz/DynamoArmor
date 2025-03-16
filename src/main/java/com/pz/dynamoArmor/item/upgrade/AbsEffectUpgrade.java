package com.pz.dynamoArmor.item.upgrade;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public abstract class AbsEffectUpgrade extends Item {
    public AbsEffectUpgrade(Properties properties) {
        super(properties);
    }


    public abstract Boolean canApplyToSlot(EquipmentSlot slot);

    public abstract void applyEffect(Level level, Entity entity);
    public abstract void applyEffect(Level level, Entity entity,int count);
}
