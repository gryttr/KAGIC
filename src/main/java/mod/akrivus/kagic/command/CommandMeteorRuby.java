package mod.akrivus.kagic.command;

import mod.akrivus.kagic.entity.gem.EntityRuby;
import mod.akrivus.kagic.init.ModSounds;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandMeteorRuby extends CommandBase {
	public String getName() {
		return "meteorruby";
	}
	public String getUsage(ICommandSender sender) {
		return "/meteorruby";
	}
	public int getRequiredPermissionLevel() {
		return 2;
	}
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityRuby ruby = new EntityRuby(sender.getEntityWorld());
		ruby.setPosition(sender.getPosition().getX(), 256, sender.getPosition().getZ());
		ruby.isSpaceBorn = true;
		sender.getEntityWorld().spawnEntity(ruby);
		ruby.onInitialSpawn(ruby.world.getDifficultyForLocation(new BlockPos(ruby)), (IEntityLivingData)null);
		sender.getEntityWorld().playSound(null, new BlockPos(sender.getPosition().getX(), 256, sender.getPosition().getZ()), ModSounds.RUBY_EXPLODE, SoundCategory.WEATHER, 1.0F, 1.0F);
		sender.sendMessage(new TextComponentTranslation("command.kagic.meteor_ruby", sender.getPosition().getX(), 256, sender.getPosition().getZ()));
	}
}
