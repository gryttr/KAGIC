package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;

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

public class BuildBridge extends Speak<EntityBismuth> {
	private float[] direction = new float[] { 0, 0 };
	private IBlockState bridgeBlock = null;
	private ItemStack placeStack = null;
	private boolean stillBuilding = true;
	private int lastBlockPlace = 0;
	private int width = 1;
	public BuildBridge() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] { 
			"make",
			"build",
			"create",
			"construct",
			"assemble",
			"generate"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] {
			"bridge"
		}));
		this.canBeStopped = true;
		this.killsOnEnd = true;
		this.can(RunWith.RESTING);
		this.task(true);
	}
	@Override
	public boolean speak(EntityBismuth gem, EntityPlayer player, String message) {
		boolean result = super.speak(gem, player, message);
		if (result) {
			int deg = MathHelper.floor(((player.rotationYaw * 4.0F) / 360.0F) + 0.5D) & 3;
			gem.rotationYaw = player.rotationYaw;
			switch (deg) {
			case 0:
				this.direction = new float[] { 0, 1 };
				break;
			case 1:
				this.direction = new float[] { -1, 0 };
				break;
			case 2:
				this.direction = new float[] { 0, -1 };
				break;
			case 3:
				this.direction = new float[] { 1, 0 };
				break;
			}
		}
		return result;
	}
	@Override
	public boolean triggered(EntityBismuth gem) {
		boolean previous = this.isAllowedToRun;
		if (previous) {
			boolean finished = false;
			int blocksPlaced = 0;
			BlockPos start = gem.getPosition();
			while (blocksPlaced < 64) {
				BlockPos nextPos = start.add(this.direction[0], -1, this.direction[1]);
				if (gem.world.getBlockState(nextPos).getBlock().isTopSolid(gem.world.getBlockState(nextPos))) {
					finished = true;
					break;
				}
				start = start.add(this.direction[0], 0, this.direction[1]);
				++blocksPlaced;
			}
			previous = finished && blocksPlaced < 64 && this.getBlock(gem);
		}
		if (previous) {
			if (!this.collectedNumbers.isEmpty()) {
				try {
					this.width = Integer.parseInt(this.collectedNumbers.get(0));
				}
				catch (Exception ex) {
					this.width = 1;
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
			BlockPos nextPos = gem.getPosition().add(this.direction[0], -1, this.direction[1]);
			gem.lookAt(nextPos.add(this.direction[0], -1, this.direction[1]));
			boolean placed = false;
			for (int i = this.width / 2 * -1; i < Math.max(1, this.width / 2); ++i) {
				BlockPos placePos = nextPos.add(this.direction[1] * i, 0, this.direction[0] * i);
				if (!gem.world.getBlockState(placePos).getBlock().isTopSolid(gem.world.getBlockState(placePos))) {
					placed = gem.placeBlock(this.bridgeBlock, placePos);
					if (placed) {
						this.placeStack.shrink(1);
						if (this.placeStack.isEmpty()) {
							this.getBlock(gem);
						}
					}
				}
			}
			gem.tryToMoveTo(nextPos.up());
			this.stillBuilding = placed;
			this.lastBlockPlace = 0;
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
		return this.getClass().getSimpleName();
	}
}
