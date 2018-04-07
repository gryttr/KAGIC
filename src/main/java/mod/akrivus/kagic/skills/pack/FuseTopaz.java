package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;

import mod.akrivus.kagic.entity.gem.EntityTopaz;
import mod.akrivus.kagic.skills.Speak;

public class FuseTopaz extends Speak<EntityTopaz> {
	public FuseTopaz() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] { 
			"fuse",
			"combine",
			"join",
			"form"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>();
		this.can(RunWith.LOOKING);
		this.priority(Priority.LOW);
		this.task(false);
	}
	@Override
	public boolean proceed(EntityTopaz gem) {
		return !gem.isFusion();
	}
	@Override
	public void init(EntityTopaz gem) {
		if (!gem.isFusion()) {
			gem.wantsToFuse = true;
		}
	}
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
