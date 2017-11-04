package mod.akrivus.kagic.entity.gem;

import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityHoloPearl extends EntityGem {
	private EntityPearl creator = null;

	private static final DataParameter<Integer> COLOR = EntityDataManager.<Integer>createKey(EntityHoloPearl.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> DRESS_STYLE = EntityDataManager.<Integer>createKey(EntityHoloPearl.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> NAKED = EntityDataManager.<Boolean>createKey(EntityHoloPearl.class, DataSerializers.BOOLEAN);

	public EntityHoloPearl(World world) {
		this(world, null);
	}
	
	public EntityHoloPearl(World world, EntityPearl creator) {
		super(world);
		this.creator = creator;

		this.dataManager.register(COLOR, this.rand.nextInt(16));
		this.dataManager.register(DRESS_STYLE, 1);
		this.dataManager.register(NAKED, false);
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		IEntityLivingData data = super.onInitialSpawn(difficulty, livingdata);
		this.setPosition(this.creator.posX + this.creator.getLookVec().x, this.creator.posY, this.creator.posZ + this.creator.getLookVec().z);
		this.setGemCut(this.creator.getGemCut().id);
		this.setGemPlacement(this.creator.getGemPlacement().id);
		this.setHairColor(this.creator.getHairColor());
		this.setColor(this.creator.getColor());
		this.setUniformColor(this.creator.getUniformColor());
		this.setInsigniaColor(this.creator.getInsigniaColor());
		this.setHairStyle(this.creator.getHairStyle());
		this.setDressStyle(this.creator.getDressStyle());
		this.setNaked(this.creator.isNaked());
		return data;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound comp) {
		comp.setInteger("color", this.getColor());
		comp.setInteger("dressStyle", this.getDressStyle());
		comp.setBoolean("naked", this.isNaked());
		comp.setUniqueId("creator", this.creator.getUniqueID());
		return super.writeToNBT(comp);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound comp) {
		super.readFromNBT(comp);
		this.setColor(comp.getInteger("color"));
		this.setDressStyle(comp.getInteger("dressStyle"));
		this.setNaked(comp.getBoolean("naked"));
		this.creator = (EntityPearl) ((WorldServer) this.world).getEntityFromUuid(comp.getUniqueId("creator"));
	}

	@Override
	public void setNewCutPlacement() {
		this.setGemCut(this.creator.getGemCut().id);
		this.setGemPlacement(this.creator.getGemPlacement().id);
	}
	
	@Override
	public boolean isCorrectCutPlacement() {
		return true;
	}

	@Override
	public boolean isGemPlacementDefined() {
		return true;
	}

	@Override
	public boolean isGemCutDefined() {
		return true;
	}

	public void setColor(int color) {
		this.dataManager.set(COLOR, color);
	}
	
	public int getColor() {
		return this.dataManager.get(COLOR);
	}
	
	public void setDressStyle(int dress) {
		this.dataManager.set(DRESS_STYLE, dress);
	}
	
	public int getDressStyle() {
		return this.dataManager.get(DRESS_STYLE);
	}

	public void setNaked(boolean naked) {
		this.dataManager.set(NAKED, naked);
	}
	
	public boolean isNaked() {
		return this.dataManager.get(NAKED);
	}
}
