package enderportation;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EEventHandler {

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer && PlayerTotems.get((EntityPlayer) event.entity) == null)
			PlayerTotems.register((EntityPlayer) event.entity);

		if (event.entity instanceof EntityPlayer && event.entity.getExtendedProperties(PlayerTotems.EXT_PROP_NAME) == null)
			event.entity.registerExtendedProperties(PlayerTotems.EXT_PROP_NAME, new PlayerTotems((EntityPlayer) event.entity));
	}
}
