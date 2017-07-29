package mod.akrivus.kagic.items;

import mod.akrivus.kagic.entity.vehicles.EntityRoamingEye;
import mod.akrivus.kagic.init.ModCreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemVehicle extends Item {
	private String vehicle;
	public ItemVehicle(String vehicle) {
		this.setUnlocalizedName(vehicle);
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
		this.vehicle = vehicle;
	}
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			ItemStack stack = playerIn.getHeldItem(hand);
			Entity spawningEntity = this.getEntity(worldIn);
			spawningEntity.setPosition(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
			worldIn.spawnEntity(spawningEntity);
			if (!playerIn.capabilities.isCreativeMode) {
				stack.shrink(1);
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
	public Entity getEntity(World worldIn) {
		if (vehicle.equals("roaming_eye")) {
			return new EntityRoamingEye(worldIn);
		}
		return null;
	}
}
