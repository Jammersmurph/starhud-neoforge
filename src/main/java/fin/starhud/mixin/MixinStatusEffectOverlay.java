package fin.starhud.mixin;

import fin.starhud.Main;
import fin.starhud.hud.HUDComponent;
import fin.starhud.hud.HUDId;
import fin.starhud.hud.implementation.statuseffect.NegativeEffectHUD;
import fin.starhud.hud.implementation.statuseffect.PositiveEffectHUD;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinStatusEffectOverlay {

    @Inject(at = @At("HEAD"), method = "renderEffects", cancellable = true)
    private void renderMobEffectOverlay(GuiGraphics context, DeltaTracker deltaTracker, CallbackInfo ci) {
        PositiveEffectHUD positiveHUD = (PositiveEffectHUD) HUDComponent.getInstance().getHUD(HUDId.POSITIVE_EFFECT);
        NegativeEffectHUD negativeHUD = (NegativeEffectHUD) HUDComponent.getInstance().getHUD(HUDId.NEGATIVE_EFFECT);
        if (positiveHUD != null && negativeHUD != null
                && !Main.settings.generalSettings.inGameSettings.disableHUDRendering
                && (positiveHUD.shouldRender() || negativeHUD.shouldRender())) {
            ci.cancel();
        }
    }
}
