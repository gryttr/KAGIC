package mod.heimrarnadalr.kagic.networking;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class KTPacketHandler {
	private static int packetID = 0;
	public static SimpleNetworkWrapper INSTANCE = null;
	
	public static int nextID() {
		return packetID++;
	}
	
	public static void registerMessages(String channelName) {
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
		registerMessages();
	}
	
	public static void registerMessages() {
		INSTANCE.registerMessage(TENameMessage.TENameMessageHandler.class, TENameMessage.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PadDataRequestMessage.PadDataRequestMessageHandler.class, PadDataRequestMessage.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PadDataMessage.PadDataMessageHandler.class, PadDataMessage.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(WarpSignalMessage.WarpSignalMessageHandler.class, WarpSignalMessage.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(EntityTeleportMessage.EntityTeleportMessageHandler.class, EntityTeleportMessage.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(GalaxySignalMessage.GalaxySignalMessageHandler.class, GalaxySignalMessage.class, nextID(), Side.SERVER);
	}
}
