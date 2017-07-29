package mod.akrivus.kagic.init;

import net.minecraft.item.ItemRecord;
import net.minecraft.util.SoundEvent;

public class ModRecord extends ItemRecord {
	public ModRecord(String name, SoundEvent music) {
		super(name, music);
		this.setUnlocalizedName("record_" + name);
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_MUSIC);
	}
}
