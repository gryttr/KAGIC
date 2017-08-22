package mod.akrivus.kagic.items;

import mod.akrivus.kagic.client.gui.KTGUIProxy;
import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModCreativeTabs;
import mod.akrivus.kagic.tileentity.TileEntityWarpPadCore;
import mod.heimrarnadalr.kagic.networking.KTPacketHandler;
import mod.heimrarnadalr.kagic.networking.PadDataRequestMessage;
import mod.heimrarnadalr.kagic.networking.TENameMessage;
import mod.heimrarnadalr.kagic.worlddata.WorldDataWarpPad;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWarpWhistle extends Item {

    public ItemWarpWhistle() {
    	super();
        this.setUnlocalizedName("warp_whistle");
        this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer playerIn, EnumHand hand) {
    	ItemStack stack = playerIn.getHeldItem(hand);
    	if (world.isRemote) {
	    	TileEntityWarpPadCore te = TileEntityWarpPadCore.getEntityPad(playerIn);//getPlayerPadTE(world, playerIn);
	    	if (te != null && te.isValidPad() && !te.isWarping()) {
				//playerIn.openGui(KAGIC.instance, KTGUIProxy.GUIWARPPADSELECTIONID, world, te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
				KTPacketHandler.INSTANCE.sendToServer(new PadDataRequestMessage(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ()));
	    	}
    	}
    	return new ActionResult<> (EnumActionResult.SUCCESS, stack);
    }
}
