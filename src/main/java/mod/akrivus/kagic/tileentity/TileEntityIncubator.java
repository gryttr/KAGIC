package mod.akrivus.kagic.tileentity;

import mod.akrivus.kagic.blocks.BlockInjector;
import mod.akrivus.kagic.init.ModBlocks;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import mod.akrivus.kagic.util.injector.Incubator;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityIncubator extends TileEntityLockableLoot implements ITickable {
	private NonNullList<ItemStack> chestContents = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.chestContents = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		if (!this.checkLootAndRead(compound)) {
			ItemStackHelper.loadAllItems(compound, this.chestContents);
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		if (!this.checkLootAndWrite(compound)) {
			ItemStackHelper.saveAllItems(compound, this.chestContents);
		}
		return compound;
	}
	
	@Override
	public void update() {
		Block block = this.world.getBlockState(this.pos.down()).getBlock();
		if ((block == ModBlocks.ANALOG_INJECTOR || block == ModBlocks.INJECTOR) && !this.world.isBlockPowered(this.pos.down())) {
			boolean containsBase = false;
			for (int i = 0; i < this.chestContents.size(); ++i) {
				ItemStack stack = this.chestContents.get(i);
				if (stack.getItem() == ModItems.ACTIVATED_GEM_BASE) {
					stack.shrink(1);
					this.chestContents.set(i, stack);
					containsBase = true;
					break;
				}
			}
			if (containsBase) {
				if (this.world.getBlockState(this.pos.down()).getBlock() == ModBlocks.ANALOG_INJECTOR) {
					this.world.setBlockState(this.pos.down(), ModBlocks.EQUIPPED_ANALOG_INJECTOR.getDefaultState().withProperty(BlockInjector.FACING, this.world.getBlockState(this.pos.down()).getValue(BlockInjector.FACING)));	
				}
				else if (this.world.getBlockState(this.pos.down()).getBlock() == ModBlocks.INJECTOR) {
					this.world.setBlockState(this.pos.down(), ModBlocks.EQUIPPED_INJECTOR.getDefaultState().withProperty(BlockInjector.FACING, this.world.getBlockState(this.pos.down()).getValue(BlockInjector.FACING)));
				}
				this.world.playSound(null, this.pos.down(), ModSounds.BLOCK_INJECTOR_CLOSE, SoundCategory.BLOCKS, 0.3F, 1.0F);
			}
		}
	}
	
	@Override
	public int getSizeInventory() {
		return 9;
	}
	
	@Override
	public boolean isEmpty() {
		return false;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public String getName() {
		return new TextComponentTranslation("tile.incubator.name").getUnformattedText();
	}
	
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new Incubator(playerInventory, this);
	}
	
	@Override
	public String getGuiID() {
		return "kagic:incubator";
	}
	
	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.chestContents;
	}
}
