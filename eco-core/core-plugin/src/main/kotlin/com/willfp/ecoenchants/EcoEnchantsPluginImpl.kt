package com.willfp.ecoenchants

import com.willfp.eco.core.command.impl.PluginCommand
import com.willfp.eco.core.display.DisplayModule
import com.willfp.eco.core.fast.FastItemStack
import com.willfp.eco.core.integrations.IntegrationLoader
import com.willfp.eco.util.TelekinesisUtils
import com.willfp.ecoenchants.command.CommandEcoEnchants
import com.willfp.ecoenchants.command.CommandEnchantinfo
import com.willfp.ecoenchants.data.SaveHandler
import com.willfp.ecoenchants.data.storage.DataHandler
import com.willfp.ecoenchants.data.storage.MySQLDataHandler
import com.willfp.ecoenchants.data.storage.YamlDataHandler
import com.willfp.ecoenchants.display.EnchantDisplay
import com.willfp.ecoenchants.enchantments.EcoEnchants
import com.willfp.ecoenchants.enchantments.support.merging.anvil.AnvilListeners
import com.willfp.ecoenchants.enchantments.support.merging.grindstone.GrindstoneListeners
import com.willfp.ecoenchants.enchantments.support.obtaining.EnchantingListeners
import com.willfp.ecoenchants.enchantments.support.obtaining.LootPopulator
import com.willfp.ecoenchants.enchantments.support.obtaining.VillagerListeners
import com.willfp.ecoenchants.enchantments.util.ItemConversions
import com.willfp.ecoenchants.enchantments.util.TimedRunnable
import com.willfp.ecoenchants.enchantments.util.WatcherTriggers
import com.willfp.ecoenchants.integrations.registration.RegistrationManager
import com.willfp.ecoenchants.integrations.registration.plugins.IntegrationEssentials
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

class EcoEnchantsPluginImpl : EcoEnchantsPlugin() {
    val dataHandler: DataHandler = if (configYml.getBool("mysql.enabled"))
        MySQLDataHandler(this) else YamlDataHandler(this)

    override fun handleEnable() {
        logger.info(EcoEnchants.values().size.toString() + " Enchantments Loaded")
        TelekinesisUtils.registerTest { player ->
            FastItemStack.wrap(player.inventory.itemInMainHand).getLevelOnItem(EcoEnchants.TELEKINESIS, false) > 0
        }
    }

    override fun handleDisable() {
        SaveHandler.save(this)
        for (world in Bukkit.getServer().worlds) {
            world.populators.removeIf { it is LootPopulator }
        }
    }

    override fun handleReload() {
        displayModule.update()
        for (enchant in EcoEnchants.values()) {
            HandlerList.unregisterAll(enchant)
            scheduler.run {
                if (enchant.isEnabled) {
                    eventManager.registerListener(enchant)
                    if (enchant is TimedRunnable) {
                        scheduler.syncRepeating(enchant, 5, enchant.time)
                    }
                }
            }
        }

        scheduler.runTimer({
            for (enchant in EcoEnchants.values()) {
                enchant.clearCachedRequirements()
            }
        }, 300, 300)

        SaveHandler.save(this)

        scheduler.runTimer(SaveHandler.Runnable(this), 20000, 20000)
    }

    override fun handleAfterLoad() {
        if (configYml.getBool("loot.enabled")) {
            for (world in Bukkit.getServer().worlds) {
                world.populators.removeIf { it is LootPopulator }
                world.populators.add(LootPopulator(this))
            }
        }

        RegistrationManager.registerEnchantments()
    }

    override fun loadIntegrationLoaders(): List<IntegrationLoader> {
        return listOf(
            IntegrationLoader("Essentials") { RegistrationManager.register(IntegrationEssentials()) }
        )
    }

    override fun loadPluginCommands(): List<PluginCommand> {
        return listOf(
            CommandEnchantinfo(this),
            CommandEcoEnchants(this)
        )
    }

    override fun loadListeners(): List<Listener> {
        return listOf(
            EnchantingListeners(this),
            GrindstoneListeners(this),
            AnvilListeners(this),
            WatcherTriggers(this),
            VillagerListeners(this),
            ItemConversions(this)
        )
    }

    override fun createDisplayModule(): DisplayModule? {
        return EnchantDisplay(this)
    }

    override fun getDisplayModule(): EnchantDisplay {
        return super.getDisplayModule() as EnchantDisplay
    }
}