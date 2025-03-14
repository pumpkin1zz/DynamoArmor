package com.pz.dynamoArmor.item.armor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pz.dynamoArmor.register.ModItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.fml.common.Mod;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record ArmorUpgradesComponent(String id,List<ItemStack> upgrades) {


    public static ArmorUpgradesComponent empty(int upgradeSlots){
        List<ItemStack> slots = new ArrayList<>(upgradeSlots);
        for (int i = 0; i < upgradeSlots; i++) {
            slots.add(ModItem.EMPTY.get().getDefaultInstance());
        }
        return new ArmorUpgradesComponent("armor_upgrades",slots);
    }

    public static final Codec<ArmorUpgradesComponent> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(
                            Codec.STRING.fieldOf("id").forGetter(ArmorUpgradesComponent::id),
                            ItemStack.CODEC.listOf()
                                    .fieldOf("upgrades")
                                    .forGetter(ArmorUpgradesComponent::upgrades)
                    ).apply(instance, ArmorUpgradesComponent::new)
            );

    public static final StreamCodec<RegistryFriendlyByteBuf, ArmorUpgradesComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            ArmorUpgradesComponent::id,
            ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()),
            ArmorUpgradesComponent::upgrades,
            ArmorUpgradesComponent::new
    );


}
