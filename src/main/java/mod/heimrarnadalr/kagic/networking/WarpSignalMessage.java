package mod.heimrarnadalr.kagic.networking;

import io.netty.buffer.ByteBuf;
import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.tileentity.TileEntityWarpPadCore;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class WarpSignalMessage implements IMessage {
	private BlockPos sourcePad;
	private BlockPos destinationPad;
	
	public WarpSignalMessage(BlockPos source, BlockPos dest) {
		this.sourcePad = source;
		this.destinationPad = dest;
	}
	
	public WarpSignalMessage() {}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.sourcePad = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		this.destinationPad = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.sourcePad.getX());
		buf.writeInt(this.sourcePad.getY());
		buf.writeInt(this.sourcePad.getZ());
		buf.writeInt(this.destinationPad.getX());
		buf.writeInt(this.destinationPad.getY());
		buf.writeInt(this.destinationPad.getZ());
	}

	public static class WarpSignalMessageHandler implements IMessageHandler<WarpSignalMessage, IMessage> {
		@Override
		public IMessage onMessage(WarpSignalMessage message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(WarpSignalMessage message, MessageContext ctx) {
			EntityPlayerMP playerEntity = ctx.getServerHandler().player;
			World world = playerEntity.getEntityWorld();
			TileEntityWarpPadCore te = (TileEntityWarpPadCore) world.getTileEntity(message.sourcePad);
			if (te != null) {
				te.beginWarp(message.destinationPad);
			} else {
				KAGIC.instance.chatInfoMessage("TE was null!");				
			}
		}
	}
}
