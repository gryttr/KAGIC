package mod.akrivus.kagic.blocks;

import mod.akrivus.kagic.init.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockPinkSandstone extends Block {
	//Texture color: EEC2D5
	//Water color: FFE3F4
	public static final PropertyEnum<BlockPinkSandstone.EnumType> TYPE = PropertyEnum.<BlockPinkSandstone.EnumType>create("type", BlockPinkSandstone.EnumType.class);
	
	public BlockPinkSandstone() {
		super(Material.ROCK);
		this.setSoundType(SoundType.STONE);
		this.setHardness(0.8F);
		this.setResistance(4F);
		this.setUnlocalizedName("pink_sandstone");
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
	}

	@Override
	public int damageDropped(IBlockState state) {
		return ((BlockPinkSandstone.EnumType)state.getValue(TYPE)).getMetadata();
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (BlockPinkSandstone.EnumType blocksandstone$enumtype : BlockPinkSandstone.EnumType.values()) {
			items.add(new ItemStack(this, 1, blocksandstone$enumtype.getMetadata()));
		}
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return MapColor.PINK;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, BlockPinkSandstone.EnumType.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((BlockPinkSandstone.EnumType)state.getValue(TYPE)).getMetadata();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {TYPE});
	}

	public static enum EnumType implements IStringSerializable {
		DEFAULT(0, "pink_sandstone", "default"),
		CHISELED(1, "chiseled_pink_sandstone", "chiseled"),
		SMOOTH(2, "smooth_pink_sandstone", "smooth"),
		WAVY(3, "wavy_pink_sandstone", "wavy");

		private static final BlockPinkSandstone.EnumType[] META_LOOKUP = new BlockPinkSandstone.EnumType[values().length];
		private final int metadata;
		private final String name;
		private final String unlocalizedName;

		private EnumType(int meta, String name, String unlocalizedName) {
			this.metadata = meta;
			this.name = name;
			this.unlocalizedName = unlocalizedName;
		}

		public int getMetadata() {
			return this.metadata;
		}

		@Override
		public String toString() {
			return this.name;
		}

		public static BlockPinkSandstone.EnumType byMetadata(int meta) {
			if (meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		@Override
		public String getName() {
			return this.name;
		}

		public String getUnlocalizedName() {
			return this.unlocalizedName;
		}

		static {
			for (BlockPinkSandstone.EnumType blockpinksandstone$enumtype : values())
			{
				META_LOOKUP[blockpinksandstone$enumtype.getMetadata()] = blockpinksandstone$enumtype;
			}
		}
	}
}