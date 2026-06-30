package fin.starhud.init;

import com.mojang.blaze3d.platform.InputConstants;
import fin.starhud.Main;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

public class KeybindInit {

    public static void init(RegisterKeyMappingsEvent event) {
        Main.openEditHUDKey = new KeyMapping(
                "key.starhud.open_edithud",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "key.categories.starhud"
        );

        Main.toggleHUDKey = new KeyMapping(
                "key.starhud.toggle_hud",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                "key.categories.starhud"
        );

        event.register(Main.openEditHUDKey);
        event.register(Main.toggleHUDKey);
    }
}
