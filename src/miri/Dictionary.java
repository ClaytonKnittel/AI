package miri;

public class Dictionary {

	private static Pair[] list = new Pair[] {new Pair("hello", 1), new Pair("fuck", -10),
			new Pair("bitch", -8), new Pair("cunt", -11), new Pair("whore", -9), new Pair("ass", -3),
			new Pair("damn", -1), new Pair("dick", -10), new Pair("bastard", -4), new Pair("love", 9),
			new Pair("cool", 1), new Pair("dumb", -2), new Pair("wrong", -1), new Pair("right", 2),
			new Pair("test", -1), new Pair("friend", 3), new Pair("pal", 2), new Pair("acquaintance", 1),
			new Pair("like", 4), new Pair("beautiful", 7), new Pair("lovely", 6), new Pair("hilarious", 3),
			new Pair("stfu", -7), new Pair("lol", 1), new Pair("suck", -3), new Pair("rock", 1),
			new Pair("miri", 3), new Pair("igneous", 13), new Pair("electromagnetism", 111), new Pair("no", -4),
			new Pair("hate", -3), new Pair("dont", -1), new Pair("yes", 4), new Pair("bed", 2), new Pair("dayquil", -30),
			new Pair("nyquil", -300), new Pair("shuo", 10), new Pair("melissa", -5), new Pair("smart", 3),
			new Pair("god", -1)};
	
