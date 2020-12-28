package com.willfp.ecoenchants.enchantments.ecoenchants.normal;

import com.willfp.eco.util.integrations.antigrief.AntigriefManager;
import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import com.willfp.ecoenchants.enchantments.meta.EnchantmentType;
import com.willfp.ecoenchants.enchantments.util.EnchantChecks;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
public class Spiked extends EcoEnchant {
    public Spiked() {
        super(
                "spiked", EnchantmentType.NORMAL
        );
    }
    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if(!event.getState().equals(PlayerFishEvent.State.CAUGHT_ENTITY))
            return;

        if(!(event.getCaught() instanceof LivingEntity))
            return;

        Player player = event.getPlayer();

        LivingEntity victim = (LivingEntity) event.getCaught();

        if(victim.hasMetadata("NPC")) return;

        if(!AntigriefManager.canInjure(player, victim)) return;

        if (!EnchantChecks.mainhand(player, this)) return;
        if(this.getDisabledWorlds().contains(player.getWorld())) return;

        int level = EnchantChecks.getMainhandLevel(player, this);

        double damagePerLevel = this.getConfig().getDouble(EcoEnchants.CONFIG_LOCATION + "damage-per-level");
        double damage = damagePerLevel * level;
        victim.damage(damage, player);
    }
}
