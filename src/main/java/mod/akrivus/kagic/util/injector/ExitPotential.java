package mod.akrivus.kagic.util.injector;

import java.util.Comparator;

public class ExitPotential implements Comparator<ExitPotential> {
	public final boolean canSeeSky;
	public final int lightLevel;
	public final int length;
	public final char direction;
	
	public ExitPotential() {
		this.canSeeSky = false;
		this.lightLevel = 0;
		this.length = 9;
		this.direction = 'o';
	}
	
	public ExitPotential(boolean canSeeSky, int lightLevel, int length, char direction) {
		this.canSeeSky = canSeeSky;
		this.lightLevel = lightLevel;
		this.length = length;
		this.direction = direction;
	}

	@Override
	public int compare(ExitPotential arg0, ExitPotential arg1) {
		if (arg0.canSeeSky && !arg1.canSeeSky) {
			return -1;
		}
		if (arg1.canSeeSky && !arg0.canSeeSky) {
			return 1;
		}
		
		if (arg0.lightLevel > arg1.lightLevel) {
			return -1;
		}
		if (arg1.lightLevel > arg0.lightLevel) {
			return 1;
		}
		
		if (arg0.length < arg1.length) {
			return -1;
		}
		if (arg1.length < arg0.length) {
			return 1;
		}
		
		return 0;
	}
}
