package mod.akrivus.kagic.entity.pepo;

import mod.akrivus.kagic.entity.EntityPepo;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityPumpkin extends EntityPepo {
	protected static final DataParameter<Boolean> IS_LIT = EntityDataManager.<Boolean>createKey(EntityPumpkin.class, DataSerializers.BOOLEAN);
	public EntityPumpkin(World worldIn) {
		super(new ItemStack(Blocks.PUMPKIN), worldIn);
        this.dataManager.register(IS_LIT, false);
	}
	public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("isLit", this.isLit());
    }
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setLit(compound.getBoolean("isLit"));
    }
    public void onDeath(DamageSource cause) {
    	if (this.isLit()) {
    		this.dropItem = new ItemStack(Blocks.LIT_PUMPKIN);
    	}
    	super.onDeath(cause);
    }
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (this.isLit()) {
			this.setLit(false);
			this.dropItem(Item.getItemFromBlock(Blocks.TORCH), 1);
		}
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.TORCH)) {
			this.setLit(true);
			if (!player.capabilities.isCreativeMode) {
				stack.shrink(1);
			}
		}
		return true;
    }
	public void setLit(boolean isLit) {
		this.dataManager.set(IS_LIT, isLit);
	}
	public boolean isLit() {
		return KAGIC.isHalloween() || this.dataManager.get(IS_LIT);
	}
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return this.isLit() ? 15728880 : super.getBrightnessForRender();
	}
    public float getBrightness() {
        return this.isLit() ? 1.0F : super.getBrightness();
    }
}
