package mod.akrivus.kagic.entity.gem;

public enum GemPlacements {
	BACK_OF_HEAD(0),
	FOREHEAD(1),
	LEFT_EYE(2),
	RIGHT_EYE(3),
	NOSE(4),
	LEFT_CHEEK(5),
	RIGHT_CHEEK(6),
	MOUTH(7),
	LEFT_SHOULDER(8),
	RIGHT_SHOULDER(9),
	LEFT_HAND(10),
	RIGHT_HAND(11),
	BACK(12),
	CHEST(13),
	BELLY(14),
	LEFT_THIGH(15),
	RIGHT_THIGH(16),
	LEFT_KNEE(17),
	RIGHT_KNEE(18);
	public int id;
	private GemPlacements(int id) {
		this.id = id;
	}
}
