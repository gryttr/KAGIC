package mod.akrivus.kagic.event;

import mod.akrivus.kagic.entity.gem.EntityBismuth;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

public class BismuthInteractEvent extends Event {
	public final EntityBismuth bismuth;
	public final EntityPlayer player;
	public final ItemStack stack;
	public BismuthInteractEvent(EntityBismuth bismuth, EntityPlayer player, ItemStack stack) {
		this.bismuth = bismuth;
		this.player = player;
		this.stack = stack;
	}
	@Override
	public boolean isCancelable() {
		return true;
	}
}
