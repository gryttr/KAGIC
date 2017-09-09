package mod.heimrarnadalr.kagic.networking;

import io.netty.buffer.ByteBuf;
import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.tileentity.TileEntityWarpPadCore;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TENameMessage implements IMessage{
	private String name;
	private int x;
	private int y;
	private int z;
	
	public TENameMessage() {}
	
	public TENameMessage(String name, int x, int y, int z) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.name);
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.name = ByteBufUtils.readUTF8String(buf);
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
	}
	
	public static class TENameMessageHandler implements IMessageHandler<TENameMessage, IMessage> {
		@Override
		public IMessage onMessage(TENameMessage message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(TENameMessage message, MessageContext ctx) {
			KAGIC.instance.chatInfoMessage("Handling name");
			EntityPlayerMP playerEntity = ctx.getServerHandler().player;
			World world = playerEntity.getEntityWorld();
			TileEntityWarpPadCore te = (TileEntityWarpPadCore) world.getTileEntity(new BlockPos(message.x, message.y, message.z));
			if (te != null) {
				te.setName(message.name);
			} else {
				KAGIC.instance.chatInfoMessage("TE was null!");				
			}
		}
	}
}
