package mod.heimrarnadalr.kagic.proxies;

import java.util.LinkedHashMap;

import mod.akrivus.kagic.blocks.BlockPinkSandstone;
import mod.akrivus.kagic.client.gui.GUIGalaxyPadSelection;
import mod.akrivus.kagic.client.gui.GUIWarpPadSelection;
import mod.akrivus.kagic.client.model.FluidModelMapper;
import mod.akrivus.kagic.init.ModBlocks;
import mod.akrivus.kagic.init.ModItems;
import mod.heimrarnadalr.kagic.worlddata.GalaxyPadLocation;
import mod.heimrarnadalr.kagic.worlddata.WarpPadDataEntry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		ModelLoader.setCustomStateMapper(ModBlocks.PINK_SANDSTONE, (new StateMap.Builder()).withName(BlockPinkSandstone.TYPE).build());
	}

	/*@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		ModelLoader.registerItemVariants(Item.getItemFromBlock(ModBlocks.PINK_SANDSTONE), new ResourceLocation("kagic:pink_sandstone"), new ResourceLocation("kagic:chiseled_pink_sandstone"), new ResourceLocation("kagic:smooth_pink_sandstone"));
	}*/

	@Override
	public void openWarpPadSelectionGUI(LinkedHashMap<BlockPos, WarpPadDataEntry> padData, int x, int y, int z) {
		Minecraft.getMinecraft().displayGuiScreen(new GUIWarpPadSelection(padData, x, y, z));		
	}

	@Override
	public void openGalaxyPadSelectionGUI(LinkedHashMap<GalaxyPadLocation, WarpPadDataEntry> padData, int x, int y, int z) {
		Minecraft.getMinecraft().displayGuiScreen(new GUIGalaxyPadSelection(padData, x, y, z));		
	}
}
