package me.rina.rocan.mixin.mixins;

import me.rina.rocan.Rocan;
import me.rina.rocan.mixin.interfaces.IMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author SrRina
 * @since 16/11/20 at 10:05pm
 */
@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements IMinecraft {
    @Inject(method = "shutdown", at = @At("HEAD"))
    public void onShutdown(CallbackInfo callbackInfo) {
        Rocan.onEndClient();
    }

    @Redirect(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayCrashReport(Lnet/minecraft/crash/CrashReport;)V"))
    public void onRun(Minecraft minecraft, CrashReport crashReport) {
        Rocan.onEndClient();
    }

    @Redirect(at = @At(
        value = "INVOKE",
        target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V"),
        method = "createDisplay")
    public void
    createDisplay(String name) {
        Display.setTitle(Rocan.NAME + " " + Rocan.VERSION);
    }

    @Accessor
    @Override
    public abstract void setRightClickDelayTimer(int delay);
}
