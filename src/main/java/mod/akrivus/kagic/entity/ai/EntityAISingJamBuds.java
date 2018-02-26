package mod.akrivus.kagic.entity.ai;

import java.util.List;

import mod.akrivus.kagic.entity.humans.EntityConnie;
import mod.akrivus.kagic.entity.humans.EntitySteven;
import mod.akrivus.kagic.init.ModSounds;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;

public class EntityAISingJamBuds extends EntityAIBase {
	private EntitySteven steven;
	private EntityConnie connie;
	private long lastSongTime;
	private int step;

	public EntityAISingJamBuds(EntitySteven steven) {
		this.steven = steven;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		List<EntityConnie> list = this.steven.world.<EntityConnie>getEntitiesWithinAABB(EntityConnie.class, this.steven.getEntityBoundingBox().grow(24.0F, 24.0F, 24.0F));
		double distance = Double.MAX_VALUE;
		for (EntityConnie connie : list) {
			double newDistance = this.steven.getDistanceSq(connie);
			if (newDistance <= distance) {
				distance = newDistance;
				this.connie = connie;
			}
		}
		boolean noteblockNearby = false;
		for (int x = -4; x < 4; ++x) {
			for (int y = -4; y < 4; ++y) {
				for (int z = -4; z < 4; ++z) {
					IBlockState state = this.steven.world.getBlockState(this.steven.getPosition().add(x, y, z));
					if (state.getBlock() == Blocks.NOTEBLOCK || state.getBlock() == Blocks.JUKEBOX) {
						noteblockNearby = true;
					}
				}
			}
		}
		return noteblockNearby && this.steven.world.getWorldTime() - this.lastSongTime > 24000 && this.connie != null && this.steven.getDistanceSq(this.connie) < 8;
	}

	@Override
	public boolean shouldContinueExecuting() {
		return this.connie != null && this.steven.getDistanceSq(this.connie) < 8 && this.step < 40;
	}
	
	@Override
	public void startExecuting() {
		this.steven.silent = true;
		this.connie.silent = true;
		this.lastSongTime = this.steven.world.getWorldTime();
		if (this.steven.ticksExisted % 20 == 0) {
			this.steven.playSound(ModSounds.JAM_BUD_SONG_PARTS[this.step], 8.0F, 1.0F);
			++this.step;
		}
	}
	
	@Override
	public void resetTask() {
		this.steven.silent = false;
		this.connie.silent = false;
		this.connie = null;
		this.step = 0;
	}
	
	@Override
	public void updateTask() {
		this.startExecuting();
	}
}