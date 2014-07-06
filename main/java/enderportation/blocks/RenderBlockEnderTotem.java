package enderportation.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import enderportation.Enderportation;
import enderportation.items.ItemEnderToken;

public class RenderBlockEnderTotem implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		TileEnderTotem totem = ((TileEnderTotem) world.getTileEntity(x, y, z));
		int colour = totem.getColour();

		renderer.setRenderBounds(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.125D, 0.9375D);
		renderer.renderStandardBlock(block, x, y, z);
		
		renderer.setRenderBounds(0.8125D, 0.125D, 0.8125D, 0.1875D, 1.0D, 0.1875D);
		renderer.renderStandardBlock(block, x, y, z);

		{
			Tessellator t = Tessellator.instance;
			if (colour < 16) t.setColorOpaque_I(ItemEnderToken.codes[colour]);

			IIcon sideOverlay = ((BlockEnderTotem)block).sideOverlay;
			if (block.shouldSideBeRendered(world, x + 1, y, z, 6)) renderer.renderFaceXPos(block, x, y, z, sideOverlay);
		    if (block.shouldSideBeRendered(world, x - 1, y, z, 6)) renderer.renderFaceXNeg(block, x, y, z, sideOverlay);
		    if (block.shouldSideBeRendered(world, x, y, z + 1, 6)) renderer.renderFaceZPos(block, x, y, z, sideOverlay);
		    if (block.shouldSideBeRendered(world, x, y, z - 1, 6)) renderer.renderFaceZNeg(block, x, y, z, sideOverlay);
		    
		    IIcon topOverlay = ((BlockEnderTotem)block).topOverlay;
		    if (block.shouldSideBeRendered(world, x, y + 1, z, 6)) renderer.renderFaceYPos(block, x, y, z, topOverlay);
		}
		
		renderer.clearOverrideBlockTexture();
		
		renderer.setRenderBounds(0.8125D, 0.125D, 0.8125D, 0.1875D, 1.0D, 0.1875D);
		renderer.renderStandardBlock(block, x, y, z);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelID) {
		return false;
	}

	@Override
	public int getRenderId() {
		return Enderportation.instance.IDEnderTotem;
	}
}
