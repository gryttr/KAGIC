package mod.akrivus.kagic.blocks;


import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockRoseTears extends BlockFluidClassic {

	public BlockRoseTears(Fluid fluid, Material material) {
		super(fluid, material);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		if (entity instanceof EntityLivingBase && !((EntityLivingBase) entity).isPotionActive(MobEffects.REGENERATION)) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 2));
		}
	}
	
	@Override
	protected void flowIntoBlock(World world, BlockPos pos, int meta) {
		if (world.getBlockState(pos).getMaterial() == Material.LAVA) {
			world.setBlockState(pos, ModBlocks.PINK_SANDSTONE.getDefaultState());
			this.lavaExtinguishEffects(world, pos);
		} else if (world.getBlockState(pos.down()).getMaterial() == Material.LAVA) {
			super.flowIntoBlock(world, pos, meta);
			world.setBlockState(pos.down(), ModBlocks.PINK_SANDSTONE.getDefaultState());
			this.lavaExtinguishEffects(world, pos.down());
		} else if (world.getBlockState(pos).getMaterial() == Material.WATER) {
			return;
		} else {
			super.flowIntoBlock(world, pos, meta);
		}
	}

	protected void lavaExtinguishEffects(World worldIn, BlockPos pos) {
		double d0 = (double)pos.getX();
		double d1 = (double)pos.getY();
		double d2 = (double)pos.getZ();
		worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);

		for (int i = 0; i < 8; ++i) {
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0 + Math.random(), d1 + 1.2D, d2 + Math.random(), 0.0D, 0.0D, 0.0D);
		}
	}
}
