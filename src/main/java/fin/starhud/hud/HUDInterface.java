package fin.starhud.hud;

import net.minecraft.client.gui.GuiGraphics;

public interface HUDInterface {

    boolean shouldRender();
    void update();
    boolean render(GuiGraphics context);
    boolean collect();
    String getId();
    String getName();
}
