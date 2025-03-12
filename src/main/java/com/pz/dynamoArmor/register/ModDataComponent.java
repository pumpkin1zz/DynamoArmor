package com.pz.dynamoArmor.register;

import com.pz.dynamoArmor.Dynamo_armor;
import com.pz.dynamoArmor.item.armor.ArmorUpgradesComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponent {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Dynamo_armor.MODID);

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<ArmorUpgradesComponent>> MODULE_ARMOR =DATA_COMPONENTS.register(
            "module_armor",
            ()-> DataComponentType.<ArmorUpgradesComponent>builder().persistent(ArmorUpgradesComponent.CODEC).networkSynchronized(ArmorUpgradesComponent.STREAM_CODEC).build()
    );
}