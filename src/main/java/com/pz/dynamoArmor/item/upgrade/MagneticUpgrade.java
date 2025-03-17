package com.pz.dynamoArmor.item.upgrade;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class MagneticUpgrade extends AbsEffectUpgrade{
    public MagneticUpgrade(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canApplyToSlot(EquipmentSlot slot) {
        return EquipmentSlotGroup.ANY.test(slot);
    }

    @Override
    public void applyEffect(Level level, Entity entity, int count) {
        entity.sendSystemMessage(Component.literal(String.valueOf(count)));
    }


}
