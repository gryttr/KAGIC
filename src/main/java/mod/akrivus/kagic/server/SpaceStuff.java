package mod.akrivus.kagic.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class SpaceStuff {
	private final String FILE_PATH;
	private boolean yellowDiamondSpawned = false;
	private boolean blueDiamondSpawned = false;
	private long lastRubyImpactTime = 0;
	private long lastRobonoidImpactTime = 0;

	public SpaceStuff(World world) throws IOException {
		this.FILE_PATH = new File(world.getMinecraftServer().getDataDirectory().getAbsolutePath().replaceAll("\\\\\\.$", "") + (world.getMinecraftServer().isSinglePlayer() ? "\\saves\\" : "\\") + world.getMinecraftServer().getFolderName() + "\\space_stuff.dat").getAbsolutePath();
		this.load();
	}
	public void load() {
		try {
			File file = new File(this.FILE_PATH);
			if (!file.exists()) {
				file.createNewFile();
				this.save();
			}
			else {
				FileInputStream stream = new FileInputStream(file);
				NBTTagCompound nbt = CompressedStreamTools.readCompressed(stream);
				this.readFromNBT(nbt);
				stream.close();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void save() {
		try {
			File file = new File(this.FILE_PATH);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream stream = new FileOutputStream(file);
			NBTTagCompound nbt = this.writeToNBT(new NBTTagCompound());
			CompressedStreamTools.writeCompressed(nbt, stream);
			stream.flush();
			stream.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("yellowDiamondSpawned")) {
			this.yellowDiamondSpawned = nbt.getBoolean("yellowDiamondSpawned");
		}
		if (nbt.hasKey("blueDiamondSpawned")) {
			this.blueDiamondSpawned = nbt.getBoolean("blueDiamondSpawned");
		}
		if (nbt.hasKey("lastRubyImpactTime")) {
			this.lastRubyImpactTime = nbt.getLong("lastRubyImpactTime");
		}
		if (nbt.hasKey("lastRobonoidImpactTime")) {
			this.lastRobonoidImpactTime = nbt.getLong("lastRobonoidImpactTime");
		}
	}
	// Writes file contents to NBT.
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("yellowDiamondSpawned", this.yellowDiamondSpawned);
		compound.setBoolean("blueDiamondSpawned", this.blueDiamondSpawned);
		compound.setLong("lastRubyImpactTime", this.lastRubyImpactTime);
		compound.setLong("lastRobonoidImpactTime", this.lastRobonoidImpactTime);
		return compound;
	}
	// Gets if yellow diamond spawned.
	public boolean hasYellowSpawned() {
		return this.yellowDiamondSpawned;
	}
	// Sets if yellow diamond spawned.
	public void yellowDiamondSpawned(boolean spawned) {
		this.yellowDiamondSpawned = spawned;
	}
	// Gets if blue diamond spawned.
	public boolean hasBlueSpawned() {
		return this.blueDiamondSpawned;
	}
	// Sets if blue diamond spawned.
	public void blueDiamondSpawned(boolean spawned) {
		this.blueDiamondSpawned = spawned;
	}
	// Gets last impact time for Rubies.
	public long getLastRubyImpactTime() {
		return this.lastRubyImpactTime;
	}
	// Sets last impact time for Rubies.
	public void setLastRubyImpactTime(long lastImpactTime) {
		this.lastRubyImpactTime = lastImpactTime;
	}
	// Gets last impact time for Rubies.
	public long getLastRobonoidImpactTime() {
		return this.lastRobonoidImpactTime;
	}
	// Sets last impact time for Rubies.
	public void setLastRobonoidImpactTime(long lastImpactTime) {
		this.lastRobonoidImpactTime = lastImpactTime;
	}
	// Static method for obtaining current instance.
	public static SpaceStuff get() {
		return KAGIC.spaceStuff;
	}
}
