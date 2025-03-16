package com.pz.dynamoArmor.item.armor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pz.dynamoArmor.register.ModItem;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

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
