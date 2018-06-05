package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mod.akrivus.kagic.entity.EntityCrystalSkills;
import mod.akrivus.kagic.entity.gem.EntityPearl;
import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.skills.Speak;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class SingSong extends Speak<EntityPearl> {
	private String song = "543578743857348";
	private int lastIndex = 0;
	private long lastNote = 0;
	private int noteSleep = 0;
	private int iteration = 0;
	public SingSong() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] {
			"sing",
			"play"
		}));
		this.canBeStopped = true;
		this.killsOnEnd = true;
		this.can(RunWith.EVERYTHING);
		this.task(true);
	}
	@Override
	public boolean proceed(EntityPearl gem) {
		return this.iteration < 4;
	}
	@Override
	public void init(EntityPearl gem) {
		boolean previous = this.isAllowedToRun;
		if (previous) {
			if (!this.collectedNumbers.isEmpty()) {
				try {
					this.song = this.collectedNumbers.get(0);
				}
				catch (Exception ex) {
					this.song = gem.soulSong;
				}
			}
		}
	}
	@Override
	public void run(EntityPearl gem) {
		try {
			if (gem.world.getWorldTime() - this.lastNote > this.noteSleep) {
				if (this.lastIndex < this.song.length()) {
					int tone = Integer.parseInt(Character.toString(this.song.charAt(this.lastIndex)));
					this.noteSleep = gem.playNote(tone);
				}
				else {
					this.lastIndex = -1;
					++this.iteration;
				}
				this.lastNote = gem.world.getWorldTime();
				++this.lastIndex;
			}
		}
		catch (Exception e) {
			this.isAllowedToRun = false;
		}
	}
	@Override
	public void reset(EntityPearl gem) {
		this.lastIndex = 0;
		this.lastNote = 0;
		this.noteSleep = 0;
		this.iteration = 0;
	}
	@Override
	public String toString() {
		return "singing";
	}
}
