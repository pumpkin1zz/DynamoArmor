package com.pz.dynamoArmor.item.armor;

import com.pz.dynamoArmor.register.ModDataComponent;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

import java.util.List;

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

        tooltipComponents.add(Component.translatable("tooltip.dynamo_armor.upgrade_slots", getUpgradeSlots()));

        ArmorUpgradesComponent upgradesComponent = getArmorUpgradesComponent(stack);
        if (upgradesComponent != null) {
            tooltipComponents.add(Component.literal("------------"));

            for (int i = 0; i < upgradesComponent.upgrades().size();i++){
                ResourceLocation id = upgradesComponent.upgrades().get(i);
                if (id != null) {
                    tooltipComponents.add(Component.literal("◆ ")
                            .append(Component.translatable("upgrade.dynamo_armor."+ id.getPath())));
                }else {
                    tooltipComponents.add(Component.literal("◇ ")
                            .append(Component.translatable("tooltip.dynamo_armor.empty_slot")));
                }
            }
        }


    }
    //获取升级模块组件
    public ArmorUpgradesComponent getArmorUpgradesComponent(ItemStack stack) {
        return stack.get(ModDataComponent.MODULE_ARMOR);
    }
}
