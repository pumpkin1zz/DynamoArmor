package com.pz.dynamoArmor.item.armor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pz.dynamoArmor.register.ModItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public record ArmorUpgradesComponent(String id,List<Holder<Item>> upgrades) {



    public static ArmorUpgradesComponent empty(int upgradeSlots){
        List<Holder<Item>> slots = new ArrayList<>(upgradeSlots);
        for (int i = 0; i < upgradeSlots; i++) {
            slots.add(BuiltInRegistries.ITEM.wrapAsHolder(ModItem.EMPTY.get()));
        }
        return new ArmorUpgradesComponent("armor_upgrades",slots);
    }

    public static final Codec<ArmorUpgradesComponent> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(
                            Codec.STRING.fieldOf("id").forGetter(ArmorUpgradesComponent::id),
                            ItemStack.ITEM_NON_AIR_CODEC.listOf()
                                    .fieldOf("upgrades")
                                    .forGetter(ArmorUpgradesComponent::upgrades)
                    ).apply(instance, ArmorUpgradesComponent::new)
            );

    public static final StreamCodec<RegistryFriendlyByteBuf, ArmorUpgradesComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            ArmorUpgradesComponent::id,
            ByteBufCodecs.holderRegistry(Registries.ITEM).apply(ByteBufCodecs.list()),
            ArmorUpgradesComponent::upgrades,
            ArmorUpgradesComponent::new
    );


}
