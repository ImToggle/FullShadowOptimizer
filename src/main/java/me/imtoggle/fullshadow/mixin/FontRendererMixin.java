package me.imtoggle.fullshadow.mixin;

import me.imtoggle.fullshadow.renderer.BorderedTextRenderer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FontRenderer.class)
public class FontRendererMixin {
    @Shadow
    protected float posX;

    @Shadow
    protected float posY;

    @ModifyConstant(method = "renderDefaultChar", constant = @Constant(floatValue = 128f))
    private float asciiTextureSize(float constant) {
        if (BorderedTextRenderer.INSTANCE.getDrawingFullShadow()) {
            return 160f;
        }
        return constant;
    }

    @Inject(method = "renderDefaultChar", at = @At("HEAD"))
    private void asciiShift(int ch, boolean italic, CallbackInfoReturnable<Float> cir) {
        if (BorderedTextRenderer.INSTANCE.getDrawingFullShadow()) {
            posX -= 1;
            posY -= 1;
        }
    }

    @Inject(method = "renderDefaultChar", at = @At("RETURN"))
    private void asciiUnshift(CallbackInfoReturnable<Float> cir) {
        if (BorderedTextRenderer.INSTANCE.getDrawingFullShadow()) {
            posX += 1;
            posY += 1;
        }
    }

    @ModifyConstant(method = "renderDefaultChar", constant = @Constant(intValue = 8))
    private int asciiGlyphSize(int constant) {
        return BorderedTextRenderer.INSTANCE.getDrawingFullShadow() ? 10 : constant;
    }

    @ModifyConstant(method = "renderDefaultChar", constant = @Constant(floatValue = 0.01f))
    private float asciiGlyphWidth(float constant) {
        return BorderedTextRenderer.INSTANCE.getDrawingFullShadow() ? constant - 4.0f : constant;
    }
    @ModifyConstant(method = "renderDefaultChar", constant = @Constant(floatValue = 7.99f))
    private float asciiPixelSize(float constant) {
        if (BorderedTextRenderer.INSTANCE.getDrawingFullShadow()) {
            return 9.99f;
        }
        return constant;
    }

    @ModifyArg(method = "renderDefaultChar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;bindTexture(Lnet/minecraft/util/ResourceLocation;)V"))
    private ResourceLocation asciiTexture(ResourceLocation location) {
        if (BorderedTextRenderer.INSTANCE.getDrawingFullShadow()) {
            return BorderedTextRenderer.INSTANCE.getAsciiTexture().getBordered().getLocation();
        }
        return location;
    }

    @ModifyConstant(method = "renderUnicodeChar", constant = @Constant(floatValue = 256f))
    private float unicodeTextureSize(float constant) {
        if (BorderedTextRenderer.INSTANCE.getDrawingFullShadow()) {
            return 320f;
        }
        return constant;
    }

    @ModifyConstant(method = "renderUnicodeChar", constant = {@Constant(intValue = 16, ordinal = 1), @Constant(intValue = 16, ordinal = 3)})
    private int unicodeGlyphSize(int constant) {
        if (BorderedTextRenderer.INSTANCE.getDrawingFullShadow()) {
            return 20;
        }
        return constant;
    }

    @ModifyConstant(method = "renderUnicodeChar", constant = @Constant(floatValue = 0.02f))
    private float unicodeGlyphWidth(float constant) {
        if (BorderedTextRenderer.INSTANCE.getDrawingFullShadow()) {
            return constant - 4.0f;
        }
        return constant;
    }

    @ModifyConstant(method = "renderUnicodeChar", constant = @Constant(floatValue = 15.98F))
    private float unicodeGlyphHeight(float constant) {
        if (BorderedTextRenderer.INSTANCE.getDrawingFullShadow()) {
            return 19.98f;
        }
        return constant;
    }

    @ModifyConstant(method = "renderUnicodeChar", constant = @Constant(floatValue = 7.99f))
    private float unicodePixelSize(float constant) {
        if (BorderedTextRenderer.INSTANCE.getDrawingFullShadow()) {
            return 9.99f;
        }
        return constant;
    }

    @Inject(method = "getUnicodePageLocation", at = @At("HEAD"), cancellable = true)
    private void unicodeTexture(int page, CallbackInfoReturnable<ResourceLocation> cir) {
        if (BorderedTextRenderer.INSTANCE.getDrawingFullShadow()) {
            cir.setReturnValue(BorderedTextRenderer.INSTANCE.getUnicodeTexture()[page].getBordered().getLocation());
        }
    }


    @Inject(method = "renderUnicodeChar", at = @At("HEAD"))
    private void unicodeShift(char ch, boolean italic, CallbackInfoReturnable<Float> cir) {
        if (BorderedTextRenderer.INSTANCE.getDrawingFullShadow()) {
            posX -= 1f;
            posY -= 1f;
        }
    }

    @Inject(method = "renderUnicodeChar", at = @At("RETURN"))
    private void unicodeUnshift(CallbackInfoReturnable<Float> cir) {
        if (BorderedTextRenderer.INSTANCE.getDrawingFullShadow()) {
            posX += 1f;
            posY += 1f;
        }
    }
}