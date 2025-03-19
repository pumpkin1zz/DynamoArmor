package com.pz.dynamoArmor.item.upgrade;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MagneticUpgrade extends AbsEffectUpgrade{
    private static final int BASE_RANGE = 3;
    private static final int RANGE_PER_LEVEL = 1;
    public MagneticUpgrade(Properties properties) {
        super(properties);
    }



    @Override
    public void applyEffect(Level level, Player entity, int count) {
        int range = BASE_RANGE + RANGE_PER_LEVEL * count;
        if (!level.isClientSide) {
            level.getEntitiesOfClass(ItemEntity.class,entity.getBoundingBox().inflate(range),
                    itemEntity -> !itemEntity.hasPickUpDelay())
                    .forEach(item -> {
                        Vec3 motion = entity.position().subtract(item.position()).normalize().scale(0.1);
                        item.setDeltaMovement(motion);

                    });
        }

    }


}
