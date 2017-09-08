package mod.akrivus.kagic.init;

import mod.akrivus.kagic.enchantment.EnchantmentBreakingPoint;
import mod.akrivus.kagic.enchantment.EnchantmentFairFight;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent;

public class ModEnchantments {
	public static final EnchantmentBreakingPoint BREAKING_POINT = new EnchantmentBreakingPoint();
	public static final EnchantmentFairFight FAIR_FIGHT = new EnchantmentFairFight();
	
	public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
		event.getRegistry().register(BREAKING_POINT);//, new ResourceLocation("kagic:breaking_point"));
		event.getRegistry().register(FAIR_FIGHT);//, new ResourceLocation("kagic:fair_fight"));
	}
}
