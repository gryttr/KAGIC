package mod.heimrarnadalr.kagic.world.structure;

import java.util.Random;

import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModBiomes;
import mod.heimrarnadalr.kagic.worlddata.WorldDataRuins;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GiantWeapon extends RuinStructure {

	public GiantWeapon(String type) {
		super(type, 0, Blocks.GRASS.getDefaultState(), true, true);
		
		this.structures.add("/assets/kagic/structures/giant_weapons/axe_1.schematic");
		this.structures.add("/assets/kagic/structures/giant_weapons/axe_2.schematic");
		this.structures.add("/assets/kagic/structures/giant_weapons/diagonal_sword_1.schematic");
		this.structures.add("/assets/kagic/structures/giant_weapons/diagonal_sword_2.schematic");
		this.structures.add("/assets/kagic/structures/giant_weapons/hammer_1.schematic");
		this.structures.add("/assets/kagic/structures/giant_weapons/horizontal_sword_1.schematic");
		this.structures.add("/assets/kagic/structures/giant_weapons/mace_1.schematic");
		this.structures.add("/assets/kagic/structures/giant_weapons/vertical_sword_1.schematic");
		this.structures.add("/assets/kagic/structures/giant_weapons/vertical_sword_2.schematic");
		
		this.allowedBiomes.add(ModBiomes.STRAWBERRYBATTLEFIELD);
	}

	@Override
	protected boolean checkDistance(World world, BlockPos pos) {
		WorldDataRuins existingRuins = WorldDataRuins.get(world);
		return existingRuins.checkDistances(this.type, pos, 12*12);
	}
	
	@Override
	protected boolean checkCorners(World world, BlockPos pos) {
		return true;
	}
	
	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		if (rand.nextInt(5) != 0) {
			return false;
		}

		int depth = rand.nextInt(3) + 1;
		return super.generate(world, rand, pos.down(depth));
	}
}
