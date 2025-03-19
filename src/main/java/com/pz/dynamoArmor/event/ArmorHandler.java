package com.pz.dynamoArmor.event;

import com.pz.dynamoArmor.Dynamo_armor;
import com.pz.dynamoArmor.item.armor.ArmorUpgradesComponent;
import com.pz.dynamoArmor.item.armor.ModularArmorItem;
import com.pz.dynamoArmor.item.upgrade.AbsEffectUpgrade;
import com.pz.dynamoArmor.register.ModAttachmentType;
import com.pz.dynamoArmor.register.ModDataComponent;
import com.pz.dynamoArmor.register.ModItem;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@EventBusSubscriber(modid = Dynamo_armor.MODID)
public class ArmorHandler {

    @SubscribeEvent
    public static void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        if (!(event.getEntity() instanceof Player entity)) return;
        EquipmentSlot slot = event.getSlot();
        ItemStack from = event.getFrom();
        ItemStack to = event.getTo();

        if (!slot.isArmor()) return;
        if (to.getItem() instanceof ModularArmorItem) {
            List<Holder<Item>> data = entity.getData(ModAttachmentType.ARMOR_DATA);
            List<Holder<Item>> newData = new ArrayList<>(data);
            ArmorUpgradesComponent upgradesComponent = to.get(ModDataComponent.MODULE_ARMOR);
            for (Holder<Item> upgrade : upgradesComponent.upgrades()) {
                if (!(new ItemStack(upgrade)).is(ModItem.EMPTY.get())) newData.add(upgrade);
            }
            entity.setData(ModAttachmentType.ARMOR_DATA,newData);
        }
        if (from.getItem() instanceof ModularArmorItem) {
            List<Holder<Item>> data = entity.getData(ModAttachmentType.ARMOR_DATA);
            List<Holder<Item>> newData = new ArrayList<>(data);
            ArmorUpgradesComponent upgradesComponent = from.get(ModDataComponent.MODULE_ARMOR);
            for (Holder<Item> upgrade : upgradesComponent.upgrades()) {
                if (!(new ItemStack(upgrade)).is(ModItem.EMPTY.get())) newData.remove(upgrade);
            }
            entity.setData(ModAttachmentType.ARMOR_DATA,newData);
        }
    }

    @SubscribeEvent
    public static void onPlayerTickEvent(PlayerTickEvent.Post event) {
        Player entity = event.getEntity();
        Level level = entity.level();
        Map<Holder<Item>, Long> effect = new HashMap<>();
        List<Holder<Item>> data = entity.getData(ModAttachmentType.ARMOR_DATA);
        effect = data.stream()
                .filter(itemHolder -> itemHolder.value() instanceof AbsEffectUpgrade)
                .collect(Collectors.groupingBy(itemHolder -> itemHolder,Collectors.counting()));

        effect.forEach((itemHolder, aLong) -> {
            if (itemHolder.value() instanceof AbsEffectUpgrade e) {
                e.applyEffect( level,entity, aLong.intValue());
            }
        });
    }

}
