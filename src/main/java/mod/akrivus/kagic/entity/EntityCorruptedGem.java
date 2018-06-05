package mod.akrivus.kagic.entity;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.akrivus.kagic.entity.ai.EntityAIStandGuard;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.entity.shardfusion.EntityShardFusion;
import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModItems;
import mod.heimrarnadalr.kagic.worlddata.ChunkLocation;
import mod.heimrarnadalr.kagic.worlddata.WorldDataRuins;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityCorruptedGem extends EntityGem {

	public EntityCorruptedGem(World world) {
		super(world);

		// Apply entity AI.
		this.stayAI = new EntityAIStay(this);
		this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.414D, 32.0F));
		this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(5, new EntityAIStandGuard(this, 0.6D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityMob.class, 16.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		
		// Apply targeting.
		this.targetTasks.addTask(1, new EntityAIDiamondHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIDiamondHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));
		this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityLivingBase>(this, EntityLivingBase.class, 10, true, false, new Predicate<EntityLivingBase>() {
			public boolean apply(EntityLivingBase input) {
				return input != null 
						&& ((input instanceof EntityGem && !(input instanceof EntityCorruptedGem)) 
								|| (input instanceof EntityPlayer && !((EntityPlayer)input).isCreative()));
			}
		}));
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
		this.setServitude(EntityGem.SERVE_HOSTILE);
		this.setAttackAI();
		return super.onInitialSpawn(difficulty, livingdata);
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (!this.world.isRemote) {
			if (hand == EnumHand.MAIN_HAND) {
				ItemStack stack = player.getHeldItemMainhand();
				if (stack.getItem() == ModItems.GEM_STAFF) {
					if (player.isSneaking()) {
						this.alternateInteract(player);
						this.playObeySound();
					}
					else {
						player.sendMessage(new TextComponentTranslation("command.kagic.does_not_understand_corrupted", this.getName()));
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean spokenTo(EntityPlayer player, String message) {
		return false;
	}
	
	@Override
	public boolean getCanSpawnHere() {
		if (this.rand.nextFloat() > 0.0001) {
			return false;
		}
		
		WorldDataRuins ruins = WorldDataRuins.get(this.world);
		ChunkLocation cPos = new ChunkLocation(this.getPosition());

		boolean adjacentToRuin = false;
		for (int i = -2; i <= 2; ++i) {
			for (int j = -1; j <= 1; ++j) {
				ChunkLocation potential = cPos.add(i, j);
				if (ruins.chunkHasRuin(potential)) {
					adjacentToRuin = true;
					break;
				}
			}
		}
		
		boolean isValidRuinChunk = !ruins.chunkHasRuin(cPos) || ruins.chunkHasSpecificRuin(cPos, "mask_island");
		if (!adjacentToRuin || !isValidRuinChunk) {
			return false;
		}
		
		if (!this.world.getEntities(EntityCorruptedGem.class, EntitySelectors.withinRange(this.posX, this.posY, this.posZ, 32)).isEmpty()) {
			return false;
		}

		return super.getCanSpawnHere() && this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
	}
	
	@Override
	public int getMaxSpawnedInChunk() {
		return 1;
	}
	
	@Override
	public String generateSpecificName(BlockPos pos) {
		return "Corrupted";
	}
}
