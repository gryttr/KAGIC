package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class HarvestNetherWart extends Harvest {
	public HarvestNetherWart() {
		super();
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] {
			"wart"
		}));
	}
	@Override
	public boolean isCorrectFarmBlock(IBlockState state) {
		return state.getBlock() == Blocks.SOUL_SAND;
	}
	@Override
	public boolean isCorrectPlant(IBlockState state) {
		return state.getBlock() == Blocks.NETHER_WART && state.getValue(BlockNetherWart.AGE) >= 3;
	}
}
