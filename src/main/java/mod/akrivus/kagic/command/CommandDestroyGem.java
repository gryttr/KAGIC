package mod.akrivus.kagic.command;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.util.ShatterDamage;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.server.MinecraftServer;

public class CommandDestroyGem extends CommandBase {
	public String getName() {
		return "degem";
	}
	public String getUsage(ICommandSender sender) {
		return "/degem [filter] [radius]";
	}
	
	public int getRequiredPermissionLevel() {
		return 2;
	}
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (sender.getCommandSenderEntity() instanceof EntityLivingBase) {
			int radius = 11;
			String filter = "all";
			if (args.length > 1) {
				radius = Integer.parseInt(args[1]);
			}
			if (args.length > 0) {
				filter = args[0];
			}
			List<EntityGem> list = sender.getCommandSenderEntity().world.<EntityGem>getEntitiesWithinAABB(EntityGem.class, sender.getCommandSenderEntity().getEntityBoundingBox().grow(radius, radius, radius));
			for (EntityGem gem : list) {
				boolean destroy = false;
				if (filter == "all") {
					destroy = true;
				}
				else if (filter == "mine") {
					destroy = gem.isOwnedBy((EntityLivingBase)(sender.getCommandSenderEntity()));
				}
				else if (filter == "other") {
					destroy = !gem.isOwnedBy((EntityLivingBase)(sender.getCommandSenderEntity()));
				}
				else if (filter == "tamed") {
					destroy = gem.isTamed();
				}
				else if (filter == "wild") {
					destroy = !gem.isTamed();
				}
				else if (filter == "rebel") {
					destroy = gem.isTraitor() || gem.getServitude() == EntityGem.SERVE_REBELLION;
				}
				else if (filter == "diamond") {
					destroy = gem.getServitude() > EntityGem.SERVE_HUMAN;
				}
				for (String name : gem.getNames(new ArrayList<String>())) {
					if (name != null) {
						destroy = Pattern.compile("\\b" + name.toLowerCase() + "\\b").matcher(filter.toLowerCase()).find() || destroy;
					}
				}
				if (destroy) {
					EntityLightningBolt lightningBolt = new EntityLightningBolt(sender.getEntityWorld(), gem.posX, gem.posY, gem.posZ, true);
            		sender.getEntityWorld().addWeatherEffect(lightningBolt);
	            	gem.attackEntityFrom(new ShatterDamage(), gem.getHealth());
				}
			}
		}
	}
}
