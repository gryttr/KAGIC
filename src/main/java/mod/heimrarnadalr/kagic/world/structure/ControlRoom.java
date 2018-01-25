package mod.heimrarnadalr.kagic.world.structure;

import java.util.Random;

import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModBiomes;
import mod.akrivus.kagic.init.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ControlRoom extends BuriedRuinStructure {

	public ControlRoom(String type) {
		super(type, 32, 32, true);
		this.structures.add("/assets/kagic/structures/ControlRoom.schematic");

		this.chestTables.put(new BlockPos(48, 18, 13), LootTables.CONTROL_ROOM);
		this.chestTables.put(new BlockPos(46, 18, 14), LootTables.CONTROL_ROOM);
		this.chestTables.put(new BlockPos(50, 18, 14), LootTables.CONTROL_ROOM);
		this.chestTables.put(new BlockPos(46, 18, 16), LootTables.CONTROL_ROOM);
		this.chestTables.put(new BlockPos(50, 18, 16), LootTables.CONTROL_ROOM);
		this.chestTables.put(new BlockPos(48, 18, 17), LootTables.CONTROL_ROOM);
		this.chestTables.put(new BlockPos(48, 18, 15), LootTables.CONTROL_ROOM_MUSIC);

		this.allowedBlocks.add(ModBlocks.DRAINED_BLOCK.getDefaultState());
		this.allowedBiomes.add(ModBiomes.KINDERGARTEN);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		if (rand.nextInt(10) != 0) {
			return false;
		}
		this.rotation = (byte) rand.nextInt(4);
		BlockPos entrance = world.getTopSolidOrLiquidBlock(pos.add(Schematic.getRotatedPos(new BlockPos(3, 0, 16), 61, 31, this.rotation)));
		if (entrance.getY() > world.getSeaLevel() + 5 || !this.checkBiome(world, entrance)) {
			return false;
		}
		
		KAGIC.instance.chatInfoMessage("Generating at " + entrance);
		BlockPos genPos = new BlockPos(pos.getX(), entrance.getY(), pos.getZ());
		if (super.generate(world, rand, genPos)) {

			BlockPos redstonePos = genPos.add(Schematic.getRotatedPos(new BlockPos(54, 4, 15), this.width, this.length, this.rotation)).down(this.minDepth);
			world.setBlockState(redstonePos, Blocks.REDSTONE_WIRE.getDefaultState());
			KAGIC.instance.chatInfoMessage("Setting redstone wire for " + Schematic.getRotatedPos(new BlockPos(54, 4, 15), this.width, this.length, this.rotation) + " at " + redstonePos);

			redstonePos = genPos.add(Schematic.getRotatedPos(new BlockPos(44, 4, 16), this.width, this.length, this.rotation)).down(this.minDepth);
			world.setBlockState(redstonePos, Blocks.REDSTONE_WIRE.getDefaultState());
			KAGIC.instance.chatInfoMessage("Setting redstone wire for " + Schematic.getRotatedPos(new BlockPos(44, 4, 16), this.width, this.length, this.rotation) + " at " + redstonePos);

			return true;
		} else {
			return false; 
		}
	}
}
