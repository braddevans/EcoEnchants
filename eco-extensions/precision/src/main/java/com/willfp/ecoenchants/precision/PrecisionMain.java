package com.willfp.ecoenchants.precision;

import com.willfp.eco.core.EcoPlugin;
import com.willfp.eco.core.extensions.Extension;
import org.jetbrains.annotations.NotNull;

public class PrecisionMain extends Extension {
    public static final EcoEnchant PRECISION = new Precision();

    public PrecisionMain(@NotNull final EcoPlugin plugin) {
        super(plugin);
    }

    @Override
    public void onEnable() {
        // Handled by super
    }

    @Override
    public void onDisable() {
        // Handled by super
    }
}
