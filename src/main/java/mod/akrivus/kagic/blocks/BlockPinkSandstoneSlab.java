package mod.akrivus.kagic.blocks;

import java.util.Random;

import mod.akrivus.kagic.init.ModBlocks;
import mod.akrivus.kagic.init.ModCreativeTabs;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPinkSandstoneSlab extends BlockSlab {
	public static final PropertyEnum<BlockPinkSandstoneSlab.EnumType> VARIANT = PropertyEnum.<BlockPinkSandstoneSlab.EnumType>create("variant", BlockPinkSandstoneSlab.EnumType.class);

	public BlockPinkSandstoneSlab() {
		super(Material.ROCK, MapColor.PINK);
		IBlockState defaultState = this.blockState.getBaseState();
		if (!this.isDouble()) {
			defaultState = defaultState.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
		}
		this.setSoundType(SoundType.STONE);
		this.setHardness(0.8F);
		this.setResistance(4F);
		this.setUnlocalizedName("pink_sandstone_slab");
		this.setDefaultState(defaultState.withProperty(VARIANT, BlockPinkSandstoneSlab.EnumType.PINK));
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
		this.useNeighborBrightness = true;
	}

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.PINK_SANDSTONE_SLAB);
    }

	@Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(ModBlocks.PINK_SANDSTONE_SLAB, 1, ((BlockPinkSandstoneSlab.EnumType)state.getValue(VARIANT)).getMetadata());
    }

    @Override
	public String getUnlocalizedName(int meta) {
		return super.getUnlocalizedName() + "." + EnumType.byMetadata(meta).getUnlocalizedName();
	}

	@Override
	public boolean isDouble() {
		return false;
	}

	@Override
	public IProperty<?> getVariantProperty() {
		return VARIANT;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {
		return EnumType.byMetadata(stack.getMetadata() & 7);
	}

	@Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        for (BlockPinkSandstoneSlab.EnumType blockstoneslab$enumtype : BlockPinkSandstoneSlab.EnumType.values())
        {
            items.add(new ItemStack(this, 1, blockstoneslab$enumtype.getMetadata()));
        }
    }

    @Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, BlockPinkSandstoneSlab.EnumType.byMetadata(meta & 7));

		if (!this.isDouble()) {
			iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
		}

		return iblockstate;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | ((BlockPinkSandstoneSlab.EnumType)state.getValue(VARIANT)).getMetadata();

		if (this.isDouble()) {
			//No SEAMLESS property, unlike BlockStoneSlab
		}
		else if (state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
			i |= 8;
		}

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return this.isDouble() ? new BlockStateContainer(this, new IProperty[] {VARIANT}) : new BlockStateContainer(this, new IProperty[] {HALF, VARIANT});
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return ((BlockPinkSandstoneSlab.EnumType)state.getValue(VARIANT)).getMetadata();
	}
	
	public static enum EnumType implements IStringSerializable {
		PINK(0, "pink_sandstone");

		private static final BlockPinkSandstoneSlab.EnumType[] META_LOOKUP = new BlockPinkSandstoneSlab.EnumType[values().length];
		private final int meta;
		private final String name;
		private final String unlocalizedName;

		private EnumType(int metaData, String name) {
			this(metaData, name, name);
		}

		private EnumType(int metaData, String name, String unlocalizedName) {
			this.meta = metaData;
			this.name = name;
			this.unlocalizedName = unlocalizedName;
		}

		public int getMetadata() {
			return this.meta;
		}

		@Override
		public String toString() {
			return this.name;
		}

		public static BlockPinkSandstoneSlab.EnumType byMetadata(int meta) {
			if (meta < 0 || meta >= META_LOOKUP.length)
			{
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
			for (BlockPinkSandstoneSlab.EnumType blockstoneslab$enumtype : values())
			{
				META_LOOKUP[blockstoneslab$enumtype.getMetadata()] = blockstoneslab$enumtype;
			}
		}
	}
}