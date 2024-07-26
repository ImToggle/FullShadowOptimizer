package me.imtoggle.fullshadow.mixin;

import me.imtoggle.fullshadow.ModConfig;
import me.imtoggle.fullshadow.renderer.CachedTextRenderer;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FontRenderer.class)
public abstract class FontRendererMixin {

    @Shadow protected abstract float renderChar(char ch, boolean italic);

    @Inject(method = "drawString(Ljava/lang/String;FFIZ)I", at = @At("HEAD"), cancellable = true)
    private void overrideShadow(String text, float x, float y, int color, boolean dropShadow, CallbackInfoReturnable<Integer> cir) {
        if ((Object) this instanceof CachedTextRenderer) return;
        if (ModConfig.INSTANCE.enabled && ModConfig.INSTANCE.getAlwaysShadow()) {
            cir.setReturnValue(CachedTextRenderer.INSTANCE.drawString(text, x, y, color, false) + (dropShadow ? 1 : 0));
        }
    }

    @Redirect(method = "renderStringAtPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderChar(CZ)F", ordinal = 1))
    private float overrideShadow(FontRenderer instance, char c, boolean ch) {
        if (ModConfig.INSTANCE.enabled && ((Object) this instanceof CachedTextRenderer)) return 0f;
        return renderChar(c, ch);
    }

}