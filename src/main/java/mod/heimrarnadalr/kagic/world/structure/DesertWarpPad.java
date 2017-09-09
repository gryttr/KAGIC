package mod.heimrarnadalr.kagic.world.structure;

import java.util.Random;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DesertWarpPad extends RuinStructure {
	public DesertWarpPad(String file) {
		super(file, "DesertWarpPad", 15, 17, 3, Blocks.SANDSTONE.getDefaultState(), true, true);
		this.allowedBiomes.add(Biomes.DESERT);
		this.allowedBlocks.add(Blocks.SAND.getDefaultState());
		this.allowedBlocks.add(Blocks.SANDSTONE.getDefaultState());
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		if (rand.nextInt(500) != 0) {
			return false;
		}
		//KAGIC.instance.chatInfoMessage("Random passed; checking world conditions");
		return super.generate(world, rand, pos);
	}
}
