package com.pz.dynamoArmor.event;

import com.pz.dynamoArmor.Dynamo_armor;
import com.pz.dynamoArmor.NetWorking.ArmorDataPayload;
import com.pz.dynamoArmor.NetWorking.ClientPayloadHandler;
import com.pz.dynamoArmor.NetWorking.ServerPayloadHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Dynamo_armor.MODID,bus = EventBusSubscriber.Bus.MOD)
public class PayloadHandlersEvent {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(Dynamo_armor.MODID+"1");
        registrar.playBidirectional(
                ArmorDataPayload.TYPE,
                ArmorDataPayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        ClientPayloadHandler::handleDataOnMain,
                        ServerPayloadHandler::handleDataOnMain
                )
        );
    }
}
