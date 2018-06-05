package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class HarvestCacti extends HarvestStalks {
	public HarvestCacti() {
		super();
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] {
			"cactus",
			"cactuses",
			"cacti"
		}));
	}
	@Override
	public boolean isCorrectFarmBlock(IBlockState state) {
		return state.getBlock() == Blocks.SAND;
	}
	@Override
	public boolean isCorrectPlant(IBlockState state) {
		return state.getBlock() == Blocks.CACTUS;
	}
	@Override
	public Block getPlant() {
		return Blocks.CACTUS;
	}
	@Override
	public Item getSeed() {
		return Item.getItemFromBlock(Blocks.CACTUS);
	}
}
