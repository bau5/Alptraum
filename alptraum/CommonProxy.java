package _bau5.alptraum;

import _bau5.alptraum.utility.ContainerDiscoverer;
import _bau5.alptraum.utility.GuiDiscoverer;
import _bau5.alptraum.utility.TileEntityDiscoverer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{
	  public void registerRenderInformation() 
	  {
		  
	  }
	  @Override
	  public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	  {
		  TileEntity te = world.getBlockTileEntity(x, y, z);

		  switch(ID)
		  {
		  case 0: return new ContainerDiscoverer(player.inventory, (TileEntityDiscoverer)te);
		  }
		  
		  return null;
	  }
	
	  @Override
	  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	  {
		  TileEntity te = world.getBlockTileEntity(x, y, z);
		  if(te instanceof TileEntityDiscoverer)
		  {
			  return new GuiDiscoverer(player.inventory, (TileEntityDiscoverer)te);
		  }else return null;
	  }
	
}
