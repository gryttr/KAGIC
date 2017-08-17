package mod.akrivus.kagic.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;

public class EnchantmentFairFight extends Enchantment {
	public EnchantmentFairFight() {
		super(Rarity.UNCOMMON, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET});
		this.setRegistryName(new ResourceLocation("kagic:fair_fight"));
		this.setName("fair_fight.name");
	}
	public boolean isAllowedOnBooks() {
		return true;
	}
	public boolean isTreasureEnchantment() {
		return true;
	}
	public int getMinEnchantability(int enchantmentLevel) {
        return 1 + (enchantmentLevel - 1) * 10;
    }
    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + 15;
    }
	public int getMaxLevel() {
        return 1;
    }
}
