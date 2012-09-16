package _bau5.alptraum.client;

import _bau5.alptraum.Alptraum;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderManager;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class BlockRenderTest implements ISimpleBlockRenderingHandler
{
	public static int renderID = RenderingRegistry.getNextAvailableRenderId();
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) 
	{
		return;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		renderer.overrideBlockTexture = Block.stone.blockIndexInTexture;  
        block.setBlockBounds(0.1F, 0.1F, 0.1F, 0.9F, 0.9F, 0.9F);  
        renderer.renderStandardBlock(block, x, y, z);  
         
        renderer.overrideBlockTexture = Alptraum.nightmareStone.blockIndexInTexture;  
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F); 
        renderer.renderStandardBlock(block, x, y, z); 

        return true;  
	}

	@Override
	public boolean shouldRender3DInInventory() 
	{
		return false;
	}

	@Override
	public int getRenderId() 
	{
		return renderID;
	}
	
}
