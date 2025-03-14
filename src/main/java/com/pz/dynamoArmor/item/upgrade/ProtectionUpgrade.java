package com.pz.dynamoArmor.item.upgrade;

import com.google.common.base.Suppliers;
import com.pz.dynamoArmor.Dynamo_armor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.UUID;
import java.util.function.Supplier;

public class ProtectionUpgrade extends Item {

    private final Supplier<ItemAttributeModifiers> defaultModifiers;
    public ProtectionUpgrade(Properties properties,
                             double armor,
                             double armorToughness,
                             double knockbackResistance,
                             double movementSpeed,
                             double attackSpeed) {
        super(properties);
        this.defaultModifiers = Suppliers.memoize(()->{
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
            ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(Dynamo_armor.MODID,"upgrade");

            builder.add(Attributes.ARMOR,new AttributeModifier(resourceLocation,armor, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.ANY);

            return builder.build();
        });
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        return this.defaultModifiers.get();
    }
}
