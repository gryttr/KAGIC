package mod.heimrarnadalr.kagic.worlddata;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

public class WorldDataWarpPad extends WorldSavedData {
	private static final String DATA_NAME = KAGIC.MODID + "_warppads";
	private final Map<BlockPos, WarpPadDataEntry> warpPadData = new LinkedHashMap<BlockPos, WarpPadDataEntry>();
	
	public WorldDataWarpPad() {
		super(DATA_NAME);
	}
	
	public WorldDataWarpPad(String identifier) {
		super(identifier);
	}
	
	public static WorldDataWarpPad get(World world) {
		if (!world.isRemote) {
			MapStorage storage = world.getPerWorldStorage();
			WorldDataWarpPad instance = (WorldDataWarpPad) storage.getOrLoadData(WorldDataWarpPad.class, DATA_NAME);
			if (instance == null) {
				//KAGICTech.instance.chatInfoMessage("Data on server was null");
				instance = new WorldDataWarpPad();
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
		Iterator<Entry<BlockPos, WarpPadDataEntry>> it = this.warpPadData.entrySet().iterator();
		while (it.hasNext()) {
			NBTTagCompound tc = new NBTTagCompound();
			Entry<BlockPos, WarpPadDataEntry> pair = it.next();
			tc.setString("name", ((WarpPadDataEntry)(pair.getValue())).name);
			tc.setBoolean("valid", ((WarpPadDataEntry)(pair.getValue())).valid);
			tc.setBoolean("clear", ((WarpPadDataEntry)(pair.getValue())).clear);
			tc.setInteger("x", ((BlockPos) pair.getKey()).getX());
			tc.setInteger("y", ((BlockPos) pair.getKey()).getY());
			tc.setInteger("z", ((BlockPos) pair.getKey()).getZ());
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
			BlockPos pos = new BlockPos(tc.getInteger("x"), tc.getInteger("y"), tc.getInteger("z"));
			String name = tc.getString("name");
			boolean valid = tc.getBoolean("valid");
			boolean clear = tc.getBoolean("clear");
			this.warpPadData.put(pos, new WarpPadDataEntry(name, valid, clear));
		}
	}
	
	public void addWarpPadEntry(String name, boolean valid, boolean clear, BlockPos pos) {
		//KAGIC.instance.chatInfoMessage("Adding entry");
		this.warpPadData.put(pos, new WarpPadDataEntry(name, valid, clear));
		this.markDirty();
	}
	
	public void removeWarpPadEntry(BlockPos pos) {
		if (this.warpPadData.containsKey(pos)) {
			this.warpPadData.remove(pos);
			this.markDirty();
		}
	}
	
	public String getNameFromPos(BlockPos pos) {
		if (this.warpPadData.containsKey(pos)) {
			return this.warpPadData.get(pos).name;			
		} else {
			KAGIC.instance.chatInfoMessage("Tried getting name of nonexistent BlockPos");
			return null;
		}
	}
	
	public Map<BlockPos, WarpPadDataEntry> getWarpPadData() {
		return this.warpPadData;
	}
	
	public static SortedMap<Double, BlockPos> getSortedPositions(Map<BlockPos, WarpPadDataEntry> data, BlockPos pos) {
		SortedMap<Double, BlockPos> sortedPoses = new TreeMap<Double, BlockPos>();
		Set<Entry<BlockPos, WarpPadDataEntry>> entrySet = data.entrySet();
		Iterator<Entry<BlockPos, WarpPadDataEntry>> it = entrySet.iterator();
		while (it.hasNext()) {
			BlockPos otherPos = ((BlockPos)it.next().getKey());
			if (otherPos.equals(pos)) {
				continue;
			}
			double dist = pos.distanceSq(otherPos.getX(), otherPos.getY(), otherPos.getZ());
			sortedPoses.put(dist, otherPos);
		}
		return sortedPoses;
	}
}