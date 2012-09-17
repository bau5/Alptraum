package _bau5.alptraum;

import _bau5.alptraum.client.GuiDiscoverer;
import _bau5.alptraum.client.NMGuiBook;
import _bau5.alptraum.utility.ContainerDiscoverer;
import _bau5.alptraum.utility.TileEntityDiscoverer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiNewChat;
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
		  if(te instanceof TileEntityDiscoverer)
		  {
			  TileEntityDiscoverer ted = (TileEntityDiscoverer)te;
			  return new ContainerDiscoverer(player.inventory, (TileEntityDiscoverer)te);
		  }
		  else return null;
	  }
	
	  @Override
	  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	  {
		  if(ID == 0)
		  {
			  return new GuiDiscoverer(player.inventory, (TileEntityDiscoverer)world.getBlockTileEntity(x, y, z));
		  }
		  if(ID == 1)
		  {
			  return new NMGuiBook(player, player.getHeldItem());
		  }
		  return null;
	  }
	
}
