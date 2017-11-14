package mod.akrivus.kagic.blocks;

import java.util.Random;

import mod.akrivus.kagic.init.ModCreativeTabs;
import mod.akrivus.kagic.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockGiantStrawberry extends Block {
	//Textures by Peridot#7455
	public BlockGiantStrawberry() {
		super(Material.GOURD, MapColor.RED);
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
		this.setUnlocalizedName("giant_strawberry");
		this.setHardness(1.0F);
		this.setSoundType(SoundType.SLIME);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ModItems.STRAWBERRY_SLICE;
	}
	
	@Override
	public int quantityDropped(Random random) {
		return 3 + random.nextInt(5);
	}
	
	@Override
	public int quantityDroppedWithBonus(int fortune, Random random) {
		return Math.min(9, this.quantityDropped(random) + random.nextInt(1 + fortune));
	}
}
