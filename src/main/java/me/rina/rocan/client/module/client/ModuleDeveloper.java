package me.rina.rocan.client.module.client;

import me.rina.rocan.api.module.Module;
import me.rina.rocan.api.module.impl.ModuleCategory;
import me.rina.rocan.api.module.registry.Registry;
import me.rina.rocan.api.setting.value.ValueColor;
import me.rina.rocan.api.setting.value.ValueNumber;
import me.rina.rocan.api.util.client.NullUtil;
import me.rina.rocan.client.event.client.ClientTickEvent;
import net.minecraft.item.ItemStack;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.awt.*;

/**
 * @author SrRina
 * @since 20/02/2021 at 00:18
 **/
@Registry(name = "Developer", tag = "Developer", description = "Test stuff!", category = ModuleCategory.CLIENT)
public class ModuleDeveloper extends Module {
    public static ValueColor colorPicker = new ValueColor("Color", "Color", "Color picker.", new Color(255, 0, 255));


    @Listener
    public void onListenClientTickEvent(ClientTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
    }
}
