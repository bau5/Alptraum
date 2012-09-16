package _bau5.alptraum;

import javax.swing.Renderer;

import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderBlocks;
import net.minecraftforge.client.IItemRenderer;
import _bau5.alptraum.utility.TileEntityDiscoverer;
import _bau5.alptraum.utility.TileEntityDiscovererRenderer;
import _bau5.testing.TileEntityRendererTesting;
import _bau5.testing.TileEntityTesting;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	public static final int discovererModelID = RenderingRegistry.getNextAvailableRenderId();
	
	@Override 
	public void registerRenderInformation() 
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDiscoverer.class, new TileEntityDiscovererRenderer());
	}
}
