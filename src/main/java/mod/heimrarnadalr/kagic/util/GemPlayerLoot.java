package mod.heimrarnadalr.kagic.util;

import mod.akrivus.kagic.entity.EntityCorruptedGem;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.shardfusion.EntityShardFusion;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class GemPlayerLoot {

	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event) {
		Entity trueSource = event.getSource().getTrueSource();
		if (trueSource instanceof EntityGem
				&& !(trueSource instanceof EntityCorruptedGem)
				&& !(trueSource instanceof EntityShardFusion)) {
			try {
				ReflectionHelper.setPrivateValue(EntityLivingBase.class, event.getEntityLiving(), 100, "recentlyHit", "field_70718_bc", "aT");
			} catch (Exception e) {}
		}
	}

}
