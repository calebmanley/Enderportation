package enderportation.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import enderportation.Enderportation;
import enderportation.blocks.RenderBlockEnderTotem;

public class ClientProxy extends CommonProxy {

	@Override
	public void initRenders() {
		Enderportation.instance.IDEnderTotem = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new RenderBlockEnderTotem());
	}
}
