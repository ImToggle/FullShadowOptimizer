package me.imtoggle.fullshadow.mixin;

import cc.polyfrost.oneconfig.renderer.TextRenderer;
import me.imtoggle.fullshadow.renderer.BorderedTextRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextRenderer.class)
public class TextRendererMixin {

    @Inject(method = "drawBorderedText", at = @At("HEAD"), cancellable = true, remap = false)
    private static void optimize(String text, float x, float y, int color, int opacity, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue((int) BorderedTextRenderer.INSTANCE.draw(text, x, y, color));
    }

}
