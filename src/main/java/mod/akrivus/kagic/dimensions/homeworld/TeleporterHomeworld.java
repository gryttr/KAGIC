package mod.akrivus.kagic.dimensions.homeworld;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterHomeworld extends Teleporter {
	public TeleporterHomeworld(WorldServer worldIn) {
		super(worldIn);
	}
	public void placeInPortal(Entity entityIn, float rotationYaw) {
		entityIn.setLocationAndAngles(0, 60, 0, entityIn.rotationYaw, 0.0F);
        entityIn.motionX = 0.0D;
        entityIn.motionY = 0.0D;
        entityIn.motionZ = 0.0D;
	}
	public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
		return false;
	}
}
