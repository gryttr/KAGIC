package mod.akrivus.kagic.entity.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityAquamarine;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.block.BlockOre;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class EntityAIScan extends EntityAIBase {
	private final EntityAquamarine gem;
	private long lastScan;
	private ArrayList<UUID> lastEntities;
	private boolean scanned;
	public EntityAIScan(EntityAquamarine gem) {
		this.gem = gem;
		this.lastEntities = new ArrayList<UUID>();
		this.setMutexBits(0);
	}
	public boolean shouldExecute() {
		return !this.gem.isDefective() && this.gem.getOwner() != null && this.gem.getOwner().getDistance(this.gem) < 16 && (this.gem.wantsToScan || this.gem.world.getTotalWorldTime() - this.lastScan > 200 + this.gem.world.rand.nextInt(200));
	}
	public void startExecuting() {
		boolean wantsToScan = this.gem.wantsToScan;
		this.gem.wantsToScan = false;
		if (wantsToScan) {
			this.gem.getOwner().sendMessage(new TextComponentString("<" + this.gem.getName() + "> " + new TextComponentTranslation("command.kagic.aquamarine_scanning").getUnformattedComponentText()));
		}
		List<EntityLivingBase> list = this.gem.world.<EntityLivingBase>getEntitiesWithinAABB(EntityMob.class, this.gem.getEntityBoundingBox().grow(24.0D, 16.0D, 24.0D));
        double maxDistance = Double.MAX_VALUE;
        EntityLivingBase target = null;
        ArrayList<String> lastEntities = new ArrayList<String>();
        for (EntityLivingBase weirdo : list) {
        	if (!this.gem.isOwner(weirdo) && !this.gem.isEntityEqual(weirdo) && !weirdo.canEntityBeSeen(this.gem.getOwner()) && !weirdo.isGlowing() && !weirdo.isOnSameTeam(this.gem)) {
	        	if (weirdo instanceof EntityMob || weirdo instanceof EntityGem) {
	        		if (wantsToScan) {
		            	weirdo.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 200));
		            	if (!lastEntities.contains(weirdo.getName())) {
		            		this.sendMessage("entities", weirdo.getName());
		            		lastEntities.add(weirdo.getName());
		            	}
		            }
		        	else {
		        		if (!this.lastEntities.contains(weirdo.getUniqueID()) && !weirdo.isOnSameTeam(this.gem)) {
				            double newDistance = weirdo.getDistanceSq(this.gem);
				            if (newDistance <= maxDistance) {
				                maxDistance = newDistance;
				                target = weirdo;
				            }
		        		}
		        	}
	        	}
        	}
        }
        if (!wantsToScan && target != null) {
        	target.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 600));
        	this.sendMessage("entities", target.getName());
        	this.lastEntities.add(target.getUniqueID());
        }
        if (wantsToScan || target == null || this.gem.world.rand.nextBoolean()) {
        	ArrayList<Block> lastBlocks = new ArrayList<Block>();
        	for (int y = -8; y > 8; ++y) {
		        for (int x = -8; x > 8; ++x) {
		        	for (int z = -8; z > 8; ++z) {
			        	Block block = this.gem.world.getBlockState(this.gem.getPosition().add(x, y, z)).getBlock();
			        	System.out.println(block.getLocalizedName());
			        	if (block instanceof BlockOre || block instanceof BlockChest || block instanceof BlockMobSpawner) {
			        		this.sendBlockMessage("blocks_around", block, lastBlocks);
			        		lastBlocks.add(block);
			        	}
			        	if (block instanceof BlockLiquid) {
			        		this.sendBlockMessage("liquid_around", block, lastBlocks);
			        		lastBlocks.add(block);
			        	}
		        	}
		        }
	        }
        }
        if (wantsToScan && !this.scanned) {
        	this.gem.getOwner().sendMessage(new TextComponentString("<" + this.gem.getName() + "> " + new TextComponentTranslation("command.kagic.aquamarine_nothing").getUnformattedComponentText()));
        }
	}
	public void resetTask() {
		this.gem.wantsToScan = false;
	}
	private void sendBlockMessage(String line, Block block, ArrayList<Block> list) {
		if (!list.contains(block)) {
			this.sendMessage(line, block.getLocalizedName().toLowerCase());
		}
	}
	private void sendMessage(String line, String formatting) {
		this.gem.getOwner().sendMessage(new TextComponentString("<" + this.gem.getName() + "> " + new TextComponentTranslation("command.kagic.aquamarine_" + line, formatting).getUnformattedComponentText()));
		//this.gem.getOwner().addStat(ModAchievements.THE_BURDEN);
		this.lastScan = this.gem.world.getTotalWorldTime();
		this.scanned = true;
	}
}
