package mod.akrivus.kagic.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockMoonBlessedStone extends Block {

	public BlockMoonBlessedStone(String name) {
		super(Material.ROCK);

		this.setHardness(1.5F);
		this.setResistance(10F);
		this.setSoundType(SoundType.STONE);
		
		this.setUnlocalizedName(name);
	}

}
