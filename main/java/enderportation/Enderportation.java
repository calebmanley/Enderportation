package enderportation;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import enderportation.blocks.BlockEnderTotem;
import enderportation.blocks.TileEnderTotem;
import enderportation.items.ItemEnderToken;
import enderportation.network.PacketHandler;
import enderportation.proxy.CommonProxy;

@Mod(modid = Enderportation.MOD_ID, name = Enderportation.NAME, version = Enderportation.VERSION)
public class Enderportation {

	public static final String MOD_ID = "ep";
	public static final String NAME = "Enderportation";
	public static final String VERSION = "1.0";
	
	@Instance(MOD_ID)
	public static Enderportation instance;
	
	@SidedProxy(clientSide = "enderportation.proxy.ClientProxy", serverSide = "enderportation.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static Block enderTotem;
	public static Item enderToken;
	
	public int IDEnderTotem;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		PacketHandler.init();
		proxy.initRenders();
		
		enderTotem = new BlockEnderTotem().setBlockName("enderTotem").setCreativeTab(CreativeTabs.tabTransport);
		GameRegistry.registerBlock(enderTotem, "enderTotem");
		GameRegistry.registerTileEntity(TileEnderTotem.class, "tile.enderTotem");
		
		enderToken = new ItemEnderToken().setUnlocalizedName("enderToken").setCreativeTab(CreativeTabs.tabTransport);
		GameRegistry.registerItem(enderToken, "enderToken");
		
		for (int i = 0; i < 16; i++) {
			CraftingManager.getInstance().addShapelessRecipe(new ItemStack(enderToken, 1, i), new Object[] {new ItemStack(Items.dye, 1, i), new ItemStack(Item.getItemFromBlock(Blocks.stone))});
		}
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new EEventHandler());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		
	}
}
