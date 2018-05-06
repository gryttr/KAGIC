package mod.akrivus.kagic.entity.ai;

import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntitySapphire;
import mod.akrivus.kagic.event.FutureVisionEvent;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class EntityAIFutureVision extends EntityAIBase {
	private final EntitySapphire gem;
	private long lastPrediction;
	private String lastMessage = "";
	public EntityAIFutureVision(EntitySapphire gem) {
		this.gem = gem;
		this.setMutexBits(0);
	}
	
	@Override
	public boolean shouldExecute() {
		return !this.gem.isDefective() && this.gem.getOwner() != null && this.gem.getOwner().getDistance(this.gem) < 16 && this.gem.world.getTotalWorldTime() - this.lastPrediction > 200 + this.gem.world.rand.nextInt(200);
	}
	
	@Override
	public void startExecuting() {
		World world = this.gem.world;
		FutureVisionEvent event = new FutureVisionEvent(this.gem, null);
		if (MinecraftForge.EVENT_BUS.post(event)) return;
		if (world.rand.nextInt(100) == 0 && world.rand.nextBoolean() && KAGIC.isBirthdayTomorrow() /*&& !this.lastMessage.equals("birthday")*/) {
			this.sendMessage("birthday");
		}
		List<EntityLivingBase> list = this.gem.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, this.gem.getEntityBoundingBox().grow(24.0D, 8.0D, 24.0D));
        double maxDistance = Double.MAX_VALUE;
        for (EntityLivingBase weirdo : list) {
            double newDistance = weirdo.getDistanceSq(this.gem);
            if (newDistance <= maxDistance) {
                maxDistance = newDistance;
                if (!this.gem.isOwner(weirdo)) {
                	if (weirdo instanceof EntityGem) {
                		EntityGem possibleRebel = (EntityGem) weirdo;
                		if (this.gem.isOwner(possibleRebel.getOwner()) && possibleRebel.isTraitor() && !this.lastMessage.equals(possibleRebel.getUniqueID().toString())) {
                			this.sendMessage("rebel", possibleRebel.getName());
                			this.lastMessage = possibleRebel.getUniqueID().toString();
                		}
                	}
                	else if (weirdo instanceof EntityMob) {
                		EntityMob possibleAttacker = (EntityMob) weirdo;
                		if (possibleAttacker.getAttackTarget() != null && possibleAttacker.getAttackTarget().equals(this.gem.getOwner()) && !this.lastMessage.equals(possibleAttacker.getUniqueID().toString())) {
                			this.sendMessage("attacker", possibleAttacker.getName());
                			this.lastMessage = possibleAttacker.getUniqueID().toString();
                		}
                	}
                	else if (weirdo instanceof EntityPlayer) {
                		EntityPlayer possibleAttacker = (EntityPlayer) weirdo;
                		if ((possibleAttacker.getHeldItemMainhand().getItem() instanceof ItemSword || possibleAttacker.getHeldItemMainhand().getItem() instanceof ItemBow) && !this.lastMessage.equals(possibleAttacker.getUniqueID().toString())) {
                			this.sendMessage("attacker", possibleAttacker.getName());
                			this.lastMessage = possibleAttacker.getUniqueID().toString();
                		}
                	}
                }
            }
        }
	}

	@Override
    public boolean shouldContinueExecuting() {
		return false;
    }
    
    private void sendMessage(String line, String formatting) {
		this.gem.getOwner().sendMessage(new TextComponentString("<" + this.gem.getName() + "> " + new TextComponentTranslation("command.kagic.sapphire_" + line, formatting).getUnformattedText()));
		//this.gem.getOwner().addStat(ModAchievements.YOUR_CLARITY);
		this.lastPrediction = this.gem.world.getTotalWorldTime();
		this.lastMessage = formatting;
	}
    
	private void sendMessage(String line) {
		this.gem.getOwner().sendMessage(new TextComponentString("<" + this.gem.getName() + "> " + new TextComponentTranslation("command.kagic.sapphire_" + line).getUnformattedText()));
		//this.gem.getOwner().addStat(ModAchievements.WHAT_A_MYSTERY);
		this.lastPrediction = this.gem.world.getTotalWorldTime();
		this.lastMessage = line;
	}
}
