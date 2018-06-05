package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;

import mod.akrivus.kagic.entity.gem.EntityPeridot;
import net.minecraft.block.Block;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;

public class HarvestAndReplantNetherWart extends HarvestAndReplant {
	public HarvestAndReplantNetherWart() {
		super();
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] {
			"wart"
		}));
	}
	@Override
	public boolean isCorrectFarmBlock(IBlockState state) {
		return state.getBlock() == Blocks.SOUL_SAND;
	}
	@Override
	public boolean isCorrectPlant(IBlockState state) {
		return state.getBlock() == Blocks.NETHER_WART && state.getValue(BlockNetherWart.AGE) >= 3;
	}
	@Override
	public IBlockState getPlant(EntityPeridot gem) {
		return Blocks.NETHER_WART.getDefaultState();
	}
	public ItemStack getSeed(EntityPeridot gem) {
		InventoryBasic inventory = gem.gemStorage;
		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			Item item = inventory.getStackInSlot(i).getItem();
			if (item == Items.NETHER_WART) {
				return inventory.getStackInSlot(i);
			}
		}
		return ItemStack.EMPTY;
	}
	@Override
	public boolean hasSeeds(EntityPeridot gem) {
		InventoryBasic inventory = gem.gemStorage;
		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			Item item = inventory.getStackInSlot(i).getItem();
			if (item == Items.NETHER_WART) {
				return true;
			}
		}
		return false;
	}
}
