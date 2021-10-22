package com.willfp.ecoenchants.data

import com.willfp.ecoenchants.EcoEnchantsPluginImpl
import com.willfp.ecoenchants.data.storage.PlayerProfile
import org.bukkit.Bukkit

class SaveHandler {
    companion object {
        fun save(plugin: EcoEnchantsPluginImpl) {
            if (Bukkit.getOnlinePlayers().isEmpty()) {
                return
            }
            if (plugin.configYml.getBool("autosave.log")) {
                plugin.logger.info("Auto-Saving player data!")
            }
            PlayerProfile.saveAll(plugin.configYml.getBool("autosave.async"))
            if (plugin.configYml.getBool("autosave.log")) {
                plugin.logger.info("Saved data!")
            }
        }
    }

    class Runnable(
        private val plugin: EcoEnchantsPluginImpl
    ) : java.lang.Runnable {
        override fun run() {
            save(plugin)
        }
    }
}