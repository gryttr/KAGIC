package mod.akrivus.kagic.init;

import mod.akrivus.kagic.enchantment.EnchantmentBreakingPoint;
import mod.akrivus.kagic.enchantment.EnchantmentFairFight;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModEnchantments {
	public static final EnchantmentBreakingPoint BREAKING_POINT = new EnchantmentBreakingPoint();
	public static final EnchantmentFairFight FAIR_FIGHT = new EnchantmentFairFight();
	
	public static void register() {
		GameRegistry.register(BREAKING_POINT, new ResourceLocation("kagic:breaking_point"));
		GameRegistry.register(FAIR_FIGHT, new ResourceLocation("kagic:fair_fight"));
	}
}
