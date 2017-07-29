package mod.akrivus.kagic.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class ShatterDamage extends DamageSource {
	public ShatterDamage() {
		super("shatter");
	}
	public ITextComponent getDeathMessage(EntityLivingBase victim) {
		EntityLivingBase attacker = victim.getAttackingEntity();
		if (attacker != null) {
			ItemStack stack = attacker.getHeldItemMainhand();
			if (stack != null && stack.hasDisplayName()) {
				return new TextComponentTranslation("death.attack.shatter.item", new Object[] { victim.getDisplayName(), attacker.getDisplayName(), stack.getDisplayName() });
			}
			return new TextComponentTranslation("death.attack.shatter.player", new Object[] { victim.getDisplayName(), attacker.getDisplayName() });
		}
		return new TextComponentTranslation("death.attack.shatter", new Object[] { victim.getDisplayName() });
	}
}
