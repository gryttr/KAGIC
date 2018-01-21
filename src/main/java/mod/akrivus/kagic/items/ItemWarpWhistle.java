package mod.akrivus.kagic.items;

import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModCreativeTabs;
import mod.akrivus.kagic.tileentity.TileEntityGalaxyPadCore;
import mod.akrivus.kagic.tileentity.TileEntityWarpPadCore;
import mod.heimrarnadalr.kagic.networking.KTPacketHandler;
import mod.heimrarnadalr.kagic.networking.PadDataRequestMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

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
	    	
	    	if (te != null && te.validateWarpPad() && !te.isWarping()) {
				if (te instanceof TileEntityGalaxyPadCore) {
					KTPacketHandler.INSTANCE.sendToServer(new PadDataRequestMessage(true, te.getPos().getX(), te.getPos().getY(), te.getPos().getZ()));
				} else {
					KTPacketHandler.INSTANCE.sendToServer(new PadDataRequestMessage(false, te.getPos().getX(), te.getPos().getY(), te.getPos().getZ()));
				}
	    	} else {
	    		if (te == null) {
	    			KAGIC.instance.chatInfoMessage("Could not find warp pad");
	    		} else if (!te.isValidPad()) {
	    			KAGIC.instance.chatInfoMessage("Warp pad is not valid");
	    		} else if (te.isWarping()) {
	    			KAGIC.instance.chatInfoMessage("Warp pad is already in use");
	    		}
	    	}
    	}
    	return new ActionResult<> (EnumActionResult.SUCCESS, stack);
    }
}
