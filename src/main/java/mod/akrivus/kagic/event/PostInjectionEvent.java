package mod.akrivus.kagic.event;

import java.util.HashMap;

import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PostInjectionEvent extends Event {
	public final World world;
	public final BlockPos pos;
	public final HashMap<Class<EntityGem>, Double> defectivity;
	public final HashMap<Class<EntityGem>, Double> yields;
	public final HashMap<Class<EntityGem>, Double> friction;
	public PostInjectionEvent(World world, BlockPos pos, HashMap<Class<EntityGem>, Double> defectivity, HashMap<Class<EntityGem>, Double> yields, HashMap<Class<EntityGem>, Double> friction) {
		this.world = world;
		this.pos = pos;
		this.defectivity = defectivity;
		this.yields = yields;
		this.friction = friction;
	}
	@Override
	public boolean isCancelable() {
		return true;
	}
}
