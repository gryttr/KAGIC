package mod.akrivus.kagic.entity.gem;

public enum GemCuts {
	UNKNOWN(-1),
	BISMUTH(0),
	FACETED(1),
	PERIDOT(2),
	PILLOW(3),
	TINY(4),
	CABOCHON(5),
	SQUARE(6),
	DRUM(7),
	TEARDROP(8),
	TRIANGULAR(9),
	DIAMOND(10),
	SHARD(11);
	public int id;
	
	private static GemCuts[] vals = GemCuts.values();
	
	private GemCuts(int id) {
		this.id = id;
	}
	
	public static GemCuts getCut(int i) {
		return vals[i + 1];
	}
}
