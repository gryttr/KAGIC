package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class HarvestPumpkins extends HarvestPepos {
	public HarvestPumpkins() {
		super();
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] {
			"pumpkin",
			"pumpkins"
		}));
	}
	@Override
	public boolean isCorrectPlant(IBlockState state) {
		return state.getBlock() == Blocks.PUMPKIN;
	}
}
