package com.pz.dynamoArmor.NetWorking;

import com.pz.dynamoArmor.Dynamo_armor;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.List;

public record ArmorDataPayload(List<Holder<Item>> items) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ArmorDataPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Dynamo_armor.MODID, "armor_data"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ArmorDataPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.holderRegistry(Registries.ITEM).apply(ByteBufCodecs.list()),
            ArmorDataPayload::items,
            ArmorDataPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
