package mod.akrivus.kagic.entity.gem;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAICommandGems;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAIMineOres;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityQuartzSoldier extends EntityGem {

	public EntityQuartzSoldier(World world) {
		super(world);
		this.setSize(0.9F, 2.3F);
		this.isSoldier = true;

		// Apply entity AI.
		this.stayAI = new EntityAIStay(this);
		this.tasks.addTask(4, new EntityAIFollowDiamond(this, 1.0D));
        this.tasks.addTask(4, new EntityAICommandGems(this, 0.6D));
        this.tasks.addTask(5, new EntityAIMineOres(this, 8));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityMob.class, 16.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));

		// Apply targetting.
		this.targetTasks.addTask(1, new EntityAIDiamondHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIDiamondHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));

		// Apply entity attributes.
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(150.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
	}

	/*********************************************************
	 * Methods related to entity interaction.                *
	 *********************************************************/    
    @Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (!this.world.isRemote) {
			if (hand == EnumHand.MAIN_HAND) {
				if (this.isTamed() && this.isOwnedBy(player)) {
					ItemStack stack = player.getHeldItemMainhand();
					Item item = stack.getItem();
					if (item instanceof ItemArmor) {
						this.playEquipSound(stack);
						this.setItemStackToSlot(((ItemArmor)item).armorType, stack.copy());
						if (!player.isCreative()) {
							stack.shrink(1);
						}
						this.playObeySound();
						return true;
					}
					else if (stack.getItem() == Items.BUCKET) {
						this.entityDropItem(this.getHeldItemMainhand(), 0.0F);
						this.setHeldItem(EnumHand.MAIN_HAND, stack);
						return true;
					}
					else if (player.isSneaking() && this.getHeldItemMainhand().getItem() instanceof ItemBucket) {
						this.entityDropItem(this.getHeldItemMainhand(), 0.0F);
						this.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
						return true;
					}
					else if (player.isSneaking() && stack.isEmpty()) {
						this.entityDropItem(this.getItemStackFromSlot(EntityEquipmentSlot.HEAD), 2.3F);
						this.entityDropItem(this.getItemStackFromSlot(EntityEquipmentSlot.CHEST), 1.7F);
						this.entityDropItem(this.getItemStackFromSlot(EntityEquipmentSlot.LEGS), 1.0F);
						this.entityDropItem(this.getItemStackFromSlot(EntityEquipmentSlot.FEET), 0.0F);
						this.setItemStackToSlot(EntityEquipmentSlot.HEAD, stack.copy());
						this.setItemStackToSlot(EntityEquipmentSlot.CHEST, stack.copy());
						this.setItemStackToSlot(EntityEquipmentSlot.LEGS, stack.copy());
						this.setItemStackToSlot(EntityEquipmentSlot.FEET, stack.copy());
						this.playObeySound();
						return true;
					}
				}
			}
		}
		return super.processInteract(player, hand);
    }
    
	@Override
	public void whenPrimary() {
		super.whenPrimary();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
		IAttributeInstance attackDamage = this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		attackDamage.setBaseValue(attackDamage.getAttributeValue() * 2);
		this.setSize(0.9F, 2.53F);
	}
}
