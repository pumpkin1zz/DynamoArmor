package com.pz.dynamoArmor.capabilities;

import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.INBTSerializable;



public interface IArmorEffectCapability extends INBTSerializable<CompoundTag> {

    void addEffect(Holder<Item> effectId, int level);

    void removeEffect(Holder<Item> effectId);

    boolean hasEffect(Holder<Item> effectId);

    int getEffectLevel(Holder<Item> effectId);

    void applyEffects(Level level , LivingEntity entity);

    void clearEffects();
}
