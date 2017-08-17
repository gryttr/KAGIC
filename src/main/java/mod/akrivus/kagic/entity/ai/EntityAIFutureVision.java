package mod.akrivus.kagic.entity.ai;

import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntitySapphire;
import mod.akrivus.kagic.init.ModAchievements;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class EntityAIFutureVision extends EntityAIBase {
	private final EntitySapphire gem;
	private long lastPrediction;
	private String lastMessage = "";
	public EntityAIFutureVision(EntitySapphire gem) {
		this.gem = gem;
		this.setMutexBits(0);
	}
	public boolean shouldExecute() {
		return this.gem.getOwner() != null && this.gem.getOwner().getDistanceToEntity(this.gem) < 16 && this.gem.world.getTotalWorldTime() - this.lastPrediction > 100 + this.gem.world.rand.nextInt(100);
	}
	public void startExecuting() {
		List<EntityLivingBase> list = this.gem.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, this.gem.getEntityBoundingBox().grow(24.0D, 8.0D, 24.0D));
        double maxDistance = Double.MAX_VALUE;
        for (EntityLivingBase weirdo : list) {
            double newDistance = weirdo.getDistanceSqToEntity(this.gem);
            if (newDistance <= maxDistance) {
                maxDistance = newDistance;
                if (!this.gem.isOwner(weirdo)) {
                	if (weirdo instanceof EntityGem) {
                		EntityGem possibleRebel = (EntityGem) weirdo;
                		if (this.gem.isOwner(possibleRebel.getOwner()) && possibleRebel.isTraitor() && !this.lastMessage.equals(possibleRebel.getUniqueID())) {
                			this.sendMessage("rebel", possibleRebel.getName());
                			this.lastMessage = possibleRebel.getUniqueID().toString();
                		}
                	}
                	else if (weirdo instanceof EntityMob) {
                		EntityMob possibleAttacker = (EntityMob) weirdo;
                		if (possibleAttacker.getAttackTarget() != null && possibleAttacker.getAttackTarget().equals(this.gem.getOwner()) && !this.lastMessage.equals(possibleAttacker.getUniqueID())) {
                			this.sendMessage("attacker", possibleAttacker.getName());
                			this.lastMessage = possibleAttacker.getUniqueID().toString();
                		}
                	}
                	else if (weirdo instanceof EntityPlayer) {
                		EntityPlayer possibleAttacker = (EntityPlayer) weirdo;
                		if ((possibleAttacker.getHeldItemMainhand().getItem() instanceof ItemSword || possibleAttacker.getHeldItemMainhand().getItem() instanceof ItemBow) && !this.lastMessage.equals(possibleAttacker.getUniqueID())) {
                			this.sendMessage("attacker", possibleAttacker.getName());
                			this.lastMessage = possibleAttacker.getUniqueID().toString();
                		}
                	}
                }
            }
        }
	}
	private void sendMessage(String line, String formatting) {
		this.gem.getOwner().sendMessage(new TextComponentString("<" + this.gem.getName() + "> " + new TextComponentTranslation("command.kagic.sapphire_" + line, formatting).getUnformattedComponentText()));
		//this.gem.getOwner().addStat(ModAchievements.YOUR_CLARITY);
		this.lastPrediction = this.gem.world.getTotalWorldTime();
		this.lastMessage = formatting;
	}
}
