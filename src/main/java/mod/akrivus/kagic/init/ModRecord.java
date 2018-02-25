package mod.akrivus.kagic.init;

import net.minecraft.item.ItemRecord;
import net.minecraft.util.SoundEvent;

public class ModRecord extends ItemRecord {
	public ModRecord(String name, SoundEvent music) {
		this(name, music, true);
	}
	public ModRecord(String name, SoundEvent music, boolean prefix) {
		super(name, music);
		this.setUnlocalizedName(prefix ? "record_" + name : name);
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_MUSIC);
	}
}
