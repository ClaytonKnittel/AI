package miri;

public enum Q {

	SUBJECT,
	HELPINGVERB,
	VERB,
	OBJECT;
	
	public static int getVerbPos(Word[] sentence) {
		for (int x = 0; x < sentence.length; x++) {
			if (sentence[x].getPOS().equals(P.VERB) || sentence[x].getPOS().equals(P.HELPINGVERB) || sentence[x].getPOS().equals(P.LINKINGVERB))
				return x;
		}
		return -1;
	}
	
	public static boolean isSubject(Word[] sentence, int index) {
		return index < getVerbPos(sentence);
	}
}
