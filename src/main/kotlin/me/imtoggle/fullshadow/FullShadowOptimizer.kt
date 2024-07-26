package me.imtoggle.fullshadow

import cc.polyfrost.oneconfig.utils.dsl.mc
import me.imtoggle.fullshadow.renderer.CachedTextRenderer
import net.minecraft.client.resources.IReloadableResourceManager
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent

@Mod(modid = FullShadowOptimizer.MODID, name = FullShadowOptimizer.NAME, version = FullShadowOptimizer.VERSION, modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter")
object FullShadowOptimizer {
    const val MODID = "@ID@"
    const val NAME = "@NAME@"
    const val VERSION = "@VER@"

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        ModConfig
        (mc.resourceManager as IReloadableResourceManager).registerReloadListener(CachedTextRenderer)
    }
}