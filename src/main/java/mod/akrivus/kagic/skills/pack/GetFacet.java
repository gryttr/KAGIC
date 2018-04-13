package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityPeridot;
import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.skills.Speak;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class GetFacet extends Speak<EntityPeridot> {
	public GetFacet() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] { 
			"get",
			"tell",
			"is"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] { 
			"facet",
			"code",
			"location"
		}));
		this.can(RunWith.EVERYTHING);
		this.priority(Priority.LOW);
		this.task(false);
	}
	@Override
	public boolean proceed(EntityPeridot gem) {
		return true;
	}
	@Override
	public void init(EntityPeridot gem) {
		gem.feedback("We are in Facet" + Integer.toString(Math.abs((int)(gem.posX+gem.posZ)/48),36).toUpperCase() + " at the moment.");
	}
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
