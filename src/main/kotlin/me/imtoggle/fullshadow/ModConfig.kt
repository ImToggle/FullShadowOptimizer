package me.imtoggle.fullshadow

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.Slider
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.data.*

object ModConfig : Config(Mod(FullShadowOptimizer.NAME, ModType.UTIL_QOL), "${FullShadowOptimizer.MODID}.json") {

    @Slider(name = "threshold", min = 0f, max = 255f)
    var threshold = 33

    @Switch(
        name = "Always Full Shadow"
    )
    var alwaysShadow = false

    init {
        initialize()
    }
}