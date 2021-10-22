package com.willfp.ecoenchants.enchantments.ecoenchants.normal;

import com.willfp.ecoenchants.enchantments.meta.EnchantmentType;

public class Reaper extends EcoEnchant {
    public Reaper() {
        super(
                "reaper", EnchantmentType.NORMAL
        );
    }

    @Override
    public String getPlaceholder(final int level) {
        return EnchantmentUtils.chancePlaceholder(this, level);
    }

    // Actual code is in soulbound.
}
