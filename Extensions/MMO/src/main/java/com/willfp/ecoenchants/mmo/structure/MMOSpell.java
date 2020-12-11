package com.willfp.ecoenchants.mmo.structure
        ;

import com.willfp.ecoenchants.enchantments.itemtypes.Spell;
import com.willfp.ecoenchants.mmo.MMOMain;
import com.willfp.ecoenchants.mmo.MMOPrerequisites;
import com.willfp.ecoenchants.util.optional.Prerequisite;

public abstract class MMOSpell extends Spell implements MMOEnchant {
    protected MMOSpell(String key, Prerequisite... prerequisites) {
        super(key, MMOMain.class, MMOPrerequisites.append(prerequisites, MMOPrerequisites.HAS_MMOCORE));

        MMOEnchant.REGISTRY.add(this);
    }
}