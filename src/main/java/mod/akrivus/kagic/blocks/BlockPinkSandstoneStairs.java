package mod.akrivus.kagic.blocks;

import mod.akrivus.kagic.init.ModBlocks;
import mod.akrivus.kagic.init.ModCreativeTabs;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;

public class BlockPinkSandstoneStairs extends BlockStairs {

	public BlockPinkSandstoneStairs(String name) {
		super(ModBlocks.PINK_SANDSTONE.getDefaultState());
		this.setSoundType(SoundType.STONE);
		this.setHardness(0.8F);
		this.setResistance(4F);
		this.setUnlocalizedName(name);
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
		this.useNeighborBrightness = true;
	}

}
