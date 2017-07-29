package mod.akrivus.kagic.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class LaserDamage extends DamageSource {
	public LaserDamage() {
		super("laser");
	}
	public ITextComponent getDeathMessage(EntityLivingBase victim) {
		EntityLivingBase attacker = victim.getAttackingEntity();
		if (attacker != null) {
			return new TextComponentTranslation("death.attack.laser.player", new Object[] { victim.getDisplayName(), attacker.getDisplayName() });
		}
		return new TextComponentTranslation("death.attack.laser", new Object[] { victim.getDisplayName() });
	}
}
