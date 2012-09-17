package _bau5.alptraum.item;

import _bau5.alptraum.Alptraum;
import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemAxe;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ShiftingAxe extends ItemAxe
{

	public ShiftingAxe(int par1, EnumToolMaterial par2EnumToolMaterial) 
	{
		super(par1, par2EnumToolMaterial);
		this.setIconIndex(65);
	}
	public String getTextureFile()
	{
		return Alptraum.textureFile;
	}
	public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) 
	{
		par1ItemStack.setItemDamage(this.getMaxDamage());
	}
	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
		if(par1ItemStack.getItemDamage() == this.getMaxDamage())
		{
			return 0.1F;
		}	
		else return super.getStrVsBlock(par1ItemStack, par2Block);
    }
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
		InventoryPlayer inventory = par3EntityPlayer.inventory;
		for (int var2 = 0; var2 < par3EntityPlayer.inventory.mainInventory.length; ++var2)
        {
            if (par3EntityPlayer.inventory.mainInventory[var2] != null && par3EntityPlayer.inventory.mainInventory[var2].itemID == Alptraum.alpMultiItem.shiftedIndex && par3EntityPlayer.inventory.mainInventory[var2].getItemDamage() == 2)
            {
            	System.out.println("yup");
            	ItemStack residueItemStack = inventory.mainInventory[var2];
        		int stackSize = residueItemStack.stackSize;
        		if(stackSize >= 5)
        		{
        			for(int i = 0; i < 5; i++)
            		{
            			if(par3EntityPlayer.inventory.mainInventory[var2].getItemDamage() == 2)
            			{
                			par3EntityPlayer.inventory.mainInventory[var2].stackSize--;
            			}
            		}
        			par1ItemStack.damageItem(-100, par3EntityPlayer);
        			            		
        		}else if(stackSize < 5)
        		{
        			System.out.println("" +stackSize);
        			for(int i = 0; i <= stackSize +1; i++)
        			{
        				if(par3EntityPlayer.inventory.mainInventory[var2] != null && par3EntityPlayer.inventory.mainInventory[var2].getItemDamage() == 2)
            			{
                			par3EntityPlayer.inventory.mainInventory[var2].stackSize--;
                			stackSize--;
                    		System.out.println("" +stackSize);
                    		if(stackSize <= 0)
                    		{
                    			inventory.mainInventory[var2] = null;
                    		}
            			}
        				par1ItemStack.damageItem(-20, par3EntityPlayer);
        			}
        		}
        		if(par1ItemStack.getItemDamage() < 0)
        		{
        			par1ItemStack.setItemDamage(0);
        		}
        		return par1ItemStack;
            }
        }
        return par1ItemStack;
    }
	public void harvestTree(World world, int i, int j, int k)
	{
		System.out.println("" +i +" " +j +" " +k);
		int x = 1;
		int blockWoodId = 17;
		while(world.getBlockId(i, j-x, k) == 17)
		{
			System.out.println(x);
			x++;
		}
		if(world.getBlockId(i, j-x, k) == 17);
		{
//			if(sanityMeter.getSanityStage() > 2) 
//			{
				int b = j-x;
				System.out.println("Check " +x);
				for(int y = 0; y < 20; y++)
				{
					if(world.getBlockId(i, b+y, k) == 17)
					{
						int a = world.getBlockMetadata(i, b+y, k);
						System.out.println("Check " +y);
						if(!world.isRemote)
						{
							world.spawnEntityInWorld(new EntityItem(world, i, b, k, new ItemStack(Block.wood, 1, a)));
						}
						world.setBlockAndMetadataWithNotify(i, b+y, k, Alptraum.nightmareStone.blockID, 2);
						world.setBlockAndMetadataWithNotify(i, j, k, Alptraum.nightmareStone.blockID, 2);
//						if(sanityMeter.getSanityStage() < 5 && world.getBlockId(i+1, b+y, k) == Block.leaves.blockID || world.getBlockId(i, b+y, k +1) == Block.leaves.blockID)
//						{
//							world.setBlockWithNotify(i+1, b+y, k, Block.fire.blockID);
//							world.setBlockWithNotify(i, b+y, k+1, Block.fire.blockID);
//						}
					}
				}
//			} else return;
			
		}
	}
	public boolean func_77660_a(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLiving par7EntityLiving)
    {
		if(par3 == Block.wood.blockID)
		{
			harvestTree(par2World, par4, par5, par6);
			par1ItemStack.damageItem(1, par7EntityLiving);
			return true;
		}
		else return super.func_77660_a(par1ItemStack, par2World, par3, par4, par5, par6, par7EntityLiving);
    }
}
