package mod.akrivus.kagic.entity.gem;

public enum GemCuts {
	BISMUTH(0),
	FACETED(1),
	PERIDOT(2),
	PILLOW(3),
	TINY(4),
	CABOCHON(5),
	SQUARE(6),
	DRUM(7),
	TEARDROP(8),
	TRIANGULAR(9);
	public int id;
	private GemCuts(int id) {
		this.id = id;
	}
}
