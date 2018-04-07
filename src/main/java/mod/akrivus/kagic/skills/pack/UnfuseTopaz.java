package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;

import mod.akrivus.kagic.entity.gem.EntityTopaz;
import mod.akrivus.kagic.skills.Speak;

public class UnfuseTopaz extends Speak<EntityTopaz> {
	public UnfuseTopaz() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] { 
			"unfuse",
			"remove",
			"split"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>();
		this.can(RunWith.LOOKING);
		this.priority(Priority.LOW);
		this.task(false);
	}
	@Override
	public boolean proceed(EntityTopaz gem) {
		return gem.isFusion();
	}
	@Override
	public void init(EntityTopaz gem) {
		if (gem.isFusion()) {
			gem.unfuse();
		}
		else {
			gem.wantsToFuse = false;
		}
	}
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
