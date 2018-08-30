package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.fusion.EntitySameTypeFusion;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

public class GemLayer {
	public String getName(EntityGem gem) {
		ResourceLocation loc = EntityList.getKey(gem);
		String name = loc.getResourcePath();
		if (gem instanceof EntitySameTypeFusion) {
			name = name.replaceFirst("_fusion", "");
		}
		if (loc.getResourceDomain().equals("kagic")) {
	        return name.replaceFirst("kagic.", "");
		}
		else {
	        return name;
		}
	}

}
