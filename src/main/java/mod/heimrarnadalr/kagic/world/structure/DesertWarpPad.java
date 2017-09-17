package mod.heimrarnadalr.kagic.world.structure;

import java.util.Random;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DesertWarpPad extends RuinStructure {
	public DesertWarpPad(String type) {
		super(type, 3, Blocks.SANDSTONE.getDefaultState(), true, true);
		this.structures.add("/assets/kagic/structures/DesertWarpPad.schematic");
		
		this.allowedBiomes.add(Biomes.DESERT);
		this.allowedBiomes.add(Biomes.DESERT_HILLS);
		this.allowedBiomes.add(Biomes.MUTATED_DESERT);
		this.allowedBlocks.add(Blocks.SAND.getDefaultState());
		this.allowedBlocks.add(Blocks.SANDSTONE.getDefaultState());
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		if (rand.nextInt(750) != 0) {
			return false;
		}
		//KAGIC.instance.chatInfoMessage("Random passed; checking world conditions");
		return super.generate(world, rand, pos);
	}
}
