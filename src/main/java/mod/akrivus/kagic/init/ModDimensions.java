package mod.akrivus.kagic.init;

import mod.akrivus.kagic.dimensions.homeworld.WorldProviderHomeworld;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class ModDimensions {
	public static final DimensionType HOMEWORLD = DimensionType.register("homeworld", "_hw", DimensionManager.getNextFreeDimId(), WorldProviderHomeworld.class, false);
	
	public static void register() {
		DimensionManager.registerDimension(DimensionManager.getNextFreeDimId(), ModDimensions.HOMEWORLD);
	}
}
