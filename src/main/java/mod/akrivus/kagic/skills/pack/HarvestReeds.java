package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class HarvestReeds extends HarvestStalks {
	public HarvestReeds() {
		super();
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] {
			"reed",
			"reeds",
			"cane",
			"canes",
			"sugarcane",
			"sugarcanes"
		}));
	}
	@Override
	public boolean isCorrectFarmBlock(IBlockState state) {
		return state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.SAND;
	}
	@Override
	public boolean isCorrectPlant(IBlockState state) {
		return state.getBlock() == Blocks.REEDS;
	}
	@Override
	public Block getPlant() {
		return Blocks.REEDS;
	}
	@Override
	public Item getSeed() {
		return Items.REEDS;
	}
}
