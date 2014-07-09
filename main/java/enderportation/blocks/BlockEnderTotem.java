package enderportation.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import enderportation.Enderportation;
import enderportation.PlayerTotems;
import enderportation.items.ItemEnderToken;

public class BlockEnderTotem extends Block implements ITileEntityProvider {

	@SideOnly(Side.CLIENT)
	private IIcon side;
	@SideOnly(Side.CLIENT)
	private IIcon top;
	@SideOnly(Side.CLIENT)
	public static IIcon sideOverlay;
	@SideOnly(Side.CLIENT)
	public static IIcon topOverlay;

	public BlockEnderTotem() {
		super(Material.rock);
		this.setHardness(0.3F);
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TileEnderTotem totem = (TileEnderTotem) world.getTileEntity(x, y, z);

		if (entity != null && totem != null) {
			totem.setOwnerUUID("");
			world.markBlockForUpdate(x, y, z);
		}
	}

	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
		TileEnderTotem totem = (TileEnderTotem) world.getTileEntity(x, y, z);

		if (player != null && totem != null) {
			if (totem.isOwner(player)) {
				PlayerTotems props = PlayerTotems.get(player);
				props.removeTotem(x, y, z);
				
				return world.setBlockToAir(x, y, z);
			} else if (totem.getColour() == 16) {
				return world.setBlockToAir(x, y, z);
			}
		}
		
		return false;
	}

	public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z) {
		TileEnderTotem totem = (TileEnderTotem) world.getTileEntity(x, y, z);

		if (player != null && totem != null) {
			if (totem.isOwner(player) || totem.getColour() == 16) {
				return ForgeHooks.blockStrength(this, player, world, x, y, z);
			}
		}
		
		return 0.0F;
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEnderTotem totem = (TileEnderTotem) world.getTileEntity(x, y, z);

		if (player != null && totem != null) {
			if (player.isSneaking()) {
				if (world.isRemote) {
					if (totem.getColour() < 16) player.addChatComponentMessage(new ChatComponentText("Colour: " + ItemEnderToken.colours[totem.getColour()]));
					else player.addChatComponentMessage(new ChatComponentText("Unclaimed."));
				}
			} else {
				if (totem.isOwner(player) || totem.getColour() == 16) {
					if (player.getHeldItem() != null) {
						ItemStack held = player.getHeldItem();
						PlayerTotems props = PlayerTotems.get(player);

						if (held.getItem() == Items.dye) {
							if (props.canUseColour(held.getItemDamage())) {
								props.removeTotem(x, y, z);

								props.createTotem(held.getItemDamage(), x, y, z);
								totem.setColour(held.getItemDamage());
								totem.setOwnerUUID(player.getUniqueID().toString());

								world.markBlockForUpdate(x, y, z);
								totem.markDirty();
								held.stackSize--;
							}
						}
					}
				}
			}
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEnderTotem();
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		return i == 1 ? this.top : (i == 0 ? this.blockIcon : side);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon("obsidian");

		this.top = iconRegister.registerIcon("ep:totemTop");
		this.side = iconRegister.registerIcon("ep:totemSide");
		this.topOverlay = iconRegister.registerIcon("ep:totemTopOverlay");
		this.sideOverlay = iconRegister.registerIcon("ep:totemSideOverlay");
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public int getRenderType() {
		return Enderportation.instance.IDEnderTotem;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		TileEnderTotem totem = (TileEnderTotem) world.getTileEntity(x, y, z);

		if (totem.getColour() < 16) {
			for (int l = 0; l < 3; ++l) {
				double d6 = (double)((float)x + rand.nextFloat());
				double d1 = (double)((float)y + rand.nextFloat());
				d6 = (double)((float)z + rand.nextFloat());
				double d3 = 0.0D;
				double d4 = 0.0D;
				double d5 = 0.0D;
				int i1 = rand.nextInt(2) * 2 - 1;
				int j1 = rand.nextInt(2) * 2 - 1;
				d3 = ((double)rand.nextFloat() - 0.5D) * 0.125D;
				d4 = ((double)rand.nextFloat() - 0.5D) * 0.125D;
				d5 = ((double)rand.nextFloat() - 0.5D) * 0.125D;
				double d2 = (double)z + 0.5D + 0.25D * (double)j1;
				d5 = (double)(rand.nextFloat() * 1.0F * (float)j1);
				double d0 = (double)x + 0.5D + 0.25D * (double)i1;
				d3 = (double)(rand.nextFloat() * 1.0F * (float)i1);
				world.spawnParticle("portal", d0, d1, d2, d3, d4, d5);
			}
		}
	}
}
