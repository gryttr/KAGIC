package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.skills.Speak;

public class GetSkills extends Speak<EntityGem> {
	public GetSkills() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] { 
			"get",
			"tell",
			"is",
			"doing"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] { 
			"skills",
			"tasks"
		}));
		this.can(RunWith.EVERYTHING);
		this.priority(Priority.LOW);
		this.task(false);
	}
	@Override
	public boolean proceed(EntityGem gem) {
		return super.proceed(gem);
	}
	@Override
	public void init(EntityGem gem) {
		String feedback = "I am ";
		if (gem.skills.isEmpty()) {
			feedback += "doing nothing.";
		}
		else {
			for (int i = 0; i < gem.skills.keySet().size(); ++i) {
				if (i > 0) {
					feedback += ", ";
				}
				feedback += gem.skills.get(gem.skills.keySet().toArray()[i]);
			}
		}
		feedback += ".";
		gem.feedback(feedback);
		this.isAllowedToRun = false;
	}
	@Override
	public String toString() {
		return "talking to you";
	}
}