	private static Word[] words = new Word[] {

			new ProperNoun("Becky", true), new ProperNoun("Shuo", true), new ProperNoun("Clayton", false),
			new ProperNoun("Stephanie", true), new ProperNoun("Melissa", true), new ProperNoun("Martin", false),
			new ProperNoun("Alex", true, false), new ProperNoun("Mark S. Wrighton", false),
			
			//concrete nouns
			new Noun("table"), new Noun("book"), new Noun("computer"), new Noun("phone"),
			new Noun("paper"), new Noun("desk"), new Noun("door"), new Noun("backpack"),
			new Noun("sheet"), new Noun("blanket"), new Noun("jacket"), new Noun("noun"),
			new Noun("homework"), new Noun("strap"), new Noun("lamp"), new Noun("screen"),
			new Noun("laugh"), new Noun("line"), new Noun("tissue"), new Noun("bottle"),
			new Noun("test"), new Noun("foot", "feet"), new Noun("leg"), new Noun("neck"),
			new Noun("knee"), new Noun("ankle", true), new Noun("eye", true), new Noun("belly"),
			new Noun("handle"), new Noun("textbook"), new Noun("finger"), new Noun("light"),
			new Noun("radiation"), new Noun("pants", "pants"), new Noun("shoe"), new Noun("bowl"),
			new Noun("bed"), new Noun("way"), new Noun("world"), new Noun("map"),
			new Noun("meat"), new Noun("person", "people"), new Noun("food"), new Noun("bird"),
			new Noun("television"), new Noun("library"), new Noun("oven", true), new Noun("player"), 
			new Noun("exam", true), new Noun("movie"), new Noun("equipment", true), new Noun("boyfriend"),
			new Noun("camera"), new Noun("university"), new Noun("child", "children"), new Noun("article", true), 
			new Noun("user"), new Noun("medicine"), new Noun("teacher"), new Noun("disk"),
			
			//abstract nouns: describe groups of concrete nouns or things that cannot be touched but can be pluralized
			new AbstractNoun("family"), new AbstractNoun("system"), new AbstractNoun("year"), new AbstractNoun("reading"),
			new AbstractNoun("method"), new AbstractNoun("theory"), new AbstractNoun("law"), new AbstractNoun("problem"),
			new AbstractNoun("power"), new AbstractNoun("ability", true), new AbstractNoun("industry", true), new AbstractNoun("thing"), 
			new AbstractNoun("fact"), new AbstractNoun("product"), new AbstractNoun("idea", true), new AbstractNoun("investment", true),
			new AbstractNoun("area", true), new AbstractNoun("society"), new AbstractNoun("activity", true), new AbstractNoun("story"),
			new AbstractNoun("community"), new AbstractNoun("definition"), new AbstractNoun("development"), new AbstractNoun("language"),
			new AbstractNoun("variety"), new AbstractNoun("video"), new AbstractNoun("week"), new AbstractNoun("country"),
			new AbstractNoun("organization", true), new AbstractNoun("policy"), new AbstractNoun("series"), new AbstractNoun("thought"),
			new AbstractNoun("basis", "bases"), new AbstractNoun("direction"), new AbstractNoun("strategy"), new AbstractNoun("technology"),
			new AbstractNoun("army", true), new AbstractNoun("environment", true), new AbstractNoun("instance", true), new AbstractNoun("month"),
			new AbstractNoun("truth"), new AbstractNoun("department"), new AbstractNoun("difference"), new AbstractNoun("goal"),
			new AbstractNoun("audience", true), new AbstractNoun("source"), new AbstractNoun("marriage"), new AbstractNoun("combination"),
			new AbstractNoun("failure"), new AbstractNoun("success", "successes"), new AbstractNoun("meaning"), new AbstractNoun("philosophy"),
			new AbstractNoun("communication"), new AbstractNoun("night"), new AbstractNoun("disease"), new AbstractNoun("tongue"),
			new AbstractNoun("government"), new AbstractNoun("history"),
			
			//ideas: nouns that are just ideas and can have no quantity, do not work with a, an or the
			new Noun("economics", true), new Noun("love"), new Noun("internet", true), new Noun("history"),
			new Noun("mathematics"), new Noun("art", true), new Noun("information", true), new Noun("skill"),
			new Noun("health"), new Noun("music"), new Noun("data"), new Noun("understanding", true),
			new Noun("literature"), new Noun("software"), new Noun("control"), new Noun("knowledge"),
			new Noun("science"), new Noun("nature"), new Noun("temperature"), new Noun("media"),
			new Noun("safety"), new Noun("quality"), new Noun("management"), new Noun("security"),
			new Noun("organization", true), new Noun("physics"), new Noun("analysis", true), new Noun("policy"), 
			new Noun("freedom"), new Noun("marketing"), new Noun("writing"), new Noun("news"),
			new Noun("fishing"), new Noun("growth"), new Noun("income", true), new Noun("chemistry"),
			
			
			new Verb("kill"), new Verb("jump"), new Verb("cut"), new Verb("read"),
			new Verb("walk"), new Verb("hold"), new Verb("drop"), new Verb("rip"),
			new Verb("record"), new Verb("open", true), new Verb("close"), new Verb("run"),
			new Verb("yell"), new Verb("talk"), new Verb("stand"), new Verb("live"),
			new Verb("eat"), new Verb("drink"), new Verb("fuck"), new Verb("look"),
			new Verb("sniff"), new Verb("breathe"), new Verb("shape"), new Verb("swipe"),
			
			new Word("quick", P.ADJECTIVE), new Word("disastrous", P.ADJECTIVE), new Word("pretty", P.ADJECTIVE),
			new Word("funny", P.ADJECTIVE), new Word("silly", P.ADJECTIVE), new Word("fun", P.ADJECTIVE),
			new Word("amiable", P.ADJECTIVE, true), new Word("quiet", P.ADJECTIVE), new Word("intolerable", P.ADJECTIVE, true),
			new Word("restless", P.ADJECTIVE), new Word("ugly", P.ADJECTIVE, true), new Word("cute", P.ADJECTIVE),
			new Word("hairless", P.ADJECTIVE), new Word("random", P.ADJECTIVE), new Word("heavy", P.ADJECTIVE),
			new Word("light", P.ADJECTIVE), new Word("colorful", P.ADJECTIVE), new Word("smelly", P.ADJECTIVE),
			new Word("skinny", P.ADJECTIVE), new Word("fat", P.ADJECTIVE), new Word("turnt", P.ADJECTIVE),
			new Word("dark", P.ADJECTIVE), new Word("tolerable", P.ADJECTIVE), new Word("enjoyable", P.ADJECTIVE, true),
			new Word("sick", P.ADJECTIVE), new Word("demented", P.ADJECTIVE), new Word("sadistic", P.ADJECTIVE),
			new Word("dumb", P.ADJECTIVE), new Word("smart", P.ADJECTIVE), new Word("tall", P.ADJECTIVE),
			new Word("short", P.ADJECTIVE), new Word("boyish", P.ADJECTIVE), new Word("girly", P.ADJECTIVE),
			new Word("good", P.ADJECTIVE), new Word("bad", P.ADJECTIVE),
			
			new Word("a", P.ARTICLE), new Word("the", P.ARTICLE),
			
			new Word("and", P.CONJUNCTION), new Word("or", P.CONJUNCTION), new Word("for", P.CONJUNCTION), 
			new Word("nor", P.CONJUNCTION), new Word("but", P.CONJUNCTION), new Word("yet", P.CONJUNCTION), 
			new Word("so", P.CONJUNCTION), 
			
			new HelpingVerb("shall"), new HelpingVerb("may"), new HelpingVerb("will"), 
			new HelpingVerb("can"), new HelpingVerb("must"), new HelpingVerb("need to"), 
			
			new LinkingVerb("be"),
			
			new Word("under", P.PREPOSITION), new Word("above", P.PREPOSITION), new Word("below", P.PREPOSITION), 
			new Word("inside", P.PREPOSITION), new Word("outside", P.PREPOSITION), new Word("in", P.PREPOSITION),
			new Word("out", P.PREPOSITION), new Word("beside", P.PREPOSITION), new Word("behind", P.PREPOSITION),
			new Word("around", P.PREPOSITION), new Word("about", P.PREPOSITION), new Word("atop", P.PREPOSITION),
			new Word("beneath", P.PREPOSITION), new Word("aside", P.PREPOSITION),
			
	};
	
