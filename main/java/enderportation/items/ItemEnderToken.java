package enderportation.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import enderportation.PlayerTotems;

public class ItemEnderToken extends Item {
	
	public static final String[] colours = new String[] {"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};
	public static final int[] codes = new int[] {1644825, 10040115, 6717235, 6704179, 3361970, 8339378, 5013401, 10066329, 5000268, 15892389, 8375321, 15066419, 6724056, 11685080, 14188339, 16777215};
	
	@SideOnly(Side.CLIENT)
    private IIcon[] icons;
	
	public ItemEnderToken() {
		this.setHasSubtypes(true);
        this.setMaxDamage(0);
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        int j = MathHelper.clamp_int(meta, 0, 15);
        return this.icons[j];
    }
	
    public String getUnlocalizedName(ItemStack stack) {
        int i = MathHelper.clamp_int(stack.getItemDamage(), 0, 15);
        return super.getUnlocalizedName() + "." + this.colours[i];
    }

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }
	
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.block;
    }
    
    public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }
    
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
        stack.stackSize--;
        this.performTeleportation(stack, world, player);
        return stack;
    }
    
    protected void performTeleportation(ItemStack stack, World world, EntityPlayer player) {
    	PlayerTotems props = PlayerTotems.get(player);
    	Vec3 loc = props.getTotemLocation(this.getDamage(stack));
    	player.setPositionAndUpdate(loc.xCoord + 0.5D, loc.yCoord + 1.0D, loc.zCoord + 0.5D);
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < 16; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.icons = new IIcon[colours.length];

        for (int i = 0; i < colours.length; ++i) {
            this.icons[i] = iconRegister.registerIcon(this.getIconString() + "_" + colours[i]);
        }
    }
}
