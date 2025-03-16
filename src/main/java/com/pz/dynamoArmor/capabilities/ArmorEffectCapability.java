package com.pz.dynamoArmor.capabilities;

import com.pz.dynamoArmor.item.upgrade.AbsEffectUpgrade;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.UnknownNullability;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class ArmorEffectCapability implements IArmorEffectCapability{
    private final Map<Holder<Item>,Integer> effects = new HashMap<>();
    @Override
    public void addEffect(Holder<Item> effectId, int level) {
        effects.merge(effectId,level,Integer::sum);
    }

    @Override
    public void removeEffect(Holder<Item> effectId) {
        effects.remove(effectId);
    }

    @Override
    public boolean hasEffect(Holder<Item> effectId) {
        return effects.containsKey(effectId);
    }

    @Override
    public int getEffectLevel(Holder<Item> effectId) {
        return effects.getOrDefault(effectId,0);
    }

    @Override
    public void applyEffects(Level level, LivingEntity entity) {
        effects.forEach((effectId,level1) -> {
            ItemStack stack = new ItemStack(effectId);
            if (stack.getItem() instanceof AbsEffectUpgrade e){
                e.applyEffect(level,entity,level1);
            }
        });

    }

    @Override
    public void clearEffects() {
        effects.clear();
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        effects.forEach((effectId,level) -> tag.putInt(BuiltInRegistries.ITEM.getKey(effectId.value()).toString(),level));

        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        effects.clear();
        nbt.getAllKeys().forEach(key ->{
            Optional<Holder.Reference<Item>> holder = BuiltInRegistries.ITEM.getHolder(ResourceLocation.parse(key));
            holder.ifPresent(itemHolder -> effects.put(itemHolder,nbt.getInt(key)));
        });
    }
}
