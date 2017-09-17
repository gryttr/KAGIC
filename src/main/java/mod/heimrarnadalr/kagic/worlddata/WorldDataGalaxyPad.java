package mod.heimrarnadalr.kagic.worlddata;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

public class WorldDataGalaxyPad extends WorldSavedData {
	private static final String DATA_NAME = KAGIC.MODID + "_galaxypads";
	private final Map<GalaxyPadLocation, WarpPadDataEntry> galaxyPadData = new LinkedHashMap<GalaxyPadLocation, WarpPadDataEntry>();
	
	public WorldDataGalaxyPad() {
		super(DATA_NAME);
	}
	
	public WorldDataGalaxyPad(String identifier) {
		super(identifier);
	}
	
	public static WorldDataGalaxyPad get(World world) {
		if (!world.isRemote) {
			MapStorage storage = world.getMapStorage();
			WorldDataGalaxyPad instance = (WorldDataGalaxyPad) storage.getOrLoadData(WorldDataGalaxyPad.class, DATA_NAME);
			if (instance == null) {
				//KAGICTech.instance.chatInfoMessage("Data on server was null");
				instance = new WorldDataGalaxyPad();
				storage.setData(DATA_NAME, instance);
			}
			return instance;
		} else {
			KAGIC.instance.chatInfoMessage("Tried to get world data from client");
			return null;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagList list = new NBTTagList();
		Iterator<Entry<GalaxyPadLocation, WarpPadDataEntry>> it = this.galaxyPadData.entrySet().iterator();
		while (it.hasNext()) {
			NBTTagCompound tc = new NBTTagCompound();
			Entry<GalaxyPadLocation, WarpPadDataEntry> pair = it.next();
			tc.setString("name", ((WarpPadDataEntry)(pair.getValue())).name);
			tc.setBoolean("valid", ((WarpPadDataEntry)(pair.getValue())).valid);
			tc.setBoolean("clear", ((WarpPadDataEntry)(pair.getValue())).clear);
			tc.setInteger("dim", ((GalaxyPadLocation) pair.getKey()).getDimension());
			tc.setInteger("x", ((GalaxyPadLocation) pair.getKey()).getPos().getX());
			tc.setInteger("y", ((GalaxyPadLocation) pair.getKey()).getPos().getY());
			tc.setInteger("z", ((GalaxyPadLocation) pair.getKey()).getPos().getZ());
			list.appendTag(tc);
		}
		compound.setTag("pads", list);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		NBTTagList list = compound.getTagList("pads", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < list.tagCount(); ++i) {
			NBTTagCompound tc = list.getCompoundTagAt(i);
			int dim = tc.getInteger("dim");
			BlockPos pos = new BlockPos(tc.getInteger("x"), tc.getInteger("y"), tc.getInteger("z"));
			String name = tc.getString("name");
			boolean valid = tc.getBoolean("valid");
			boolean clear = tc.getBoolean("clear");
			this.galaxyPadData.put(new GalaxyPadLocation(dim, pos), new WarpPadDataEntry(name, valid, clear));
		}
	}
	
	public void addGalaxyPadEntry(String name, boolean valid, boolean clear, GalaxyPadLocation gLoc) {
		//KAGIC.instance.chatInfoMessage("Adding entry");
		this.galaxyPadData.put(gLoc, new WarpPadDataEntry(name, valid, clear));
		this.markDirty();
	}
	
	public void removeGalaxyPadEntry(GalaxyPadLocation gpLoc) {
		if (this.galaxyPadData.containsKey(gpLoc)) {
			this.galaxyPadData.remove(gpLoc);
			this.markDirty();
		}
	}
	
	public String getNameFromPos(GalaxyPadLocation gpLoc) {
		if (this.galaxyPadData.containsKey(gpLoc)) {
			return this.galaxyPadData.get(gpLoc).name;			
		} else {
			KAGIC.instance.chatInfoMessage("Tried getting name of nonexistent GalaxyPadLocation");
			return null;
		}
	}
	
	public Map<GalaxyPadLocation, WarpPadDataEntry> getGalaxyPadData() {
		return this.galaxyPadData;
	}
	
	public static SortedMap<Double, GalaxyPadLocation> getSortedPositions(Map<GalaxyPadLocation, WarpPadDataEntry> data, BlockPos pos) {
		SortedMap<Double, GalaxyPadLocation> sortedPoses = new TreeMap<Double, GalaxyPadLocation>();
		Set<Entry<GalaxyPadLocation, WarpPadDataEntry>> entrySet = data.entrySet();
		Iterator<Entry<GalaxyPadLocation, WarpPadDataEntry>> it = entrySet.iterator();
		while (it.hasNext()) {
			GalaxyPadLocation gLoc = ((GalaxyPadLocation)it.next().getKey());
			BlockPos otherPos = gLoc.getPos();
			if (otherPos.equals(pos)) {
				continue;
			}
			double dist = pos.distanceSq(otherPos.getX(), otherPos.getY(), otherPos.getZ());
			sortedPoses.put(dist, gLoc);
		}
		return sortedPoses;
	}
}
