package mod.akrivus.kagic.command;

import mod.akrivus.kagic.init.ModEntities;
import mod.akrivus.kagic.util.injector.InjectorResult;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentString;

public class CommandScanGems extends CommandBase {
	public String getName() {
		return "scangems";
	}
	public String getUsage(ICommandSender sender) {
		return "/scangems";
	}
	
	public int getRequiredPermissionLevel() {
		return 2;
	}
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		ChunkPos c = server.getEntityWorld().getChunkFromBlockCoords(sender.getPosition()).getPos();
		int chunkPos = (c.x + c.z) % ModEntities.MINERALS.size();
		InjectorResult result = InjectorResult.create(server.getEntityWorld(), sender.getPosition(), false);
		sender.sendMessage(new TextComponentString("Gem species: " + result.getName()));
		sender.sendMessage(new TextComponentString("Mineral value: " + chunkPos + " (" + ModEntities.MINERALS.get(Math.abs(chunkPos)).getSimpleName() + ")"));
		sender.sendMessage(new TextComponentString("Defectivity: " + Math.round(result.getDefectivity() * 100) + "%"));
		sender.sendMessage(new TextComponentString("Primary?: " + result.isPrimary()));
		sender.sendMessage(new TextComponentString("Slags?: " + result.canCreateSlags()));
	}
}
