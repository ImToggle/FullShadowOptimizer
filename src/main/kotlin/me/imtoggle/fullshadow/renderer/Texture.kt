package me.imtoggle.fullshadow.renderer

import cc.polyfrost.oneconfig.utils.dsl.mc
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.client.renderer.texture.TextureUtil
import net.minecraft.client.resources.IResourceManager
import net.minecraft.util.ResourceLocation

class Texture(path: String) : AbstractTexture() {
    val location = ResourceLocation(path)

    init {
        mc.textureManager.loadTexture(location, this)
    }

    fun load(data: IntArray, width: Int, height: Int) {
        val id = getGlTextureId()
        TextureUtil.allocateTexture(id, width, height)
        TextureUtil.uploadTexture(id, data, width, height)
    }

    override fun loadTexture(resourceManager: IResourceManager) {}
}