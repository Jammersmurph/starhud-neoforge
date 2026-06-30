package fin.starhud;

import fin.starhud.config.Settings;
import fin.starhud.hud.HUDComponent;
import fin.starhud.init.ConfigInit;
import fin.starhud.init.EventInit;
import fin.starhud.init.KeybindInit;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(value = "starhud", dist = Dist.CLIENT)
public class Main {

    public static final Logger LOGGER = LoggerFactory.getLogger("starhud");

    public static Settings settings;

    public static KeyMapping openEditHUDKey;
    public static KeyMapping toggleHUDKey;

    public Main(IEventBus modEventBus) {
        if (FMLEnvironment.dist != Dist.CLIENT) return;

        modEventBus.addListener(this::onRegisterKeyMappings);

        ConfigInit.init();
        EventInit.init();
        HUDComponent.getInstance().init();
    }

    private void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        KeybindInit.init(event);
    }
}
