package me.imtoggle.fullshadow.renderer

import cc.polyfrost.oneconfig.utils.dsl.mc
import net.minecraft.client.renderer.texture.TextureUtil
import net.minecraft.util.ResourceLocation

class CachedTexture(path: String, private val isUnicode: Boolean) {
    constructor() : this("textures/font/ascii.png", false)
    constructor(page: Int) : this("textures/font/unicode_page_%02x.png".format(page), true)

    private val original = ResourceLocation(path)
    val bordered = Texture("bordered:$path")

    fun load() {
        val texture = runCatching {
            TextureUtil.readBufferedImage(mc.resourceManager.getResource(original).inputStream)
        }.getOrNull() ?: return
        if (texture.width != texture.height) return
        var imageWidth = texture.width
        val glyphs = texture.getRGB(0, 0, imageWidth, imageWidth, null, 0, imageWidth)
        val scale = imageWidth < 256
        if (scale) {
            imageWidth *= 2
        }

        val glyphSize = imageWidth / 16
        val pixelSize = glyphSize / 16
        val borderedSize = pixelSize * 20
        val borderedMapSize = borderedSize * 16

        val borderedMap = IntArray(borderedMapSize * borderedMapSize)

        for (row in 0..15) {
            val glyphY = row * glyphSize
            val outlineY = row * borderedSize
            for (column in 0..15) {
                val glyphX = column * glyphSize
                val outlineX = column * borderedSize
                for (glyphYOffset in 0..<glyphSize) {
                    for (glyphXOffset in 0..<glyphSize) {
                        val glyphIndex = if (scale) {
                            ((glyphY + glyphYOffset) / 2) * (imageWidth / 2) + (glyphX + glyphXOffset) / 2
                        } else {
                            (glyphY + glyphYOffset) * imageWidth + glyphX + glyphXOffset
                        }
                        val color = glyphs[glyphIndex]
                        val alpha = color and 0xFF000000.toInt()
                        if (alpha == 0) continue

                        val shadowColor = color and 0xFCFCFC ushr 2 or alpha

                        for (yOffset in 0..4) {
                            for (xOffset in 0..4) {
                                if (((xOffset == 0 || xOffset == 4) && (yOffset == 0 || yOffset == 4)) || (xOffset == 2 && yOffset == 2)) continue
                                val outlineIndex = (outlineY + glyphYOffset + yOffset * pixelSize) * borderedMapSize + outlineX + glyphXOffset + xOffset * pixelSize
                                borderedMap[outlineIndex] = borderedMap[outlineIndex] or shadowColor
                            }
                        }
                        val outlineIndex = (outlineY + glyphYOffset + 2 * pixelSize) * borderedMapSize + outlineX + glyphXOffset + 2 * pixelSize
                        borderedMap[outlineIndex] = color
                    }
                }
            }
        }

        bordered.load(borderedMap, borderedMapSize, borderedMapSize)
    }
}