package mod.akrivus.kagic.entity;

import java.util.ArrayList;

import mod.akrivus.kagic.entity.gem.GemCuts;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityFusion  extends EntityGem {
	private NBTTagList fusionGems = new NBTTagList();
	private ArrayList<GemCuts> fusionGemCuts = new ArrayList<GemCuts>();
	private ArrayList<GemPlacements> fusionGemPlacements = new ArrayList<GemPlacements>();
	private int gemCount = 0;

	public EntityFusion(World worldIn) {
		super(worldIn);
		// TODO Auto-generated constructor stub
	}

	//=========================================================================
	//=== Methods for entity NBTing ===========================================
	//=========================================================================
	
	public void writeEntityToNBT(NBTTagCompound comp) {
		comp.setTag("gems", fusionGems);
		
		comp.setInteger("cutsSize", this.fusionGemCuts.size());
		NBTTagList cuts = new NBTTagList();
		for (int i = 0; i < this.fusionGemCuts.size(); ++i) {
			int cut = this.fusionGemCuts.get(i).id;
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("cut" + i, cut);
			cuts.appendTag(nbt);
		}
		comp.setTag("cuts", cuts);
		
		comp.setInteger("placeSize", this.fusionGemPlacements.size());
		NBTTagList placements = new NBTTagList();
		for (int i = 0; i < this.fusionGemPlacements.size(); ++i) {
			int placement = this.fusionGemPlacements.get(i).id;
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("place" + i, placement);
			placements.appendTag(nbt);
		}
		comp.setTag("places", placements);
	}
	
	
	public void readEntityFromNBT(NBTTagCompound comp) {
		this.fusionGems = (NBTTagList) comp.getTag("gems");
		
		NBTTagList cuts = (NBTTagList) comp.getTag("cuts");
		int cutsSize = comp.getInteger("cutsSize");
		for (int i = 0; i < cutsSize; ++i) {
			this.fusionGemCuts.add(GemCuts.getCut(cuts.getIntAt(i)));
		}

		NBTTagList placements = (NBTTagList) comp.getTag("places");
		int placementsSize = comp.getInteger("placeSize");
		for (int i = 0; i < placementsSize; ++i) {
			this.fusionGemPlacements.add(GemPlacements.getPlacement(placements.getIntAt(i)));
		}
	}
	
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
    	return super.onInitialSpawn(difficulty, livingdata);
	}
    
    //=========================================================================
	//=== Methods for managing fusion==========================================
	//=========================================================================
	
	public void addGem(EntityGem gem) {
		NBTTagCompound comp = new NBTTagCompound();
		gem.writeEntityToNBT(comp);
		
		this.fusionGems.appendTag(comp);
		this.fusionGemCuts.add(gem.getGemCut());
		this.fusionGemPlacements.add(gem.getGemPlacement());
		++this.gemCount;
	}
	
	public void poof() {
		KAGIC.instance.chatInfoMessage("Fusion poofed");
	}
}
