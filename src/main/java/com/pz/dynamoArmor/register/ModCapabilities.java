package com.pz.dynamoArmor.register;

import com.pz.dynamoArmor.Dynamo_armor;
import com.pz.dynamoArmor.capabilities.ArmorEffectCapabilityProvider;
import com.pz.dynamoArmor.capabilities.IArmorEffectCapability;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = Dynamo_armor.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModCapabilities {
    public static final EntityCapability<IArmorEffectCapability,Void> ARMOR_EFFECT_CAPABILITY = EntityCapability.createVoid(
            ResourceLocation.fromNamespaceAndPath(Dynamo_armor.MODID,"armor_effect"),
 IArmorEffectCapability.class
    );
    @SubscribeEvent
    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
    event.registerEntity(ARMOR_EFFECT_CAPABILITY, EntityType.PLAYER, new ArmorEffectCapabilityProvider());
    }
}
