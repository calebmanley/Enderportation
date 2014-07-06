package enderportation;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

import org.apache.commons.lang3.ArrayUtils;

public class PlayerTotems implements IExtendedEntityProperties {

	public final static String EXT_PROP_NAME = "ExtendedPlayer";
	private final EntityPlayer player;

	private int x[] = new int[16];
	private int y[] = new int[16];
	private int z[] = new int[16];

	public PlayerTotems(EntityPlayer player) {
		this.player = player;
		
		for (int i = 0; i < 16; i++) {
			y[i] = -1;
		}
	}

	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(PlayerTotems.EXT_PROP_NAME, new PlayerTotems(player));
	}

	public static final PlayerTotems get(EntityPlayer player) {
		return (PlayerTotems) player.getExtendedProperties(EXT_PROP_NAME);
	}

	@Override
	public void saveNBTData(NBTTagCompound tagCompound) {
		NBTTagCompound nbt = new NBTTagCompound();

		nbt.setIntArray("X", this.x);
		nbt.setIntArray("Y", this.y);
		nbt.setIntArray("Z", this.z);

		tagCompound.setTag(EXT_PROP_NAME, nbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound tagCompound) {
		NBTTagCompound properties = (NBTTagCompound) tagCompound.getTag(EXT_PROP_NAME);

		this.x = properties.getIntArray("X");
		this.y = properties.getIntArray("Y");
		this.z = properties.getIntArray("Z");

		for (int i = 0; i < 16; i++) {
			System.out.println("Totem " + i + " at X:" + x[i] + ", Y:" + y[i] + " Z:" + z[i] + ". Colour: " + i);
		}
	}

	@Override
	public void init(Entity entity, World world) {

	}

	public void createTotem(int colour, int x, int y, int z) {
		this.x[colour] = x;
		this.y[colour] = y;
		this.z[colour] = z;
	}

	public void removeTotem(int x, int y, int z) {
		for (int i = 0; i < 16; i++) {
			if (this.x[i] == x) {
				if (this.y[i] == y) {
					if (this.z[i] == z) {
						this.x[i] = 0;
						this.y[i] = -1;
						this.z[i] = 0;
					}
				}
			}
		}
	}

	public boolean canUseColour(int colour) {
		System.out.println(this.x[colour] + " " + this.y[colour] + " " + this.z[colour]);
		
		if (this.x[colour] == 0 && this.y[colour] == -1 && this.z[colour] == 0) {
			return true;
		}

		return false;
	}

	public Vec3 getTotemLocation(int colour) {
		return Vec3.createVectorHelper(this.x[colour], this.y[colour], this.z[colour]);
	}
}
