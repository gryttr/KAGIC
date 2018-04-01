package mod.akrivus.kagic.linguistics;

import mod.akrivus.kagic.init.KAGIC;

public class LinguisticsHelper {
	public static String[] getSentences(String message) {
		return KAGIC.sentDetector.sentDetect(message.replaceAll("[^A-Za-z0-9 ]", ""));
	}
	public static String[] getTokens(String message) {
		String[] tokens = message.toLowerCase().replaceAll("[^A-Za-z0-9 ]", "").split(" ");
		for (int i = 0; i < tokens.length; ++i) {
			tokens[i].replaceAll("[^A-Za-z0-9]", "");
		}
		return tokens;
	}
	public static String[] getParts(String message) {
		return KAGIC.posTagger.tag(getTokens(message));
	}
	public static String[] parseSentence(String message) {
		String[] tokens = getTokens(message);
		String[] parts = getParts(message);
		String[] pieces = new String[tokens.length + parts.length];
		int piece = 0;
		int i = 0;
		while (piece < pieces.length) {
			pieces[piece] = tokens[i];
			++piece;
			pieces[piece] = parts[i];
			++piece;
			++i;
		}
		return pieces;
	}
	public static String[][] parseSentences(String message) {
		String[] sentences = getSentences(message);
		String[][] pieces = new String[sentences.length][];
		for (int i = 0; i < sentences.length; ++i) {
			pieces[i] = parseSentence(sentences[i]);
		}
		return pieces;
	}
	public static int getDistance(String a, String b, boolean purify) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        if (purify) {
        	a.replaceAll("[^A-Za-z0-9 ]", "");
        	b.replaceAll("[^A-Za-z0-9 ]", "");
        }
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j < costs.length; j++) {
            costs[j] = j;
        }
        for (int i = 1; i <= a.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }
	public static int getDistance(String a, String b) {
		return LinguisticsHelper.getDistance(a, b, false);
	}
}
