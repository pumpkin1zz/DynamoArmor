package com.pz.dynamoArmor.item.armor;

import com.pz.dynamoArmor.Dynamo_armor;
import com.pz.dynamoArmor.item.upgrade.AbsEffectUpgrade;
import com.pz.dynamoArmor.item.upgrade.AbsProtectionUpgrade;
import com.pz.dynamoArmor.item.upgrade.ProtectionUpgrade;
import com.pz.dynamoArmor.register.ModDataComponent;
import com.pz.dynamoArmor.register.ModItem;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class ModularArmorItem extends ArmorItem {
    private final int upgradeSlots; // 升级槽位数量

    public ModularArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties, int upgradeSlots) {
        super(material, type, properties);
        this.upgradeSlots = upgradeSlots;

    }

    public int getUpgradeSlots() {
        return upgradeSlots;
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        tooltipComponents.add(Component.translatable("tooltip.dynamo_armor.upgrade_slots"));

        ArmorUpgradesComponent upgradesComponent = getArmorUpgradesComponent(stack);
        if (upgradesComponent != null) {
            tooltipComponents.add(Component.literal("---------------"));

            for (int i = 0; i < upgradesComponent.upgrades().size();i++){
                ItemStack id = new ItemStack(upgradesComponent.upgrades().get(i) );
                Item id2 = ModItem.EMPTY.get();
                if (!id.is(id2)) {
                    tooltipComponents.add(Component.literal("◆ ")
                            .append(Component.translatable("upgrade.dynamo_armor."+ BuiltInRegistries.ITEM.getKey(id.getItem()).getPath())));
                }else {
                    tooltipComponents.add(Component.literal("◇ ")
                            .append(Component.translatable("tooltip.dynamo_armor.empty")));
                }
            }
        }
    }

    @Override
    public @NotNull ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        ItemAttributeModifiers defaultAttributeModifiers = super.getDefaultAttributeModifiers(stack);
        ArmorUpgradesComponent component = getArmorUpgradesComponent(stack);
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        EquipmentSlotGroup slotGroup = EquipmentSlotGroup.bySlot(this.getType().getSlot());


        Map<Holder<Attribute>, List<AttributeModifier>> addValueMods = new HashMap<>();
        Map<Holder<Attribute>, List<AttributeModifier>> multiplyBaseMods = new HashMap<>();
        Map<Holder<Attribute>, List<AttributeModifier>> multiplyTotalMods = new HashMap<>();

        defaultAttributeModifiers.modifiers().forEach(entry -> {
            switch (entry.modifier().operation()){
                case ADD_VALUE -> addValueMods.computeIfAbsent(entry.attribute(),k->new ArrayList<>()).add(entry.modifier());
                case ADD_MULTIPLIED_BASE -> multiplyBaseMods.computeIfAbsent(entry.attribute(),k->new ArrayList<>()).add(entry.modifier());
                case ADD_MULTIPLIED_TOTAL -> multiplyTotalMods.computeIfAbsent(entry.attribute(),k->new ArrayList<>()).add(entry.modifier());
            }
        });


        component.upgrades().stream()
                .filter(upgrade-> new ItemStack(upgrade).getItem() instanceof AbsProtectionUpgrade)
                .forEach(upgrade->{
                    new ItemStack(upgrade).getAttributeModifiers().modifiers().forEach(entry -> {
                        if (entry.slot().test(this.getEquipmentSlot())) {
                            switch (entry.modifier().operation()){
                                case ADD_VALUE -> addValueMods.computeIfAbsent(entry.attribute(),k->new ArrayList<>()).add(entry.modifier());
                                case ADD_MULTIPLIED_BASE -> multiplyBaseMods.computeIfAbsent(entry.attribute(),k->new ArrayList<>()).add(entry.modifier());
                                case ADD_MULTIPLIED_TOTAL -> multiplyTotalMods.computeIfAbsent(entry.attribute(),k->new ArrayList<>()).add(entry.modifier());
                            }
                        }
                    });
                });

        addValueMods.forEach((a,l) -> {
            double addValue = l.stream().mapToDouble(AttributeModifier::amount).sum();
            if (addValue !=0) builder.add(a,new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Dynamo_armor.MODID,"armor"),addValue, AttributeModifier.Operation.ADD_VALUE),slotGroup);
        });
        multiplyBaseMods.forEach((a,l) -> {
            double multiplyBase = l.stream().mapToDouble(AttributeModifier::amount).sum();
            if (multiplyBase !=0) builder.add(a,new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Dynamo_armor.MODID,"armor_base"),multiplyBase, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),slotGroup);
        });
        multiplyTotalMods.forEach((a,l) -> {
            double multiplyTotal = l.stream().mapToDouble(AttributeModifier::amount).sum();
            if (multiplyTotal !=0) builder.add(a,new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Dynamo_armor.MODID,"armor_total"),multiplyTotal, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),slotGroup);
        });


        return builder.build();
    }


    public ArmorUpgradesComponent getArmorUpgradesComponent(ItemStack stack) {
        return stack.get(ModDataComponent.MODULE_ARMOR);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        EquipmentSlot slot = this.getType().getSlot();
        ArmorUpgradesComponent component = getArmorUpgradesComponent(stack);
        if (component != null){
            Map<Holder<Item>, Long> collect = component.upgrades().stream().collect(Collectors.groupingBy(Holder<Item>::getDelegate, Collectors.counting()));
            collect.forEach(((itemHolder, aLong) -> {
                if (itemHolder.is(BuiltInRegistries.ITEM.getKey(ModItem.EMPTY.get()))) return;
                if (itemHolder instanceof AbsEffectUpgrade e){

                }
            }));
        }


    }
}
