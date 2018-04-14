package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityBismuth;
import mod.akrivus.kagic.skills.Speak;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class BuildTower extends Speak<EntityBismuth> {
	private float[] direction = new float[] { 0, 0 };
	private boolean startedBuilding = true;
	private IBlockState bridgeBlock = null;
	private ItemStack placeStack = null;
	private boolean stillBuilding = true;
	private int lastBlockPlace = 0;
	private int height = 24;
	private int offset = 0;
	public BuildTower() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] { 
			"make",
			"build",
			"create",
			"construct",
			"assemble",
			"generate"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] {
			"tower",
			"pillar",
			"dick",
			"penis"
		}));
		this.canBeStopped = true;
		this.killsOnEnd = true;
		this.can(RunWith.RESTING);
		this.task(true);
	}
	@Override
	public boolean triggered(EntityBismuth gem) {
		boolean previous = this.isAllowedToRun;
		if (previous) {
			if (!this.collectedNumbers.isEmpty()) {
				try {
					this.height = Integer.parseInt(this.collectedNumbers.get(0));
				}
				catch (Exception ex) {
					this.height = 24;
				}
			}
		}
		return previous;
	}
	@Override
	public boolean proceed(EntityBismuth gem) {
		return this.stillBuilding && this.getBlock(gem);
	}
	@Override
	public void run(EntityBismuth gem) {
		if (this.lastBlockPlace > 20) {
			if (this.offset < this.height) {
				boolean placed = false;
				BlockPos nextPos = gem.getPosition().add(this.direction[0], 0, this.direction[1]);
				gem.lookAt(nextPos.add(this.direction[0], 4, this.direction[1]));
				placed = gem.placeBlock(this.bridgeBlock, nextPos.up(this.offset));
				if (placed) {
					gem.setPosition(nextPos.getX(), nextPos.getY() + 1, nextPos.getZ());
					this.placeStack.shrink(1);
					if (this.placeStack.isEmpty()) {
						placed = this.getBlock(gem) && placed;
					}
				}
				this.stillBuilding = placed;
				this.lastBlockPlace = 0;
			}
		}
		++this.lastBlockPlace;
	}
	public boolean getBlock(EntityBismuth gem) {
		InventoryBasic inventory = gem.gemStorage;
		for (String subject : this.collectedSubjects) {
			for (int i = 0; i < inventory.getSizeInventory(); ++i) {
				ItemStack stack = inventory.getStackInSlot(i);
				Item item = stack.getItem();
				if (item instanceof ItemBlock) {
					if (stack.getDisplayName().toLowerCase().contains(subject)) {
						this.bridgeBlock = Block.getBlockFromItem(item).getStateForPlacement(gem.world, gem.getPosition(), EnumFacing.fromAngle(gem.rotationYaw), (float) gem.posX, (float) gem.posY, (float) gem.posZ, item.getMetadata(stack.getMetadata()), this.commandingPlayer, EnumHand.MAIN_HAND);
						this.placeStack = stack;
						return true;
					}
				}
			}
		}
		if (this.collectedSubjects.size() == 0) {
			for (int i = 0; i < inventory.getSizeInventory(); ++i) {
				ItemStack stack = inventory.getStackInSlot(i);
				Item item = stack.getItem();
				if (item instanceof ItemBlock) {
					this.bridgeBlock = Block.getBlockFromItem(item).getStateForPlacement(gem.world, gem.getPosition(), EnumFacing.fromAngle(gem.rotationYaw), (float) gem.posX, (float) gem.posY, (float) gem.posZ, item.getMetadata(stack.getMetadata()), this.commandingPlayer, EnumHand.MAIN_HAND);
					this.placeStack = stack;
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public String toString() {
		return "building a tower";
	}
}
