package mod.akrivus.kagic.entity.ai;

import mod.akrivus.kagic.entity.gem.EntitySapphire;
import mod.akrivus.kagic.event.RetroVisionEvent;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class EntityAIRetroVision extends EntityAIBase {
	private final EntitySapphire gem;
	private long lastPrediction;
	private String lastMessage = "";
	public EntityAIRetroVision(EntitySapphire gem) {
		this.gem = gem;
		this.setMutexBits(0);
	}
	
	@Override
	public boolean shouldExecute() {
		return this.gem.isDefective() && this.gem.getOwner() != null && this.gem.getOwner().getDistance(this.gem) < 16 && this.gem.world.getTotalWorldTime() - this.lastPrediction > 200 + this.gem.world.rand.nextInt(200);
	}
	
	@Override
	public void startExecuting() {
		World world = this.gem.world;
		RetroVisionEvent event = new RetroVisionEvent(this.gem, null);
		if (MinecraftForge.EVENT_BUS.post(event)) return;
		if (world.rand.nextInt(100) == 0 && world.rand.nextBoolean() && !this.lastMessage.equals("wallbreaker")) {
			this.sendMessage("wallbreaker");
		}
		else if (this.gem.getOwner().ticksExisted < 100 && !this.lastMessage.equals("died")) {
			this.sendMessage("died");
		}
		else if (this.gem.getOwner().isBurning() && !this.lastMessage.equals("fire")) {
			this.sendMessage("fire");
		}
		else if (this.gem.getOwner().isInWater() && !this.lastMessage.equals("water")) {
			this.sendMessage("water");
		}
		else if (world.isRaining() && !this.lastMessage.equals("rain")) {
			if (!world.canSnowAt(this.gem.getPosition(), false)) {
				this.sendMessage("rain");
			}
		}
		else if (world.isRaining() && !this.lastMessage.equals("snow")) {
			if (world.canSnowAt(this.gem.getPosition(), false)) {
				this.sendMessage("snow");
			}
		}
		/*
		else if (world.getTotalWorldTime() - world.getLastLightningBolt() > 20 && world.getTotalWorldTime() - world.getLastLightningBolt() < 200 && !this.lastMessage.equals("thunder")) {
			this.sendMessage("thunder");
		}
		*/
		else if (this.gem.getOwner().getAttackingEntity() != null && !this.lastMessage.equals(this.gem.getOwner().getAttackingEntity().getName())) {
			this.sendMessage("hurt_by", this.gem.getOwner().getAttackingEntity().getName());
		}
		else if (world.getWorldTime() < 600 && !this.lastMessage.equals("sunrise")) {
			this.sendMessage("sunrise");
		}
		else if (world.getWorldTime() > 600 && world.getWorldTime() < 6000 && !this.lastMessage.equals("noon")) {
			this.sendMessage("noon");
		}
		else if (world.getWorldTime() > 6000 && world.getWorldTime() < 14000 && !this.lastMessage.equals("sunset")) {
			this.sendMessage("sunset");
		}
		else if (world.getWorldTime() > 14000 && world.getWorldTime() < 22000 && !this.lastMessage.equals("night")) {
			this.sendMessage("night");
		}
		else if (world.getWorldTime() > 22000 && !this.lastMessage.equals("moonset")) {
			this.sendMessage("moonset");
		}
	}
	
	@Override
    public boolean shouldContinueExecuting() {
		return false;
    }
    
	private void sendMessage(String line, String formatting) {
		this.gem.getOwner().sendMessage(new TextComponentString("<" + this.gem.getName() + "> " + new TextComponentTranslation("command.kagic.padparadscha_" + line, formatting).getUnformattedComponentText()));
		//this.gem.getOwner().addStat(ModAchievements.WHAT_A_MYSTERY);
		this.lastPrediction = this.gem.world.getTotalWorldTime();
		this.lastMessage = formatting;
	}
	
	private void sendMessage(String line) {
		this.gem.getOwner().sendMessage(new TextComponentString("<" + this.gem.getName() + "> " + new TextComponentTranslation("command.kagic.padparadscha_" + line).getUnformattedComponentText()));
		//this.gem.getOwner().addStat(ModAchievements.WHAT_A_MYSTERY);
		this.lastPrediction = this.gem.world.getTotalWorldTime();
		this.lastMessage = line;
	}
}
