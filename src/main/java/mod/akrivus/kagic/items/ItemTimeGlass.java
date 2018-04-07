package mod.akrivus.kagic.items;

import mod.akrivus.kagic.entity.humans.EntitySteven;
import mod.akrivus.kagic.init.ModCreativeTabs;
import mod.akrivus.kagic.util.injector.InjectorResult;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemTimeGlass extends Item {
	public ItemTimeGlass() {
		this.setUnlocalizedName("time_glass");
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
		this.setMaxStackSize(1);
		this.setMaxDamage(16);
	}
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			if (worldIn.rand.nextInt(16) == 0) {
				ItemStack stack = playerIn.getHeldItem(hand);
				Entity spawningEntity = new EntitySteven(worldIn);
				spawningEntity.setPosition(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
				worldIn.spawnEntity(spawningEntity);
				if (!playerIn.capabilities.isCreativeMode) {
					stack.damageItem(1, playerIn);
				}
			}
			else {
				if (worldIn.rand.nextInt(3) == 0) {
					worldIn.setWorldTime(worldIn.rand.nextInt(24000) - 12000);
				}
				else if (worldIn.rand.nextBoolean()) {
					if (worldIn.isRaining()) {
						worldIn.getWorldInfo().setCleanWeatherTime(worldIn.rand.nextInt(12000) + 1200);
						worldIn.getWorldInfo().setThunderTime(0);
						worldIn.getWorldInfo().setRainTime(0);
						worldIn.getWorldInfo().setRaining(false);
					}
					else {
						worldIn.getWorldInfo().setCleanWeatherTime(0);
						worldIn.getWorldInfo().setThunderTime(worldIn.rand.nextInt(12000) + 1200);
						worldIn.getWorldInfo().setRainTime(worldIn.rand.nextInt(12000) + 1200);
						worldIn.getWorldInfo().setRaining(true);
					}
				}
				else {
					for (int x = -4; x < 4; ++x) {
						for (int y = -4; y < 4; ++y) {
							for (int z = -4; z < 4; ++z) {
								BlockPos newp = pos.add(x, y, z);
								if (worldIn.rand.nextInt(3) != 0) {
									InjectorResult.drainBlock(worldIn, newp);
								}
							}
						}
					}
				}
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
	public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}
