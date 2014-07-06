package enderportation.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import enderportation.Enderportation;

public class PacketHandler {
	
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Enderportation.MOD_ID);

    public static void init() {
        INSTANCE.registerMessage(MessageTileEnderTotem.class, MessageTileEnderTotem.class, 0, Side.CLIENT);
    }
}