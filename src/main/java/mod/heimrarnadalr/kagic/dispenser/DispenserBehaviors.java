package mod.heimrarnadalr.kagic.dispenser;

import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.items.ItemGem;
import net.minecraft.block.BlockDispenser;

public class DispenserBehaviors {
	public static BehaviorGemDispense GEMDISPENSE = new BehaviorGemDispense();
	
	public static void register() {
		for (ItemGem gem : ModItems.GEM_TABLE.values()) {
			BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(gem, DispenserBehaviors.GEMDISPENSE);
		}
	}
}
