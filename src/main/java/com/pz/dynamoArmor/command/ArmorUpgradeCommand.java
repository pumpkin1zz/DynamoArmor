package com.pz.dynamoArmor.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.pz.dynamoArmor.item.armor.ArmorUpgradesComponent;
import com.pz.dynamoArmor.item.armor.ModularArmorItem;
import com.pz.dynamoArmor.register.ModDataComponent;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ArmorUpgradeCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("armorupgrade")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("slot", IntegerArgumentType.integer(0))
                        .executes(context -> {
                            Player player = context.getSource().getPlayerOrException();
                            ItemStack mainHand = player.getMainHandItem();
                            ItemStack offHand = player.getOffhandItem();
                            if (!(mainHand.getItem() instanceof ModularArmorItem)) {
                                context.getSource().sendFailure(Component.literal("1"));
                                return 0;
                            }
                            if (offHand.isEmpty()) {
                                context.getSource().sendFailure(Component.literal("2"));
                                return 0;
                            }
                            Integer slot = context.getArgument("slot", Integer.class);
                            ArmorUpgradesComponent upgradesComponent = mainHand.get(ModDataComponent.MODULE_ARMOR);

                            ArmorUpgradesComponent newComponent = ArmorUpgradesComponent.empty(upgradesComponent.upgrades().size());
                            for (int i = 0; i < upgradesComponent.upgrades().size(); i++) {
                                if (i == slot) {
                                    newComponent.upgrades().set(i, offHand.copy());
                                }else{
                                    newComponent.upgrades().set(i,upgradesComponent.upgrades().get(i).copy());
                                }
                            }
                            mainHand.set(ModDataComponent.MODULE_ARMOR, newComponent);
                            return 1;
                        })));
    }

}
