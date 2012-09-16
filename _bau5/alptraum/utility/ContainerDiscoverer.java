package _bau5.alptraum.utility;

import _bau5.alptraum.client.ClientPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class ContainerDiscoverer extends Container
{
	private TileEntityDiscoverer discoverer;
	
	public ContainerDiscoverer(InventoryPlayer invPlayer, TileEntityDiscoverer discoverer)
	{
		this.discoverer = discoverer;
		this.discoverer.openChest();
		this.layoutContainer(invPlayer, discoverer);
		
	}
	public ItemStack slotClick (int slot, int button, boolean flag, EntityPlayer player) 
	{
		  ItemStack stack = super.slotClick(slot, button, flag, player);
		  return stack;
		 }
	private void layoutContainer(InventoryPlayer invPlayer,	TileEntityDiscoverer discoverer) 
	{

		this.addSlotToContainer(new Slot(discoverer, 0, 39, 23));
		this.addSlotToContainer(new Slot(discoverer, 1, 24, 50));
		this.addSlotToContainer(new Slot(discoverer, 2, 54, 50));
		
		this.addSlotToContainer(new Slot(discoverer, 3, 97, 17));
		for(int discRow = 0; discRow < 3; discRow++)
		{
			for(int discCol = 0; discCol < 3; discCol++)
			{
				this.addSlotToContainer(new Slot(discoverer, 3 + (discCol + discRow * 3), 97 + discCol * 18, 17 + discRow * 18));
			}
		}
		
		int var3;
        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(invPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(invPlayer, var3, 8 + var3 * 18, 142));
        }
	}
	
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return true;
    }
	public ItemStack transferStackInSlot(int par1)
    {
        return null;
    }

}
