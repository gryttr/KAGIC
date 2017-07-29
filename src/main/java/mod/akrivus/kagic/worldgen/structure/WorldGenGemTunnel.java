package mod.akrivus.kagic.worldgen.structure;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenGemTunnel extends WorldGenerator {
	private double gemHeight = 3;
	private double gemWidth = 3;
	private int length = 7;
	private int north = 0;
	private int east = 0;
	
	public WorldGenGemTunnel(double height, double width, int north, int east, int length) {
		this.gemHeight = height;
		this.gemWidth = width;
		this.length = length;
		this.north = north;
		this.east = east;
	}
	
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		if (north != 0 && east != 0) {
			if (rand.nextBoolean()) {
				north = 0;
			}
			else {
				east = 0;
			}
		}
		if (north > 0) {
			for (float yad = 0; yad < gemHeight; ++yad) {
	    		for (float num = position.getZ(); num >= position.getZ() - length; --num) {
	    			if (gemWidth == 3) {
	    				worldIn.destroyBlock(new BlockPos(position.getX() - 1, yad + position.getY(), num), false);
	    				worldIn.destroyBlock(new BlockPos(position.getX(), yad + position.getY(), num), false);
	    				worldIn.destroyBlock(new BlockPos(position.getX() + 1, yad + position.getY(), num), false);
	    			}
	    			if (gemWidth == 2) {
	    				worldIn.destroyBlock(new BlockPos(position.getX() - 1, yad + position.getY(), num), false);
	    				worldIn.destroyBlock(new BlockPos(position.getX(), yad + position.getY(), num), false);
	    			}
	    			if (gemWidth == 1) {
	    				worldIn.destroyBlock(new BlockPos(position.getX(), yad + position.getY(), num), false);
	    			}
	    		}
			}
		}
		if (north < 0) {
			for (float yad = 0; yad < gemHeight; ++yad) {
	    		for (float num = position.getZ(); num <= position.getZ() + length; ++num) {
	    			if (gemWidth == 3) {
	    				worldIn.destroyBlock(new BlockPos(position.getX() - 1, yad + position.getY(), num), false);
	    				worldIn.destroyBlock(new BlockPos(position.getX(), yad + position.getY(), num), false);
	    				worldIn.destroyBlock(new BlockPos(position.getX() + 1, yad + position.getY(), num), false);
	    			}
	    			if (gemWidth == 2) {
	    				worldIn.destroyBlock(new BlockPos(position.getX() - 1, yad + position.getY(), num), false);
	    				worldIn.destroyBlock(new BlockPos(position.getX(), yad + position.getY(), num), false);
	    			}
	    			if (gemWidth == 1) {
	    				worldIn.destroyBlock(new BlockPos(position.getX(), yad + position.getY(), num), false);
	    			}
	    		}
			}
		}
		if (east < 0) {
			for (float yad = 0; yad < gemHeight; ++yad) {
	    		for (float num = position.getX(); num >= position.getX() - length; --num) {
	    			if (gemWidth == 3) {
	    				worldIn.destroyBlock(new BlockPos(num, yad + position.getY(), position.getZ() - 1), false);
		    			worldIn.destroyBlock(new BlockPos(num, yad + position.getY(), position.getZ()), false);
		    			worldIn.destroyBlock(new BlockPos(num, yad + position.getY(), position.getZ() + 1), false);
	    			}
	    			if (gemWidth == 2) {
	    				worldIn.destroyBlock(new BlockPos(num, yad + position.getY(), position.getZ() - 1), false);
		    			worldIn.destroyBlock(new BlockPos(num, yad + position.getY(), position.getZ()), false);
	    			}
	    			if (gemWidth == 1) {
		    			worldIn.destroyBlock(new BlockPos(num, yad + position.getY(), position.getZ()), false);
	    			}
	    		}
			}
		}
		if (east > 0) {
			for (float yad = 0; yad < gemHeight; ++yad) {
	    		for (float num = position.getX(); num <= position.getX() + length; ++num) {
	    			if (gemWidth == 3) {
	    				worldIn.destroyBlock(new BlockPos(num, yad + position.getY(), position.getZ() - 1), false);
		    			worldIn.destroyBlock(new BlockPos(num, yad + position.getY(), position.getZ()), false);
		    			worldIn.destroyBlock(new BlockPos(num, yad + position.getY(), position.getZ() + 1), false);
	    			}
	    			if (gemWidth == 2) {
	    				worldIn.destroyBlock(new BlockPos(num, yad + position.getY(), position.getZ() - 1), false);
		    			worldIn.destroyBlock(new BlockPos(num, yad + position.getY(), position.getZ()), false);
	    			}
	    			if (gemWidth == 1) {
		    			worldIn.destroyBlock(new BlockPos(num, yad + position.getY(), position.getZ()), false);
	    			}
	    		}
			}
		}
		return true;
	}
}
