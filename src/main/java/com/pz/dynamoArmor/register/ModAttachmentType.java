package com.pz.dynamoArmor.register;

import com.mojang.serialization.Codec;
import com.pz.dynamoArmor.Dynamo_armor;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import oshi.util.tuples.Pair;

import java.util.*;
import java.util.function.Supplier;

public class ModAttachmentType {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Dynamo_armor.MODID);



    public static final Supplier<AttachmentType<List<Holder<Item>>>> ARMOR_DATA = ATTACHMENT_TYPES.register("armor_data",
            () -> AttachmentType.<List<Holder<Item>>>builder(()->new ArrayList<>())
//                    .serialize(Codec.list(ItemStack.ITEM_NON_AIR_CODEC)).copyOnDeath()
                    .build());

}