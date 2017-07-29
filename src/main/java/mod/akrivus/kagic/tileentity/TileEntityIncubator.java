package mod.akrivus.kagic.tileentity;

import mod.akrivus.kagic.blocks.BlockInjector;
import mod.akrivus.kagic.init.ModBlocks;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import mod.akrivus.kagic.util.injector.Incubator;
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
	public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.chestContents = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if (!this.checkLootAndRead(compound)) {
            ItemStackHelper.loadAllItems(compound, this.chestContents);
        }
    }
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (!this.checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, this.chestContents);
        }
        return compound;
    }
    public void update() {
    	if (this.world.getBlockState(this.pos.down()).getBlock() == ModBlocks.INJECTOR && !this.world.isBlockPowered(this.pos.down())) {
    		boolean containsBase = false;
    		for (int i = 0; i < this.chestContents.size(); ++i) {
    			if (this.chestContents.get(i).getItem() == ModItems.ACTIVATED_GEM_BASE) {
    				this.chestContents.set(i, ItemStack.EMPTY);
    				containsBase = true;
    				break;
    			}
    		}
    		if (containsBase) {
    			this.world.setBlockState(this.pos.down(), ModBlocks.EQUIPPED_INJECTOR.getDefaultState().withProperty(BlockInjector.FACING, this.world.getBlockState(this.pos.down()).getValue(BlockInjector.FACING)));
    			this.world.playSound(null, this.pos.down(), ModSounds.BLOCK_INJECTOR_CLOSE, SoundCategory.BLOCKS, 0.3F, 1.0F);
    		}
    	}
    }
	public int getSizeInventory() {
		return 9;
	}
	public boolean isEmpty() {
		return false;
	}
	public int getInventoryStackLimit() {
		return 64;
	}
	public String getName() {
		return new TextComponentTranslation("tile.incubator.name").getUnformattedText();
	}
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new Incubator(playerInventory, this);
	}
	public String getGuiID() {
		return "kagic:incubator";
	}
	protected NonNullList<ItemStack> getItems() {
		return this.chestContents;
	}
}
