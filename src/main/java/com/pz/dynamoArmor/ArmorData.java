package com.pz.dynamoArmor;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.ArrayList;
import java.util.List;

public class ArmorData implements INBTSerializable<CompoundTag> {
    public final List<Holder<Item>> armorData = new ArrayList<>();

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        ListTag list = new ListTag();

        armorData.forEach(holder -> {
            CompoundTag itemTag = new CompoundTag();
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(holder.value());
            itemTag.putString("id", id.toString());
            list.add(itemTag);
        });

        tag.put("items", list);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        armorData.clear();
        ListTag list = nbt.getList("items", Tag.TAG_COMPOUND);

        list.forEach(element -> {
            CompoundTag itemTag = (CompoundTag) element;
            ResourceLocation id = ResourceLocation.parse(itemTag.getString("id"));
            BuiltInRegistries.ITEM.getHolder(id).ifPresent(armorData::add);
        });
    }
}
