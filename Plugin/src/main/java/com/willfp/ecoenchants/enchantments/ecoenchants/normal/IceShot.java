package com.willfp.ecoenchants.enchantments.ecoenchants.normal;

import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.EcoEnchantBuilder;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import com.willfp.ecoenchants.integrations.antigrief.AntigriefManager;
import com.willfp.ecoenchants.nms.Target;
import com.willfp.ecoenchants.util.HasEnchant;
import com.willfp.ecoenchants.util.Rand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
public class IceShot extends EcoEnchant {
    public IceShot() {
        super(
                new EcoEnchantBuilder("ice_shot", EnchantmentType.NORMAL, new Target.Applicable[]{Target.Applicable.CROSSBOW, Target.Applicable.BOW}, 4.0)
        );
    }

    // START OF LISTENERS

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Arrow))
            return;
        if (!(event.getEntity() instanceof LivingEntity))
            return;
        if(!(((Arrow) event.getDamager()).getShooter() instanceof Player))
            return;

        Player player = (Player) ((Arrow) event.getDamager()).getShooter();

        LivingEntity victim = (LivingEntity) event.getEntity();

        if(!AntigriefManager.canInjure(player, (Player) event.getEntity())) return;

        if(event.isCancelled()) return;

        if (!HasEnchant.playerHeld(player, this)) return;

        int level = HasEnchant.getPlayerLevel(player, this);

        if (Rand.randFloat(0, 1) > level * 0.01 * this.getConfig().getDouble(EcoEnchants.CONFIG_LOCATION + "chance-per-level"))
            return;

        victim.setVelocity(new Vector(0, 0, 0));
        victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, level));
    }
}
