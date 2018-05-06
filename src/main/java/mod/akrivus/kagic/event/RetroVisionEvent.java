package mod.akrivus.kagic.event;

import mod.akrivus.kagic.entity.gem.EntitySapphire;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RetroVisionEvent extends Event {
	public final EntitySapphire sapphire;
	public final EntityPlayer player;
	public RetroVisionEvent(EntitySapphire sapphire, EntityPlayer player) {
		this.sapphire = sapphire;
		this.player = player;
	}
	@Override
	public boolean isCancelable() {
		return true;
	}
}
