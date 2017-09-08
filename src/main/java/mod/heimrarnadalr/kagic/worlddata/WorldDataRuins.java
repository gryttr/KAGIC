package mod.heimrarnadalr.kagic.worlddata;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

public class WorldDataRuins extends WorldSavedData {
	private static final String DATA_NAME = KAGIC.MODID + "_ruins";
	private final Map<ChunkLocation, String> ruins = new HashMap<ChunkLocation, String>();

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
		NBTTagList list = comp.getTagList("ruins", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < list.tagCount(); ++i) {
			NBTTagCompound tc = list.getCompoundTagAt(i);
			ChunkLocation chunk = new ChunkLocation(tc.getInteger("x"), tc.getInteger("z"));
			String type = tc.getString("type");
			this.ruins.put(chunk, type);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound comp) {
		NBTTagList list = new NBTTagList();
		Iterator<Entry<ChunkLocation, String>> it = this.ruins.entrySet().iterator();
		while (it.hasNext()) {
			NBTTagCompound tc = new NBTTagCompound();
			Entry<ChunkLocation, String> pair = it.next();
			tc.setInteger("x", ((ChunkLocation) pair.getKey()).getX());
			tc.setInteger("z", ((ChunkLocation) pair.getKey()).getZ());
			tc.setString("type", (String) (pair.getValue()));
			list.appendTag(tc);
		}
		comp.setTag("ruins", list);
		return comp;
	}

	public boolean chunkHasRuin(ChunkLocation chunk) {
		return this.ruins.containsKey(chunk);
	}
	
	public void setChunk(ChunkLocation chunk, String type) {
		if (this.chunkHasRuin(chunk)) {
			KAGIC.instance.chatInfoMessage("WARNING: double-setting chunk " + chunk.toString());
		}
		this.ruins.put(chunk, type);
		this.markDirty();
	}
}
