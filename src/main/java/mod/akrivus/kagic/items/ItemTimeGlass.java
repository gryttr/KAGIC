package mod.akrivus.kagic.items;

import mod.akrivus.kagic.event.TimeGlassEvent;
import mod.akrivus.kagic.init.ModCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ItemTimeGlass extends Item {
	public ItemTimeGlass() {
		this.setUnlocalizedName("time_glass");
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
		this.setMaxStackSize(1);
	}
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			TimeGlassEvent e1 = new TimeGlassEvent(playerIn, playerIn.getHeldItem(hand));
			if (MinecraftForge.EVENT_BUS.post(e1)) return EnumActionResult.SUCCESS;
			worldIn.setWorldTime(worldIn.getWorldTime() * -1L);
			if (worldIn.rand.nextBoolean()) {
				if (worldIn.isRaining()) {
					worldIn.getWorldInfo().setCleanWeatherTime(worldIn.rand.nextInt(12000) + 12000);
					worldIn.getWorldInfo().setThunderTime(0);
					worldIn.getWorldInfo().setRainTime(0);
					worldIn.getWorldInfo().setRaining(false);
				}
				else {
					worldIn.getWorldInfo().setCleanWeatherTime(0);
					worldIn.getWorldInfo().setThunderTime(worldIn.rand.nextInt(12000) + 12000);
					worldIn.getWorldInfo().setRainTime(worldIn.rand.nextInt(12000) + 12000);
					worldIn.getWorldInfo().setRaining(true);
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
