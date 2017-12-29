package mod.heimrarnadalr.kagic.worlddata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

public class WorldDataRuins extends WorldSavedData {
	private static final String DATA_NAME = KAGIC.MODID + "_ruins";
	private final Map<ChunkLocation, String> ruins = new HashMap<ChunkLocation, String>();
	private final Map<String, ArrayList<BlockPos>> locations = new HashMap<String, ArrayList<BlockPos>>();

	public WorldDataRuins() {
		super(DATA_NAME);
	}
	
	public WorldDataRuins(String identifier) {
		super(identifier);
	}

	public static WorldDataRuins get(World world) {
		if (!world.isRemote) {
			MapStorage storage = world.getPerWorldStorage();
			WorldDataRuins instance = (WorldDataRuins) storage.getOrLoadData(WorldDataRuins.class, DATA_NAME);
			if (instance == null) {
				//KAGIC.instance.chatInfoMessage("Data on server was null");
				instance = new WorldDataRuins();
				storage.setData(DATA_NAME, instance);
			}
			return instance;
		} else {
			KAGIC.instance.chatInfoMessage("Tried to get world data from client");
			return null;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound comp) {
		NBTTagList ruinsList = comp.getTagList("ruins", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < ruinsList.tagCount(); ++i) {
			NBTTagCompound tc = ruinsList.getCompoundTagAt(i);
			ChunkLocation chunk = new ChunkLocation(tc.getInteger("x"), tc.getInteger("z"));
			String type = tc.getString("type");
			this.ruins.put(chunk, type);
		}
		
		NBTTagCompound locationsListList = (NBTTagCompound) comp.getTag("locations");
		for (String type : locationsListList.getKeySet()) {
			NBTTagList locationsList = locationsListList.getTagList(type, Constants.NBT.TAG_COMPOUND);
			
			ArrayList<BlockPos> locs = new ArrayList<BlockPos>();
			for (int i = 0; i < locationsList.tagCount(); ++i) {
				NBTTagCompound tc = locationsList.getCompoundTagAt(i);
				BlockPos location = new BlockPos(tc.getInteger("x"), tc.getInteger("y"), tc.getInteger("z"));
				locs.add(location);
			}
			this.locations.put(type, locs);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound comp) {
		NBTTagList ruinsList = new NBTTagList();
		Iterator<Entry<ChunkLocation, String>> it = this.ruins.entrySet().iterator();
		while (it.hasNext()) {
			NBTTagCompound tc = new NBTTagCompound();
			Entry<ChunkLocation, String> pair = it.next();
			tc.setInteger("x", ((ChunkLocation) pair.getKey()).getX());
			tc.setInteger("z", ((ChunkLocation) pair.getKey()).getZ());
			tc.setString("type", (String) (pair.getValue()));
			ruinsList.appendTag(tc);
		}
		comp.setTag("ruins", ruinsList);
		
		NBTTagCompound locationsListList = new NBTTagCompound();
		for (String type : this.locations.keySet()) {
			NBTTagList locationsList = new NBTTagList();
			for (BlockPos location : this.locations.get(type)) {
				NBTTagCompound tc = new NBTTagCompound();
				tc.setInteger("x", location.getX());
				tc.setInteger("y", location.getY());
				tc.setInteger("z", location.getZ());
				locationsList.appendTag(tc);
			}
			locationsListList.setTag(type, locationsList);
		}
		comp.setTag("locations", locationsListList);
		
		return comp;
	}

	public boolean chunkHasRuin(ChunkLocation chunk) {
		return this.ruins.containsKey(chunk);
	}
	
	public boolean chunkHasSpecificRuin(ChunkLocation chunk, String type) {
		if (this.ruins.containsKey(chunk)) {
			return this.ruins.get(chunk).equals(type);
		} else {
			return false;
		}
	}
	
	public void setChunk(ChunkLocation chunk, String type) {
		if (this.chunkHasRuin(chunk)) {
			KAGIC.instance.chatInfoMessage("WARNING: double-setting chunk " + chunk.toString());
		}
		this.ruins.put(chunk, type);
		this.markDirty();
	}
	
	public void setLocation(String type, BlockPos location) {
		if (this.locations.containsKey(type)) {
			this.locations.get(type).add(location);
		} else {
			ArrayList<BlockPos> locs = new ArrayList<BlockPos>();
			locs.add(location);
			this.locations.put(type, locs);
		}
		this.markDirty();
	}
	
	public boolean checkDistances(String type, BlockPos location, double minDistanceSq) {
		if (this.locations.containsKey(type)) {
			for (BlockPos ruinPos : this.locations.get(type)) {
				if (location.distanceSq(ruinPos) < minDistanceSq) {
					return false;
				}
			}
		}
		return true;
	}
}
