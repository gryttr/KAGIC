package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;

import mod.akrivus.kagic.entity.gem.EntityPeridot;
import mod.akrivus.kagic.skills.Speak;

public class TellReport extends Speak<EntityPeridot> {
	public TellReport() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] { 
			"tell",
			"give"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] { 
			"report",
			"analysis"
		}));
		this.can(RunWith.EVERYTHING);
		this.priority(Priority.LOW);
		this.task(false);
	}
	@Override
	public boolean proceed(EntityPeridot gem) {
		return true;
	}
	@Override
	public void init(EntityPeridot gem) {
		gem.checkSurroundings(gem.world, gem.getPosition());
	}
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
