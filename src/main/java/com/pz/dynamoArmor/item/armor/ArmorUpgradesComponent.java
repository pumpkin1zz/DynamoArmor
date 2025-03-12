package com.pz.dynamoArmor.item.armor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record ArmorUpgradesComponent(int maxSlots, List<ResourceLocation> upgrades) {


    public static ArmorUpgradesComponent empty(int upgradeSlots){
        List<ResourceLocation> slots = new ArrayList<>(upgradeSlots);
        for (int i = 0; i < upgradeSlots; i++) {
            slots.add(null);
        }
        return new ArmorUpgradesComponent(upgradeSlots, slots);
    }

    public static final Codec<ArmorUpgradesComponent> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(
                            Codec.INT
                                    .fieldOf("maxSlots")
                                    .forGetter(ArmorUpgradesComponent::maxSlots),
                            ResourceLocation.CODEC.listOf()
                                    .fieldOf("upgrades")
                                    .forGetter(ArmorUpgradesComponent::upgrades)
                    ).apply(instance, ArmorUpgradesComponent::new)
            );

    public static final StreamCodec<ByteBuf, ArmorUpgradesComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ArmorUpgradesComponent::maxSlots,
            ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list()),
            ArmorUpgradesComponent::upgrades,
            ArmorUpgradesComponent::new
    );


}
