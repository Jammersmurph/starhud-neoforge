package fin.starhud.init;

import fin.starhud.Main;
import fin.starhud.config.Settings;
import fin.starhud.helper.AttackTracker;
import fin.starhud.hud.HUDComponent;
import fin.starhud.screen.EditHUDScreen;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.GameShuttingDownEvent;

public class EventInit {

    public static void init() {
        NeoForge.EVENT_BUS.addListener(EventInit::onEndTick);
        NeoForge.EVENT_BUS.addListener(EventInit::onRenderGui);

        NeoForge.EVENT_BUS.addListener((GameShuttingDownEvent event) ->
                AutoConfig.getConfigHolder(Settings.class).save()
        );
    }

    public static void onEndTick(ClientTickEvent.Post event) {
        Minecraft client = Minecraft.getInstance();

        AttackTracker.onEndTick(client);

        while (Main.openEditHUDKey.consumeClick()) {
            client.setScreen(new EditHUDScreen(Component.nullToEmpty("Edit HUD"), client.screen));
        }

        while (Main.toggleHUDKey.consumeClick()) {
            Main.settings.generalSettings.inGameSettings.disableHUDRendering = !Main.settings.generalSettings.inGameSettings.disableHUDRendering;
        }
    }

    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft client = Minecraft.getInstance();
        if (Main.settings == null || Main.settings.generalSettings == null || Main.settings.generalSettings.inGameSettings == null) return;
        if (Main.settings.generalSettings.inGameSettings.disableHUDRendering) return;
        if (client.options.hideGui) return;
        if (client.screen instanceof EditHUDScreen) return;

        HUDComponent.getInstance().collectAll();
        if (!HUDComponent.getInstance().getRenderedHUDs().isEmpty()) {
            HUDComponent.getInstance().renderAll(event.getGuiGraphics());
        }
    }
}
