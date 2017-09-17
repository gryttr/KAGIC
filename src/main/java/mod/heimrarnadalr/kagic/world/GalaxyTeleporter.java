package mod.heimrarnadalr.kagic.world;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class GalaxyTeleporter extends Teleporter {
	private double posX;
	private double posY;
	private double posZ;
	
	public GalaxyTeleporter(WorldServer worldIn, double posX, double posY, double posZ) {
		super(worldIn);
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	@Override
	public void placeInPortal(Entity entity, float rotationYaw) {
		entity.setPosition(posX, posY, posZ);
	}
}
