package com.pz.dynamoArmor.item.upgrade;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class WaterWalkingUpgrade extends AbsEffectUpgrade{
    public WaterWalkingUpgrade(Properties properties) {
        super(properties);
    }
    private enum Status {
        ON_GROUND, //地面
        IN_WATER, //水面上
        UNDER_WATER //水中
    }
    @Override
    public void applyEffect(Level level, Player entity, int count) {

        if (!level.isClientSide) {
            if (entity.isInWater()){

            }
        }
    }

}
