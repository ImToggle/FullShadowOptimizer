package me.imtoggle.fullshadow.renderer

import cc.polyfrost.oneconfig.utils.dsl.mc
import net.minecraft.client.resources.IReloadableResourceManager
import net.minecraft.client.resources.IResourceManager
import net.minecraft.client.resources.IResourceManagerReloadListener

object BorderedTextRenderer : IResourceManagerReloadListener {
    val asciiTexture = CachedTexture()
    val unicodeTexture = Array(256) { page -> CachedTexture(page) }
    var drawingFullShadow = false

    fun initialize() {
        (mc.resourceManager as IReloadableResourceManager).registerReloadListener(this)
    }

    override fun onResourceManagerReload(resourceManager: IResourceManager) {
        asciiTexture.load()
        for (texture in unicodeTexture) {
            texture.load()
        }
    }

    fun draw(text: String, x: Float, y: Float, color: Int): Float {
        drawingFullShadow = true
        val i = mc.fontRendererObj.drawString(text, x, y, color, false)
        drawingFullShadow = false
        return i.toFloat() + 1
    }
}