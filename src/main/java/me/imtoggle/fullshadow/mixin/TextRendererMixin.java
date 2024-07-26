package me.imtoggle.fullshadow.mixin;

import cc.polyfrost.oneconfig.renderer.TextRenderer;
import me.imtoggle.fullshadow.ModConfig;
import me.imtoggle.fullshadow.renderer.CachedTextRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextRenderer.class)
public class TextRendererMixin {

    @Inject(method = "drawBorderedText", at = @At("HEAD"), cancellable = true, remap = false)
    private static void optimize(String text, float x, float y, int color, int opacity, CallbackInfoReturnable<Integer> cir) {
        if (ModConfig.INSTANCE.enabled) cir.setReturnValue(CachedTextRenderer.INSTANCE.drawString(text, x, y, color, false));
    }

}