	private static Sentence[] sentenceStructure = new Sentence[] {
			new Sentence(new P[] {P.NOUN, P.VERB}),
			new Sentence(new P[] {P.NOUN, P.VERB, P.ADJECTIVE}),
			new Sentence(new P[] {P.NOUN, P.VERB, P.ARTICLE, P.NOUN}),
			new Sentence(new P[] {P.NOUN, P.VERB, P.PREPOSITION, P.ARTICLE, P.NOUN}),
			new Sentence(new P[] {P.NOUN, P.VERB, P.NOUN}),
			new Sentence(new P[] {P.NOUN, P.VERB, P.NOUN, P.NOUN}),
			new Sentence(new P[] {P.NOUN, P.VERB, P.VERB, P.PREPOSITION, P.NOUN, P.NOUN}),
			new Sentence(new P[] {P.NOUN, P.VERB, P.VERB, P.PREPOSITION, P.ARTICLE, P.NOUN})
			
	};
	
	public static Word get(int index) {
		return words[index];
	}
	
	public static int getValue(String s) {
		for (Pair p : list) {
			if (p.is(s))
				return p.getValue();
		}
		return 0;
	}
	
	public static Word randomWord() {
		return words[(int) (Math.random() * words.length)];
	}
	
	public static Word getRandomWord(P pos) {
		Word ret;
		do {
			ret = randomWord();
		} while (ret.getPOS() != pos);
		return ret;
	}
	
	public static Word getWord(String s) {
		for (int x = 0; x < words.length; x++) {
			if (words[x].get().equals(s))
				return words[x];
		}
		return null;
	}
	
	public static String getRandomSentence() {
		Sentence sent = sentenceStructure[(int) (sentenceStructure.length * Math.random())];
		return sent.createSentence();
	}
}
