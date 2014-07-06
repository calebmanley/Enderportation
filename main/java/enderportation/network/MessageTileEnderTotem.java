package enderportation.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import enderportation.blocks.TileEnderTotem;

public class MessageTileEnderTotem implements IMessage, IMessageHandler<MessageTileEnderTotem, IMessage> {
	
    public int x, y, z;
    public String ownerUUID;
    public int colourIndex;

    public MessageTileEnderTotem() {}

    public MessageTileEnderTotem(TileEnderTotem totem) {
        this.x = totem.xCoord;
        this.y = totem.yCoord;
        this.z = totem.zCoord;
        this.ownerUUID = totem.getOwnerUUID();
        this.colourIndex = totem.getColour();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        int uuidLength = buf.readInt();
        this.ownerUUID = new String(buf.readBytes(uuidLength).array());
        this.colourIndex = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(ownerUUID.length());
        buf.writeBytes(ownerUUID.getBytes());
        buf.writeInt(colourIndex);
    }

    @Override
    public IMessage onMessage(MessageTileEnderTotem message, MessageContext ctx) {
        TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);

        if (tileEntity instanceof TileEnderTotem) {
            ((TileEnderTotem) tileEntity).setColour(message.colourIndex);
            ((TileEnderTotem) tileEntity).setOwnerUUID(message.ownerUUID);
        }

        return null;
    }
}
