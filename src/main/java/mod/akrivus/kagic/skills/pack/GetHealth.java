package mod.akrivus.kagic.skills.pack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.skills.Speak;
import net.minecraft.world.biome.Biome.TempCategory;

public class GetHealth extends Speak<EntityGem> {
	public GetHealth() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] { 
			"get",
			"tell",
			"is",
			"are"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] { 
			"health",
			"condition",
			"status"
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
		double ratio = gem.getHealth() / gem.getMaxHealth();
		String feedback = "";
		if (ratio <= 0.2) {
			feedback = "I can't make it, ";
		} else
		if (ratio <= 0.4) {
			feedback = "I don't feel good, ";
		} else
		if (ratio <= 0.6) {
			feedback = "I'm fine, I'm okay, ";
		} else
		if (ratio <= 0.8) {
			feedback = "I'm feeling great, ";
		} else
		if (ratio <= 1.0) {
			feedback = "I'm perfect, ";
		}
		feedback += "I'm at " + new DecimalFormat("#.00").format(ratio * 100) + "% health.";
		gem.feedback(feedback);
		this.isAllowedToRun = false;
	}
	@Override
	public String toString() {
		return "feeling myself";
	}
}
