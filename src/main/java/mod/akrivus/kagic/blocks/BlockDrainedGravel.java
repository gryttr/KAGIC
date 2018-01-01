package mod.akrivus.kagic.blocks;

import mod.akrivus.kagic.init.ModBlocks;
import mod.akrivus.kagic.init.ModCreativeTabs;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDrainedGravel extends BlockFalling {

	public BlockDrainedGravel(String unlocalizedName) {
		super(ModBlocks.DRAINED);
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
		this.setResistance(1);
		this.setHardness(40);
		this.setHarvestLevel("shovel", 1);
		this.setSoundType(SoundType.GROUND);
	}

    @SideOnly(Side.CLIENT)
    public int getDustColor(IBlockState state)
    {
        return 4797768;
    }
}
