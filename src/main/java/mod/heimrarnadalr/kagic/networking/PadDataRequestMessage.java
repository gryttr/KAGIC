package mod.heimrarnadalr.kagic.networking;

import io.netty.buffer.ByteBuf;
import mod.heimrarnadalr.kagic.worlddata.WorldDataGalaxyPad;
import mod.heimrarnadalr.kagic.worlddata.WorldDataWarpPad;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PadDataRequestMessage implements IMessage {
	private boolean isGalaxy;
	private int x;
	private int y;
	private int z;
	
	public PadDataRequestMessage() {}
	
	public PadDataRequestMessage(boolean isGalaxy, int x, int y, int z) {
		this.isGalaxy = isGalaxy;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.isGalaxy = buf.readBoolean();
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(this.isGalaxy);
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
	}

	public static class PadDataRequestMessageHandler implements IMessageHandler<PadDataRequestMessage, IMessage> {
		@Override
		public IMessage onMessage(PadDataRequestMessage message, MessageContext ctx) {
			((WorldServer) ctx.getServerHandler().player.world).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(PadDataRequestMessage message, MessageContext ctx) {
			EntityPlayerMP playerEntity = ctx.getServerHandler().player;
			World world = playerEntity.getEntityWorld();
			NBTTagCompound data = new NBTTagCompound();
			if (message.isGalaxy) {
				WorldDataGalaxyPad padData = WorldDataGalaxyPad.get(world);
				data = padData.writeToNBT(data);
				data.setBoolean("galaxy", true);
				KTPacketHandler.INSTANCE.sendTo(new PadDataMessage(data, message.x, message.y, message.z), ctx.getServerHandler().player);
			} else {
				WorldDataWarpPad padData = WorldDataWarpPad.get(world);
				data = padData.writeToNBT(data);
				data.setBoolean("galaxy", false);
				KTPacketHandler.INSTANCE.sendTo(new PadDataMessage(data, message.x, message.y, message.z), ctx.getServerHandler().player);
			}
		}
	}
}
