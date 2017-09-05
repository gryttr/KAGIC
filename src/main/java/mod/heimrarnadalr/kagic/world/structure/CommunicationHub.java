package mod.heimrarnadalr.kagic.world.structure;

import java.util.Random;

import mod.akrivus.kagic.init.KAGIC;
import mod.heimrarnadalr.kagic.worlddata.ChunkLocation;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommunicationHub extends RuinStructure {

	public CommunicationHub(String file) {
		super(file, "CommHub", 40, 40, 10, Blocks.SANDSTONE.getDefaultState(), true);
		this.allowedBiomes.add(Biomes.DESERT);
		this.allowedBlocks.add(Blocks.SAND.getDefaultState());
		this.allowedBlocks.add(Blocks.SANDSTONE.getDefaultState());
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		if (rand.nextInt(1000) != 0) {
			return false;
		}
		KAGIC.instance.chatInfoMessage("Random passed; checking world conditions");
		return super.generate(world, rand, pos);
	}
}
