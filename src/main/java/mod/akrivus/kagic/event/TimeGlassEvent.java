package mod.akrivus.kagic.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

public class TimeGlassEvent extends Event {
	public final EntityPlayer player;
	public final ItemStack stack;
	public TimeGlassEvent(EntityPlayer player, ItemStack stack) {
		this.player = player;
		this.stack = stack;
	}
	@Override
	public boolean isCancelable() {
		return true;
	}
}
