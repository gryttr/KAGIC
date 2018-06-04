package mod.akrivus.kagic.entity.shardfusion;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.akrivus.kagic.entity.ai.EntityAIStandGuard;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.init.ModItems;
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
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityShardFusion extends EntityGem {

	public EntityShardFusion(World world) {
		super(world);
		this.isImmuneToFire = true;

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
				return input != null && !(input instanceof EntityShardFusion);
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
						player.sendMessage(new TextComponentTranslation("command.kagic.does_not_understand", this.getName()));
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
	public String generateSpecificName(BlockPos pos) {
		return "Fusion";
	}
}
