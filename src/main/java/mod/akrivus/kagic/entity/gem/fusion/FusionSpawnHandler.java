package mod.akrivus.kagic.entity.gem.fusion;

import mod.akrivus.kagic.entity.EntityFusionGem;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FusionSpawnHandler {

	public FusionSpawnHandler() {
	}

	@SubscribeEvent
	public void onFusionSpawn(EntityJoinWorldEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof EntityFusionGem) {
			if (((EntityFusionGem)entity).getFusionCount() == 0) {
				KAGIC.instance.chatInfoMessage("ERROR: cannot spawn a fusion with no fusion gems!");
				KAGIC.instance.chatInfoMessage("Did you try to spawn a fusion with /summon?");
				KAGIC.instance.chatInfoMessage("Fusions can only be created by combining two existing gems!");
				event.setCanceled(true);
			}
		}
	}
}
