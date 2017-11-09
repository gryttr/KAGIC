package mod.akrivus.kagic.util.injector;

import java.util.HashMap;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.EntitySlag;
import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModBlocks;
import mod.akrivus.kagic.init.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class InjectorResult {
	private EntityGem gem;
	private final BlockPos position;
	private final double defectiveRate;
	private final boolean createSlags;
	private final boolean isPrimary;
	private final ExitHole exitHole;
	
	private static final float drainedPercentage = 0.75F;
	
	public InjectorResult(EntityGem gem, BlockPos pos, double defectiveRate, boolean createSlags, boolean isPrimary, ExitHole exitHole) {
		this.gem = gem;
		this.position = pos;
		this.defectiveRate = defectiveRate;
		this.createSlags = createSlags;
		this.isPrimary = isPrimary;
		this.exitHole = exitHole;
	}
	public EntityGem getGem() {
		return this.gem;
	}
	public String getName() {
		return this.gem.getName();
	}
	public BlockPos getPosition() {
		return this.position;
	}
	public double getDefectivity() {
		return this.defectiveRate;
	}
	public boolean isPrimary() {
		return this.isPrimary;
	}
	public boolean canCreateSlags() {
		return this.createSlags;
	}
	public ExitHole getExitHole() {
		return this.exitHole;
	}
	public void generate(World world) {
		if (this.canCreateSlags()) {
			int totalSlags = world.rand.nextInt(9) + 1;
			for (int i = 0; i <= totalSlags; ++i) {
				boolean failed = true;
				int attempts = 0;
				while (failed && attempts < 10) {
					BlockPos spawn = this.getPosition().add(world.rand.nextInt(9), 0, world.rand.nextInt(9));
					ChunkPos c = world.getChunkFromBlockCoords(spawn).getPos();
					failed = !world.isAirBlock(spawn);
					if (!failed) {
						EntitySlag slag = new EntitySlag(world);
						slag.setPosition(spawn.getX(), spawn.getY(), spawn.getZ());
						slag.setVariant(Math.abs((c.x + c.z) % ModEntities.MINERALS.size()));
						world.spawnEntity(slag);
						slag.onInitialSpawn(world.getDifficultyForLocation(spawn), null);
					}
					else {
						++attempts;
					}
				}
			}
			world.destroyBlock(this.position, false);
		}
		else {
			if (this.exitHole != null) {
				this.exitHole.emerge(world);
			}
			if (this.gem.world.rand.nextFloat() < this.defectiveRate) {
				this.gem.setDefective(true);
			}
			else if (this.exitHole.createRockMelt()) {
				this.gem.setPrimary(true);
			}
			world.spawnEntity(this.gem);
			this.gem.onInitialSpawn(world.getDifficultyForLocation(this.getPosition()), null);
		}
	}
	@SuppressWarnings("unchecked")
	public static InjectorResult create(World world, BlockPos pos, boolean drain) {
		HashMap<Class<EntityGem>, Double> resultTable = new HashMap<Class<EntityGem>, Double>();
		HashMap<Class<EntityGem>, Double> defectivity = new HashMap<Class<EntityGem>, Double>();
		HashMap<Class<EntityGem>, Double> friction = new HashMap<Class<EntityGem>, Double>();
		float drainedCount = 0F;
		boolean drainedChecked = false;
		for (String gem : ModEntities.GEMS.keySet()) {
			try {
				Class<EntityGem> gemClass = (Class<EntityGem>) ModEntities.GEMS.get(gem);
				HashMap<IBlockState, Double> yield = (HashMap<IBlockState, Double>) gemClass.getField((gem + "_yields").toUpperCase()).get(null);
				double defectivityRate = 1.0;
				double frictionFactor = 0.0;
				for (int x = -4; x <= 4; ++x) {
					for (int y = -4; y <= 4; ++y) {
						for (int z = -4; z <= 4; ++z) {
							BlockPos ore = pos.add(x, y, z);
							IBlockState state = world.getBlockState(ore);
							if (!resultTable.containsKey(gemClass)) {
								resultTable.put(gemClass, 0.0);
							}
							if (yield.containsKey(state)) {
								resultTable.put(gemClass, resultTable.get(gemClass) + yield.get(state));
								frictionFactor += 0.0036;
								defectivityRate -= 0.2;
								
							}
							if (!drainedChecked) {
								Block block = state.getBlock();
								if (block == ModBlocks.DRAINED_BLOCK || block == Blocks.GRAVEL || block == Blocks.AIR) {
									drainedCount += 1;
								}
							}
							if (state.getMaterial() == Material.GRASS) {
								defectivityRate -= 0.4;
							}
						}
					}
				}
				drainedChecked = true;
				defectivity.put(gemClass, Math.max(0.0, Math.min(1.0, defectivityRate)));
				friction.put(gemClass, frictionFactor);
			}
			catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error creating gem: " + e.getMessage());
			}
		}
		boolean canSpawnGem = false;
		Class<? extends EntityGem> mostLikelyGem = null;
		double highestYield = 0.0;
		for (Class<EntityGem> gemClass : resultTable.keySet()) {
			if (!gemClass.getName().contains("Diamond")) {
				boolean forget = world.rand.nextBoolean();
				if (resultTable.get(gemClass) > 0.1 && world.rand.nextInt((int)(resultTable.get(gemClass) * 10) + 1) == 0) {
					highestYield = resultTable.get(gemClass);
					mostLikelyGem = gemClass;
					canSpawnGem = true;
				}
				else {
					double result = resultTable.get(gemClass);
					if (result == highestYield && result > 0) {
						highestYield = forget ? resultTable.get(gemClass) : highestYield;
						mostLikelyGem = forget ? gemClass : mostLikelyGem;
						canSpawnGem = true;
					}
					else if (result > highestYield && result > 0) {
						highestYield = resultTable.get(gemClass);
						mostLikelyGem = gemClass;
						canSpawnGem = true;
					}
				}
			}
		}

		if (highestYield <= 0.0 && drainedCount / (9F * 9F * 9F) < InjectorResult.drainedPercentage) {
			ChunkPos c = world.getChunkFromBlockCoords(pos).getPos();
			int chunkPos = (c.x + c.z) % ModEntities.MINERALS.size();
			mostLikelyGem = ModEntities.MINERALS.get(Math.abs(chunkPos));
			canSpawnGem = true;
		}
		EntityGem gemSpawned = null;
		if (canSpawnGem) {
			try {
				gemSpawned = (EntityGem)(mostLikelyGem.getConstructors()[0].newInstance(world));
			}
			catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error creating gem: " + e.getMessage());
				canSpawnGem = false;
			}
		}
		ExitHole exitHole = null;
		if (drain && gemSpawned != null) {
			exitHole = ExitHole.create(world, pos, Math.ceil(gemSpawned.height), friction.get(gemSpawned.getClass()) >= 1.0F);
			exitHole.emerge(world);
			gemSpawned.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
		}
		if (drain) {
			for (int x = -4; x <= 4; ++x) {
				for (int y = -4; y <= 4; ++y) {
					for (int z = -4; z <= 4; ++z) {
						if (world.rand.nextBoolean()) {
							BlockPos ore = pos.add(x, y, z);
							drainBlock(world, ore);
						}
					}
				}
			}
		}
		return new InjectorResult(gemSpawned, pos, gemSpawned == null ? 0.0 : defectivity.get(gemSpawned.getClass()), !canSpawnGem, canSpawnGem ? friction.get(gemSpawned.getClass()) >= 1.0F : false, exitHole);
	}
	
	private static void drainBlock(World world, BlockPos ore) {
		IBlockState state = world.getBlockState(ore);
		Block block = state.getBlock();
		
		if (block instanceof BlockBush) {
			world.destroyBlock(ore, false);
			return;
		} else if (block == Blocks.BEDROCK) {
			return;
		}
		
		
		//Dirt/grass -> coarse dirt
		//Coarse dirt -> gravel
		//Stone -> drained stone
		if (state == Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT) || state == Blocks.SAND.getDefaultState()) {
			world.setBlockState(ore, Blocks.GRAVEL.getDefaultState());
		}
		else if (state.getMaterial() == Material.GRASS || state.getMaterial() == Material.GROUND) {
			world.setBlockState(ore, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT));
		}
		else if ((state.getMaterial() == Material.ROCK || state.getMaterial() == Material.IRON) && (state.isFullCube() || block instanceof BlockShulkerBox)) {
			if (block instanceof BlockShulkerBox) {
				TileEntityShulkerBox shulker = (TileEntityShulkerBox) world.getTileEntity(ore);
				shulker.clear();
				shulker.setDestroyedByCreativePlayer(true);
				shulker.setCustomName(null);
				shulker.setLootTable(null, 0);
			}

			if (ore.getY() % 6 == 0 || ore.getY() % 6 == 1) {
				world.setBlockState(ore, ModBlocks.DRAINED_BLOCK_2.getDefaultState());
			} else if (ore.getY() % 5 == 0) {
				world.setBlockState(ore, ModBlocks.DRAINED_BANDS.getDefaultState());
			} else {
				world.setBlockState(ore, ModBlocks.DRAINED_BLOCK.getDefaultState());
			}
		}
	}
}