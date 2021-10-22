package com.willfp.ecoenchants;

import com.willfp.eco.core.EcoPlugin;
import com.willfp.ecoenchants.config.RarityYml;
import com.willfp.ecoenchants.config.TargetYml;
import com.willfp.ecoenchants.config.VanillaEnchantsYml;

@SuppressWarnings("unused")
public abstract class EcoEnchantsPlugin extends EcoPlugin {
    /**
     * Instance of the plugin.
     */
    private static EcoEnchantsPlugin instance;

    /**
     * Rarity.yml.
     */
    private final RarityYml rarityYml;

    /**
     * Target.yml.
     */
    private final TargetYml targetYml;

    /**
     * VanillaEnchants.yml.
     */
    private final VanillaEnchantsYml vanillaEnchantsYml;

    /**
     * Internal constructor called by bukkit on plugin load.
     */
    protected EcoEnchantsPlugin() {
        super(490, 7666, "com.willfp.ecoenchants.proxy", "&a", true);
        instance = this;

        rarityYml = new RarityYml(this);
        targetYml = new TargetYml(this);
        vanillaEnchantsYml = new VanillaEnchantsYml(this);
    }

    @Override
    public String getMinimumEcoVersion() {
        return "6.10.0";
    }

    /**
     * Get the instance of EcoEnchants.
     * <p>
     * Bad practice to use this.
     *
     * @return The instance.
     */
    public static EcoEnchantsPlugin getInstance() {
        return instance;
    }

    /**
     * Get rarity.yml.
     *
     * @return rarity.yml.
     */
    public RarityYml getRarityYml() {
        return this.rarityYml;
    }

    /**
     * Get target.yml.
     *
     * @return target.yml.
     */
    public TargetYml getTargetYml() {
        return this.targetYml;
    }

    /**
     * Get vanillaenchants.yml.
     *
     * @return vanillaenchants.yml.
     */
    public VanillaEnchantsYml getVanillaEnchantsYml() {
        return this.vanillaEnchantsYml;
    }
}
