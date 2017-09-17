package mod.heimrarnadalr.kagic.networking;

import io.netty.buffer.ByteBuf;
import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.tileentity.TileEntityGalaxyPadCore;
import mod.akrivus.kagic.tileentity.TileEntityWarpPadCore;
import mod.heimrarnadalr.kagic.worlddata.GalaxyPadLocation;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class GalaxySignalMessage implements IMessage {
	private BlockPos sourcePad;
	private GalaxyPadLocation destinationPad;
	
	public GalaxySignalMessage(BlockPos source, GalaxyPadLocation dest) {
		this.sourcePad = source;
		this.destinationPad = dest;
	}
	
	public GalaxySignalMessage() {}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.sourcePad = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		this.destinationPad = new GalaxyPadLocation(buf.readInt(), new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.sourcePad.getX());
		buf.writeInt(this.sourcePad.getY());
		buf.writeInt(this.sourcePad.getZ());
		buf.writeInt(this.destinationPad.getDimension());
		buf.writeInt(this.destinationPad.getPos().getX());
		buf.writeInt(this.destinationPad.getPos().getY());
		buf.writeInt(this.destinationPad.getPos().getZ());
	}

	public static class GalaxySignalMessageHandler implements IMessageHandler<GalaxySignalMessage, IMessage> {
		@Override
		public IMessage onMessage(GalaxySignalMessage message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(GalaxySignalMessage message, MessageContext ctx) {
			EntityPlayerMP playerEntity = ctx.getServerHandler().player;
			World world = playerEntity.getEntityWorld();
			TileEntityGalaxyPadCore te = (TileEntityGalaxyPadCore) world.getTileEntity(message.sourcePad);
			if (te != null) {
				te.beginWarp(message.destinationPad);
			} else {
				KAGIC.instance.chatInfoMessage("TE was null!");				
			}
		}
	}
}
