package mod.akrivus.kagic.command;

import java.util.Random;

import mod.akrivus.kagic.blocks.BlockGemSeed;
import mod.akrivus.kagic.util.injector.InjectorResult;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class CommandSpawnGems extends CommandBase {
	public String getName() {
		return "spawngems";
	}
	public String getUsage(ICommandSender sender) {
		return "/spawngems [radius]";
	}
	
	public int getRequiredPermissionLevel() {
		return 2;
	}
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		int radius = 11;
		if (args.length > 0) {
			radius = Integer.parseInt(args[0]);
		}
		sender.sendMessage(new TextComponentTranslation("command.kagic.spawning_all_nearby_gems"));
		for (int x = -radius; x <= radius; ++x) {
			for (int y = -radius; y <= radius; ++y) {
				for (int z = -radius; z <= radius; ++z) {
					BlockPos newPos = sender.getPosition().add(x, y, z);
					IBlockState newBlockState = sender.getEntityWorld().getBlockState(newPos);
					if (newBlockState.getBlock() instanceof BlockGemSeed) {
						this.spawnGem(sender.getEntityWorld(), newPos, newBlockState, sender.getEntityWorld().rand);
					}
				}
			}
		}
		sender.sendMessage(new TextComponentTranslation("command.kagic.spawned_all_nearby_gems"));
	}
	public void spawnGem(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		InjectorResult result = InjectorResult.create(worldIn, pos, true);
		result.generate(worldIn);
	}
}
