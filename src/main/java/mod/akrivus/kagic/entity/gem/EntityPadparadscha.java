package mod.akrivus.kagic.entity.gem;

import java.util.HashMap;
import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAIRetroVision;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class EntityPadparadscha extends EntityGem {
	public static final HashMap<Block, Double> PADPARADSCHA_YIELDS = new HashMap<Block, Double>();
	public static final HashMap<Integer, ResourceLocation> PADPARADSCHA_HAIR_STYLES = new HashMap<Integer, ResourceLocation>();
	public int lastLoveTime = 0;
	public int luckTicks = 0;
	public boolean playerLoves = true;
	public EntityPadparadscha(World worldIn) {
		super(worldIn);
		this.setSize(0.7F, 1.6F);
		this.seePastDoors();

		//Define valid gem cuts and placements
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.BACK_OF_HEAD);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.FOREHEAD);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.LEFT_EYE);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.RIGHT_EYE);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.LEFT_CHEEK);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.RIGHT_CHEEK);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.BACK);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.CHEST);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.BELLY);

		this.stayAI = new EntityAIStay(this);
		this.tasks.addTask(1, new EntityAIFollowDiamond(this, 1.0D));
		this.tasks.addTask(2, new EntityAIRetroVision(this));
        this.tasks.addTask(3, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(4, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.droppedGemItem = ModItems.PADPARADSCHA_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_PADPARADSCHA_GEM;
	}

	public float[] getGemColor() {
    	return new float[] { 237F / 255F, 177F / 255F, 166F / 255F };
    }
	public void convertGems(int placement) {
    	this.setGemCut(GemCuts.FACETED.id);
    	switch (placement) {
    	case 0:
    		this.setGemPlacement(GemPlacements.CHEST.id);
    		break;
    	}
    }
	public void onLivingUpdate() {
		if (this.playerLoves) {
			++this.lastLoveTime;
		}
		if (this.luckTicks > 80 && !(this.isDead || this.getHealth() <= 0.0F)) {
			this.futureVision();
			this.luckTicks = 0;
		}
		this.luckTicks += 1;
		super.onLivingUpdate();
	}
	private void futureVision() {
        if (!this.world.isRemote) {
            AxisAlignedBB axisalignedbb = (new AxisAlignedBB(this.posX, this.posY, this.posZ, (this.posX + 1), (this.posY + 1), (this.posZ + 1))).grow(8.0, (double) this.world.getHeight(), 8.0);
            List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
            for (EntityLivingBase entity : list) {
            	if (!entity.isDead || entity.getHealth() > 0.0F) {
	            	if (entity instanceof EntityGem) {
	            		EntityGem gem = (EntityGem) entity;
	            		if (this.getServitude() == gem.getServitude()) {
	            			if (this.getServitude() == EntityGem.SERVE_HUMAN) {
	            				if (this.isOwnerId(gem.getOwnerId())) {
	            					entity.addPotionEffect(new PotionEffect(MobEffects.UNLUCK, 100));
	            				}
	            			}
	            			else {
	            				entity.addPotionEffect(new PotionEffect(MobEffects.UNLUCK, 100));
	            			}
	            		}
	            	}
	            	if (this.isOwner(entity)) {
	            		entity.addPotionEffect(new PotionEffect(MobEffects.UNLUCK, 80));
	            	}
            	}
            }
        }
    }
	public SoundEvent getAmbientSound() {
		return ModSounds.PADPARADSCHA_LIVING;
	}
	public SoundEvent getHurtSound() {
		return ModSounds.SAPPHIRE_HURT;
	}
	public SoundEvent getObeySound() {
		return ModSounds.SAPPHIRE_OBEY;
	}
	public SoundEvent getDeathSound() {
		return ModSounds.SAPPHIRE_DEATH;
	}
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (!this.world.isRemote) {
			if (hand == EnumHand.MAIN_HAND) {
				ItemStack stack = player.getHeldItemMainhand();
				if (this.isTamed()) {
					if (this.isOwner(player)) {
						if (this.isCoreItem(stack)) {
							return super.processInteract(player, hand);
						}
						else {
							if (this.playerLoves && this.lastLoveTime > this.rand.nextInt(1200)) {
								player.sendMessage(new TextComponentString("<" + this.getName() + "> " + new TextComponentTranslation("command.kagic.padparadscha_love").getUnformattedComponentText()));
								//player.addStat(ModAchievements.WHAT_A_MYSTERY);
								this.playLivingSound();
								this.lastLoveTime = 0;
								return true;
							}
							else {
								player.sendMessage(new TextComponentString("<" + this.getName() + "> " + new TextComponentTranslation("command.kagic.padparadscha_interact").getUnformattedComponentText()));
								//player.addStat(ModAchievements.WHAT_A_MYSTERY);
								this.playLivingSound();
								return true;
							}
						}
					}
				}
			}
		}
		return super.processInteract(player, hand);
    }
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (source.getTrueSource() != null && this.isOwner((EntityLivingBase) source.getTrueSource()) && this.playerLoves) {
			this.getOwner().sendMessage(new TextComponentString("<" + this.getName() + "> " + new TextComponentTranslation("command.kagic.padparadscha_hate").getUnformattedComponentText()));
			//this.getOwner().addStat(ModAchievements.WHAT_A_MYSTERY);
			this.playerLoves = false;
			this.lastLoveTime = 0;
		}
		return super.attackEntityFrom(source, amount);
	}
}
