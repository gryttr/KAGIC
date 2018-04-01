package mod.akrivus.kagic.skills;

import java.util.ArrayList;
import java.util.Arrays;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.linguistics.LinguisticsHelper;
import net.minecraft.entity.player.EntityPlayer;

public class Speak<T extends EntityGem> extends SkillBase<T> {
	protected ArrayList<String> TRIGGER_VERBS;
	protected ArrayList<String> TRIGGER_NOUNS;
	protected ArrayList<String> collectedVerbs = new ArrayList<String>();
	protected ArrayList<String> collectedSubjects = new ArrayList<String>();
	protected ArrayList<String> collectedObjects = new ArrayList<String>();
	protected ArrayList<String> collectedAdjectives = new ArrayList<String>();
	protected ArrayList<String> collectedNumbers = new ArrayList<String>();
	protected ArrayList<String> selectedPhrase;
	protected String entireMessage;
	protected String selectedVerb;
	protected int selectedVerbIndex;
	protected String selectedNoun;
	protected int selectedNounIndex;
	
	@Override
	public boolean speak(T gem, EntityPlayer player, String message) {
		boolean result = false;
		String[][] sentences = LinguisticsHelper.parseSentences(message);
		boolean verbFound = false;
		boolean nounFound = false;
		int matches = 0;
		for (int sentence = 0; sentence < sentences.length; ++sentence) {
			for (int word = 0; word < sentences[sentence].length; ++word) {
				if (word % 2 == 0) {
					String pos = sentences[sentence][word + 1];
					if (pos != null) {
						if ((pos.startsWith("NN") || pos.startsWith("PR")) && (TRIGGER_NOUNS == null || TRIGGER_NOUNS.contains(sentences[sentence][word]) || TRIGGER_NOUNS.isEmpty())) {	
							this.collectedObjects.add(sentences[sentence][word]);
							this.selectedNoun = sentences[sentence][word];
							this.selectedNounIndex = word / 2;
							nounFound = true;
							++matches;
						}
						else if (pos.startsWith("NN")) {
							this.collectedSubjects.add(sentences[sentence][word]);
						}
						if (pos.startsWith("VB") && (TRIGGER_VERBS == null || TRIGGER_VERBS.contains(sentences[sentence][word]) || TRIGGER_VERBS.isEmpty())) {
							this.collectedVerbs.add(sentences[sentence][word]);
							this.selectedVerb = sentences[sentence][word];
							this.selectedVerbIndex = word / 2;
							verbFound = true;
							++matches;
						}
						if (pos.startsWith("JJ")) {
							this.collectedAdjectives.add(sentences[sentence][word]);
						}
						if (pos.startsWith("CD")) {
							this.collectedNumbers.add(sentences[sentence][word]);
						}
					}
				}
			}
			if (matches >= 2) {
				this.selectedPhrase = new ArrayList<String>(Arrays.asList(sentences[sentence]));
			}
			else {
				matches = 0;
			}
			result = matches >= 2 && nounFound && verbFound;
		}
		this.entireMessage = message;
		this.isAllowedToRun = result;
		return result;
	}
}
