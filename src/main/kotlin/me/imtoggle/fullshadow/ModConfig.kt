package me.imtoggle.fullshadow

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.data.*

object ModConfig : Config(Mod(FullShadowOptimizer.NAME, ModType.UTIL_QOL), "${FullShadowOptimizer.MODID}.json") {

    init {
        initialize()
    }
}