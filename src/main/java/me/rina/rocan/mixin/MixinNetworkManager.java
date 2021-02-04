package me.rina.rocan.mixin;

import io.netty.channel.ChannelHandlerContext;
import me.rina.rocan.Rocan;
import me.rina.rocan.client.event.network.ReceiveEventPacket;
import me.rina.rocan.client.event.network.SendEventPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author SrRina
 * @since 16/11/20 at 10:05pm
 */
@Mixin(NetworkManager.class)
public class MixinNetworkManager {
    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    public void onSendPacket(Packet<?> packet, CallbackInfo callbackInfo) {
        SendEventPacket event = new SendEventPacket(packet);

        Rocan.getPomeloEventManager().dispatchEvent(event);

        if (event.isCanceled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    public void onReceivePacket(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callbackInfo) {
        ReceiveEventPacket event = new ReceiveEventPacket(packet);

        Rocan.getPomeloEventManager().dispatchEvent(event);

        if (event.isCanceled()) {
            callbackInfo.cancel();
        }
    }
}
