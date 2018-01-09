package mod.heimrarnadalr.kagic.util;

import java.util.Random;

import mod.akrivus.kagic.entity.EntityCorruptedGem;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.shardfusion.EntityShardFusion;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
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

	public static boolean dropEntityMainHand(EntityLivingBase entity) {
		if (entity.hasItemInSlot(EntityEquipmentSlot.MAINHAND)) {
			ItemStack weaponStack = entity.getHeldItemMainhand();
			if (weaponStack.isItemStackDamageable()) {
				weaponStack.setItemDamage(weaponStack.getMaxDamage() - entity.getRNG().nextInt(1 + entity.getRNG().nextInt(Math.max(weaponStack.getMaxDamage() - 3, 1))));
			}
			entity.entityDropItem(weaponStack, entity.height / 2);
			entity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
			return true;
		} else {
			return false;
		}
	}
	
}
