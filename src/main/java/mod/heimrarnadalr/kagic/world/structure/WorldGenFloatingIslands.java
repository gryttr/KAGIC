package mod.heimrarnadalr.kagic.world.structure;

import java.util.Random;

import net.minecraft.block.BlockVine;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenFloatingIslands extends WorldGenerator {
	private final float minRadius;
	private final float maxRadius;
	private final float minHeight;
	private final float maxHeight;
	private final boolean hasVines;

	public WorldGenFloatingIslands(float minR, float maxR, float minH, float maxH, boolean vines) {
		super();
		this.minRadius = minR;
		this.maxRadius = maxR;
		this.minHeight = minH;
		this.maxHeight = maxH;
		this.hasVines = vines;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		float xMinRadius = this.minRadius + rand.nextInt((int) (this.maxRadius - this.minRadius));
		float xMaxRadius = this.minRadius + rand.nextInt((int) (this.maxRadius - this.minRadius));
		float zMinRadius = this.minRadius + rand.nextInt((int) (this.maxRadius - this.minRadius));
		float zMaxRadius = this.minRadius + rand.nextInt((int) (this.maxRadius - this.minRadius));
		float height = this.minHeight + rand.nextInt((int) (this.maxHeight - this.minHeight));
		
		for (float x = -xMinRadius; x <= xMaxRadius; ++x) {
			for (float z = -zMinRadius; z <= zMaxRadius; ++z) {
				float a = ((x + 0.5F) * (x + 0.5F)) / (xMinRadius * xMaxRadius);
				float c = ((z + 0.5F) * (z + 0.5F)) / (zMinRadius * zMaxRadius);
				
				for (float y = 1; y >= -height; --y) {
					float b = ((y + 0.5F) * (y + 0.5F)) / (height * height);
					
					if (a + b + c <= 1 && !world.isAirBlock(pos.add(x, y, z))) {
						return false;
					}
				}
			}
		}

		for (float x = -xMinRadius; x <= xMaxRadius; ++x) {
			for (float z = -zMinRadius; z <= zMaxRadius; ++z) {
				float a = ((x + 0.5F) * (x + 0.5F)) / (xMinRadius * xMaxRadius);
				float c = ((z + 0.5F) * (z + 0.5F)) / (zMinRadius * zMaxRadius);
				
				for (float y = 0; y >= -height; --y) {
					float b = ((y + 0.5F) * (y + 0.5F)) / (height * height);
					
					if (a + b + c <= 1) {
						world.setBlockState(pos.add(x, y, z), Blocks.STONE.getDefaultState());
						if (this.hasVines && -y > height / 2) {
							float b2 = ((y - 0.5F) * (y - 0.5F)) / (height * height);
							if (a + b2 + c > 1) {
								if (rand.nextInt(3) > 0 && world.isBlockFullCube(pos.add(x + 1, y - 1, z))) {
									this.addVineLine(world, pos.add(x, y - 1, z), rand, BlockVine.EAST);
								}

								if (rand.nextInt(3) > 0 && world.isBlockFullCube(pos.add(x - 1, y - 1, z))) {
									this.addVineLine(world, pos.add(x, y - 1, z), rand, BlockVine.WEST);
								}

								if (rand.nextInt(3) > 0 && world.isBlockFullCube(pos.add(x, y - 1, z + 1))) {
									this.addVineLine(world, pos.add(x, y - 1, z), rand, BlockVine.SOUTH);
								}

								if (rand.nextInt(3) > 0 && world.isBlockFullCube(pos.add(x, y - 1, z - 1))) {
									this.addVineLine(world, pos.add(x, y - 1, z), rand, BlockVine.NORTH);
								}
							}
						}
					}
				}
				
				if (a + c <= 1) {
					world.setBlockState(pos.add(x, 1, z), Blocks.GRASS.getDefaultState());
				}
			}
		}
		return true;
	}
	
	public void addVineLine(World world, BlockPos pos, Random rand, PropertyBool facing) {
		int length = rand.nextInt(4);
		for (BlockPos newPos = pos; pos.getY() - newPos.getY() < length; newPos = newPos.down()) {
			this.setBlockAndNotifyAdequately(world, newPos, Blocks.VINE.getDefaultState().withProperty(facing, Boolean.valueOf(true)));
		}
	}
}
