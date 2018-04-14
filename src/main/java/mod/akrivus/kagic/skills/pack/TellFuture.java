package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntitySapphire;
import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.skills.Speak;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class TellFuture extends Speak<EntitySapphire> {
	public TellFuture() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] { 
			"tell",
			"give"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] { 
			"future",
			"sign",
			"vision"
		}));
		this.can(RunWith.EVERYTHING);
		this.priority(Priority.LOW);
		this.task(false);
	}
	@Override
	public boolean proceed(EntitySapphire gem) {
		return true;
	}
	@Override
	public void init(EntitySapphire gem) {
		if (gem.isDefective()) {
			World world = gem.world;
			if (world.rand.nextInt(100) == 0 && world.rand.nextBoolean()) {
				this.sendMessage(gem, "wallbreaker");
			}
			else if (gem.getOwner().ticksExisted < 100) {
				this.sendMessage(gem, "died");
			}
			else if (gem.getOwner().isBurning()) {
				this.sendMessage(gem, "fire");
			}
			else if (gem.getOwner().isInWater()) {
				this.sendMessage(gem, "water");
			}
			else if (world.isRaining()) {
				if (!world.canSnowAt(gem.getPosition(), false)) {
					this.sendMessage(gem, "rain");
				}
				else {
					this.sendMessage(gem, "snow");
				}
			}
			else if (gem.getOwner().getAttackingEntity() != null) {
				this.sendMessage(gem, "hurt_by", gem.getOwner().getAttackingEntity().getName());
			}
			else if (world.getWorldTime() < 600) {
				this.sendMessage(gem, "sunrise");
			}
			else if (world.getWorldTime() > 600 && world.getWorldTime() < 6000) {
				this.sendMessage(gem, "noon");
			}
			else if (world.getWorldTime() > 6000 && world.getWorldTime() < 14000) {
				this.sendMessage(gem, "sunset");
			}
			else if (world.getWorldTime() > 14000 && world.getWorldTime() < 22000) {
				this.sendMessage(gem, "night");
			}
			else if (world.getWorldTime() > 22000) {
				this.sendMessage(gem, "moonset");
			}
			else {
				this.sendMessage(gem, "none");
			}
		}
		else {
			World world = gem.world;
			boolean sent = false;
			if (world.rand.nextInt(100) == 0 && world.rand.nextBoolean() && KAGIC.isBirthdayTomorrow()) {
				this.sendMessage(gem, "birthday");
				sent = true;
			}
			List<EntityLivingBase> list = gem.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, gem.getEntityBoundingBox().grow(24.0D, 8.0D, 24.0D));
	        double maxDistance = Double.MAX_VALUE;
	        for (EntityLivingBase weirdo : list) {
	            double newDistance = weirdo.getDistanceSq(gem);
	            if (newDistance <= maxDistance) {
	                maxDistance = newDistance;
	                if (!gem.isOwner(weirdo)) {
	                	if (weirdo instanceof EntityGem) {
	                		EntityGem possibleRebel = (EntityGem) weirdo;
	                		if (gem.isOwner(possibleRebel.getOwner()) && possibleRebel.isTraitor()) {
	                			this.sendMessage(gem, "rebel", possibleRebel.getName());
	            				sent = true;
	                		}
	                	}
	                	else if (weirdo instanceof EntityMob) {
	                		EntityMob possibleAttacker = (EntityMob) weirdo;
	                		if (possibleAttacker.getAttackTarget() != null && possibleAttacker.getAttackTarget().equals(gem.getOwner())) {
	                			this.sendMessage(gem, "attacker", possibleAttacker.getName());
	            				sent = true;
	                		}
	                	}
	                	else if (weirdo instanceof EntityPlayer) {
	                		EntityPlayer possibleAttacker = (EntityPlayer) weirdo;
	                		if ((possibleAttacker.getHeldItemMainhand().getItem() instanceof ItemSword || possibleAttacker.getHeldItemMainhand().getItem() instanceof ItemBow)) {
	                			this.sendMessage(gem, "attacker", possibleAttacker.getName());
	            				sent = true;
	                		}
	                	}
	                }
	            }
	        }
	        if (!sent) {
	        	this.sendMessage(gem, "none");
	        }
		}
	}
	private void sendMessage(EntitySapphire sapphire, String line, String formatting) {
		sapphire.feedback(this.commandingPlayer, new TextComponentTranslation("command.kagic." + (sapphire.isDefective() ? "padparadscha" : "sapphire") + "_" + line, formatting).getUnformattedText());
	}
    
	private void sendMessage(EntitySapphire sapphire, String line) {
		sapphire.feedback(this.commandingPlayer, new TextComponentTranslation("command.kagic." + (sapphire.isDefective() ? "padparadscha" : "sapphire") + "_" + line).getUnformattedText());
	}
	@Override
	public String toString() {
		return "telling the future";
	}
}
