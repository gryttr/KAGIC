package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;

import mod.akrivus.kagic.entity.gem.EntityEnderPearl;
import mod.akrivus.kagic.skills.Speak;
import mod.akrivus.kagic.tileentity.TileEntityWarpPadCore;
import mod.heimrarnadalr.kagic.worlddata.WarpPadDataEntry;
import mod.heimrarnadalr.kagic.worlddata.WorldDataWarpPad;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class EnderPearlWarp extends Speak<EntityEnderPearl> {
	public EnderPearlWarp() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] { 
			"warp",
			"go"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>();
		this.can(RunWith.LOOKING);
		this.priority(Priority.LOW);
		this.task(false);
	}
	@Override
	public boolean proceed(EntityEnderPearl gem) {
		TileEntityWarpPadCore pad = TileEntityWarpPadCore.getEntityPad(gem);
		if (pad != null && pad.isValidPad() && !pad.isWarping()) {
			return true;
		}
		return false;
	}
	@Override
	public void init(EntityEnderPearl gem) {
		Map<BlockPos, WarpPadDataEntry> padData = WorldDataWarpPad.get(gem.world).getWarpPadData();
		SortedMap<Double, BlockPos> sortedPoses = WorldDataWarpPad.getSortedPositions(padData, gem.getPosition());
		Iterator<BlockPos> it = sortedPoses.values().iterator();
		while (it.hasNext()) {
			BlockPos pos = (BlockPos) it.next();
			WarpPadDataEntry data = padData.get(pos);
			if (this.entireMessage.toLowerCase().contains(data.name.toLowerCase())) {
				TileEntityWarpPadCore dest = (TileEntityWarpPadCore) gem.getEntityWorld().getTileEntity(pos);
				if (!dest.isValid()) {
					gem.feedback(this.commandingPlayer, new TextComponentTranslation("notify.kagic.destnotvalid").getUnformattedComponentText());
					return;
				}
				if (!dest.isClear()) {
					gem.feedback(this.commandingPlayer, new TextComponentTranslation("notify.kagic.destnotclear").getUnformattedComponentText());
					return;
				}
				gem.feedback(this.commandingPlayer, new TextComponentTranslation("notify.kagic.warping", data.name).getUnformattedComponentText());
				gem.playObeySound();
				this.commandingPlayer.setPositionAndUpdate(pos.getX(), pos.getY() + 1, pos.getZ());
				gem.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
				gem.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
				return;
			}
		}
		gem.feedback(this.commandingPlayer, new TextComponentTranslation("notify.kagic.nopad", "this").getUnformattedComponentText());
	}
	@Override
	public String toString() {
		return "warping away";
	}
}
