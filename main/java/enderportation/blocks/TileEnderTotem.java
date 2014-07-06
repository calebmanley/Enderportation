package enderportation.blocks;

import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import enderportation.network.MessageTileEnderTotem;
import enderportation.network.PacketHandler;

public class TileEnderTotem extends TileEntity {

	private String ownerUUID = null;
	private int colourIndex = 16;

	@Override
	public Packet getDescriptionPacket() {
		/*NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);*/
		
		return PacketHandler.INSTANCE.getPacketFrom(new MessageTileEnderTotem(this));
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		//readFromNBT(packet.func_148857_g());
	}

	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		
		this.setOwnerUUID(tagCompound.getString("OwnerUUID"));
		this.setColour(tagCompound.getInteger("Colour"));
	}

	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		tagCompound.setString("OwnerUUID", this.getOwnerUUID());
		tagCompound.setInteger("Colour", this.getColour());
	}

	public boolean isOwned() {
		return this.getOwnerUUID() != null || this.getOwnerUUID() != "";
	}

	public void setOwnerUUID(String uuid) {
		this.ownerUUID = uuid;
	}

	public String getOwnerUUID() {
		return ownerUUID;
	}

	public EntityLivingBase getOwner() {
		if (this.getOwnerUUID() == null || this.getOwnerUUID() == "") {
			return null;
		} else {
			try {
				UUID uuid = UUID.fromString(this.getOwnerUUID());
				return uuid == null ? null : this.worldObj.func_152378_a(uuid);
			} catch (IllegalArgumentException illegalargumentexception) {
				return null;
			}
		}
	}

	public boolean isOwner(EntityLivingBase test) {
		return this.getOwner() == test;
	}
	
	public int getColour() {
		return this.colourIndex;
	}
	
	public void setColour(int index) {
		this.colourIndex = index;
	}
}
