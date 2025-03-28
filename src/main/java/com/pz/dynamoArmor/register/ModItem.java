package com.pz.dynamoArmor.register;

import com.pz.dynamoArmor.Dynamo_armor;
import com.pz.dynamoArmor.item.armor.ArmorUpgradesComponent;
import com.pz.dynamoArmor.item.armor.ModArmorMaterial;
import com.pz.dynamoArmor.item.armor.ModularArmorItem;
import com.pz.dynamoArmor.item.upgrade.MagneticUpgrade;
import com.pz.dynamoArmor.item.upgrade.ProtectionUpgrade;
import com.pz.dynamoArmor.item.upgrade.WaterWalkingUpgrade;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems( Dynamo_armor.MODID);
    public static final Supplier<Item> EMPTY = ITEMS.register("empty",()->new Item(new Item.Properties()));

    public static final DeferredHolder<Item, ArmorItem> COPPER_HELMET = ITEMS.register(
            "copper_helmet",
            ()->new ArmorItem(ModArmorMaterial.COPPER, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15)))
    );
    public static final DeferredHolder<Item, ArmorItem> COPPER_CHESTPLATE = ITEMS.register(
            "copper_chestplate",
            ()->new ModularArmorItem(ModArmorMaterial.COPPER, ArmorItem.Type.CHESTPLATE, new Item.Properties()
                    .component(ModDataComponent.MODULE_ARMOR, ArmorUpgradesComponent.empty(5))
                    .durability(ArmorItem.Type.CHESTPLATE.getDurability(15)),5)
    );
    public static final DeferredHolder<Item, ArmorItem> COPPER_LEGGINGS = ITEMS.register(
            "copper_leggings",
            ()->new ArmorItem(ModArmorMaterial.COPPER, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(15)))
    );
    public static final DeferredHolder<Item, ArmorItem> COPPER_BOOTS = ITEMS.register(
            "copper_boots",
            ()->new ArmorItem(ModArmorMaterial.COPPER, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(15)))
    );
    public static final Supplier<ProtectionUpgrade> PROTECTION_UPGRADE = ITEMS.register(
            "protection_upgrade",
            ()->new ProtectionUpgrade(new Item.Properties(),0.5,0.1,2.0,2.0,2.0)
    );

    public static final Supplier<MagneticUpgrade> TEXT = ITEMS.register(
            "magnetic_upgrade",
            ()->new MagneticUpgrade(new Item.Properties())
    );

    public static final Supplier<WaterWalkingUpgrade> TEXT2 = ITEMS.register(
            "water_walking_upgrade",
            ()->new WaterWalkingUpgrade(new Item.Properties())
    );


}
