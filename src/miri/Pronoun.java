package miri;

public class Pronoun {

	@SuppressWarnings("unused")
	private static String[] first = new String[] {"I", "me", "my", "myself"};
	@SuppressWarnings("unused")
	private static String[] second = new String[] {"you", "you", "your", "yourself"};
	private static String[] male = new String[] {"he", "him", "his", "himself"};
	private static String[] female = new String[] {"she", "her", "her", "herself"};
	@SuppressWarnings("unused")
	private static String[] neutral = new String[] {"ze", "zer", "zer", "zerself"};
	@SuppressWarnings("unused")
	private static String[] object = new String[] {"it", "it", "its", "itself"};
	@SuppressWarnings("unused")
	private static String[] firstPlural = new String[] {"we", "us", "our", "ourselves"};
	@SuppressWarnings("unused")
	private static String[] secondPlural = new String[] {"you guys", "you guys", "your guys'", "your selves"};
	@SuppressWarnings("unused")
	private static String[] plural = new String[] {"they", "them", "their", "themself"};
	
	
	public static String getPronoun(int index, Word[] sentence) {
		if (sentence[index] instanceof ProperNoun) {
					// turns all repeated proper nouns after the first to pronouns
			if (Sentence.findAll(sentence, sentence[index].get()).length > 1 && !Q.isSubject(sentence, index)) {
				if (((ProperNoun) sentence[index]).isGirl())
					return female[3];
				return male[3];
			}
		}
		return sentence[index].get();
	}
}
