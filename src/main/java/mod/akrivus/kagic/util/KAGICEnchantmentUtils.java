package mod.akrivus.kagic.util;

import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;

public class KAGICEnchantmentUtils {
	public static Enchantment getFirstNonconflicting(Map<Enchantment, Integer> potentialEnchantments, Map<Enchantment, Integer> existingEnchantments) {
		if (existingEnchantments.isEmpty()) {
			return (Enchantment) potentialEnchantments.keySet().toArray()[0];
		}
		
		for (Enchantment potential : potentialEnchantments.keySet()) {
			for (Enchantment existing : existingEnchantments.keySet()) {
				if (potential.isCompatibleWith(existing) || potential == existing) {
					return potential;
				}
			}
		}
		
		return null;
	}
}
