package mod.akrivus.kagic.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class SlagDamage extends DamageSource {
	public SlagDamage() {
		super("slag");
	}
	public ITextComponent getDeathMessage(EntityLivingBase victim) {
		return new TextComponentTranslation("death.attack.slag", new Object[] { victim.getDisplayName() });
	}
}
