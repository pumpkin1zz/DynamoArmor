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

public class ProtectionUpgrade extends AbsProtectionUpgrade {

    private final double armor;
    private final double armorToughness;
    private final double knockbackResistance;
    private final double movementSpeed;

    public ProtectionUpgrade(Properties properties,
                             double armor,
                             double armorToughness,
                             double knockbackResistance,
                             double movementSpeed,
                             double attackSpeed) {
        super(properties.stacksTo(8));
        this.armor = armor;
        this.armorToughness = armorToughness;
        this.knockbackResistance = knockbackResistance;
        this.movementSpeed = movementSpeed;
    }

    @Override
    public Supplier<ItemAttributeModifiers> createModifiers() {
        return Suppliers.memoize(()->{
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
            builder.add(Attributes.ARMOR,new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Dynamo_armor.MODID,"upgrade"+UUID.randomUUID()),armor, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.ANY);
            builder.add(Attributes.ARMOR,new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Dynamo_armor.MODID,"upgrade"+UUID.randomUUID()),armor, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.ANY);
            builder.add(Attributes.ARMOR_TOUGHNESS,new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Dynamo_armor.MODID,"upgrade"+UUID.randomUUID()),armorToughness, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.ANY);
            builder.add(Attributes.ARMOR_TOUGHNESS,new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Dynamo_armor.MODID,"upgrade"+UUID.randomUUID()),armorToughness, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.ANY);
            return builder.build();
        });
    }

}
