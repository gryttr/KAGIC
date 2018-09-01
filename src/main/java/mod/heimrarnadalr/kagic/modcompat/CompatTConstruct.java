package mod.heimrarnadalr.kagic.modcompat;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;
import slimeknights.tconstruct.library.tools.AoeToolCore;
import slimeknights.tconstruct.library.tools.SwordCore;
import slimeknights.tconstruct.library.tools.ranged.ProjectileCore;
import slimeknights.tconstruct.library.tools.ranged.ProjectileLauncherCore;
import slimeknights.tconstruct.tools.melee.item.BattleSign;
import slimeknights.tconstruct.tools.melee.item.FryPan;
import slimeknights.tconstruct.tools.ranged.item.ShortBow;

public class CompatTConstruct {

	public static boolean isTinkersConstructLoaded() {
		return Loader.isModLoaded("tconstruct");
	}
	
	public static boolean isTinkersMeleeWeapon(Item weapon) {
		if (!isTinkersConstructLoaded()) {
			return false;
		}
		
		return weapon instanceof AoeToolCore || weapon instanceof BattleSign || weapon instanceof FryPan || weapon instanceof SwordCore;
	}
	
	public static boolean isTinkersRangedWeapon(Item weapon) {
		if (!isTinkersConstructLoaded()) {
			return false;
		}
		
		return weapon instanceof ShortBow;
	}
	
	public static boolean isTinkersProjectile(Item weapon) {
		if (!isTinkersConstructLoaded()) {
			return false;
		}
		return false; //For now
		//return weapon instanceof ProjectileCore;
	}
}
