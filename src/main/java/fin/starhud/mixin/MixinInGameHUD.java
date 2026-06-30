package fin.starhud.mixin;

import fin.starhud.condition.HeldItemTooltip;
import fin.starhud.condition.ScoreboardHUD;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = Gui.class, priority = 500)
public class MixinInGameHUD {

    @Redirect(
            method = "displayScoreboardSidebar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V",
                    ordinal = 1
            ),
            require = 0
    )
    private void captureScoreboardFill(GuiGraphics instance, int x1, int y1, int x2, int y2, int color) {
        ScoreboardHUD.captureBoundingBox(x1, y1 - 9, x2, y2);
        instance.fill(x1, y1, x2 ,y2 , color);
    }

    @Redirect(
            method = "renderSelectedItemName",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V",
                    ordinal = 0
            ),
            require = 0
    )
    private void captureTooltipBox(GuiGraphics instance, int x1, int y1, int x2, int y2, int color) {
        HeldItemTooltip.setBoundingBox(x1, y1, x2 - x1, y2 - y1);
        instance.fill(x1, y1, x2, y2, color);
    }
}
