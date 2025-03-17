package com.pz.dynamoArmor.capabilities;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

public class ArmorEffectCapabilityProvider implements ICapabilityProvider<Player,Void,IArmorEffectCapability>, INBTSerializable<CompoundTag> {
    private final IArmorEffectCapability capability;
    public ArmorEffectCapabilityProvider() {
        this.capability = new ArmorEffectCapability();
    }
    @Override
    public @Nullable IArmorEffectCapability getCapability(Player object, Void context) {
        return capability;
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return capability.serializeNBT(provider);
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        capability.deserializeNBT(provider,nbt);
    }
}
