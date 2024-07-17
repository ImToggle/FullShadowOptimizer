package me.imtoggle.fullshadow

import me.imtoggle.fullshadow.renderer.BorderedTextRenderer
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent

@Mod(modid = FullShadowOptimizer.MODID, name = FullShadowOptimizer.NAME, version = FullShadowOptimizer.VERSION, modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter")
object FullShadowOptimizer {
    const val MODID = "@ID@"
    const val NAME = "@NAME@"
    const val VERSION = "@VER@"

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
//        ModConfig
        BorderedTextRenderer.initialize()
    }
}