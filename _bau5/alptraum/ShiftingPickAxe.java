package _bau5.alptraum;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemPickaxe;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ShiftingPickAxe extends ItemPickaxe
{

	public ShiftingPickAxe(int par1, EnumToolMaterial par2EnumToolMaterial) 
	{
		super(par1, par2EnumToolMaterial);
		setIconIndex(64);
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
			return 0.3F;
		}	
		else return super.getStrVsBlock(par1ItemStack, par2Block);
    }
	public void harvestOres(World world, EntityLiving el, int i, int j, int k)
	{
		ArrayList<AlpBlockPlaceHolder> blocks = new ArrayList<AlpBlockPlaceHolder>();
		
		for(int x = i -2; x < i +3; x++)
		{
			for(int y = j -2; y < j +3; y++)
			{
				for(int z = k -2; z < k +3; z++)
				{
					int a = world.getBlockId(x, y, z);
					if(a != 0 || a != 1)
					{
						blocks.add(new AlpBlockPlaceHolder(x, y, z, -1, a));
					}
				}
			}
		}
		for(int p = 0; p < 7; p++)
		{
			for(int x = 0; x < blocks.size(); x++)
			{
				AlpBlockPlaceHolder block = blocks.get(x);
				int i1 = block.getLoc()[0];
				int j1 = block.getLoc()[1];
				int k1 = block.getLoc()[2];
				int a = block.getBlockReplaced();
				if(a == 16)
				{
					world.spawnEntityInWorld(new EntityItem(world, i, j+1, k, new ItemStack(Item.coal, 1)));
					world.setBlockAndMetadataWithNotify(i1, j1, k1, Alptraum.nightmareStone.blockID, 2);
					blocks.remove(block);
				}
				else
				{
					if(a == 14 || a == 15 || a == 16 || a == 21 || a == 56 || a == 73 || a == 74 || a == 130)
					{
						if(a == 21 || a == 73 || a == 74)
						{
							Random rand = new Random();
							if(a == 56)
							{
								world.spawnEntityInWorld(new EntityItem(world, i, j+1, k, new ItemStack(Item.diamond, 1)));
								world.setBlockAndMetadataWithNotify(i1, j1, k1, Alptraum.nightmareStone.blockID, 2);
								blocks.remove(block);
							}
							if(a == 21)
							{
								world.spawnEntityInWorld(new EntityItem(world, i, j+1, k, new ItemStack(Item.dyePowder, rand.nextInt(4) +1)));
								world.setBlockAndMetadataWithNotify(i1, j1, k1, Alptraum.nightmareStone.blockID, 2);
								blocks.remove(block);
							}
							if(a == 73 || a == 74)
							{
								world.spawnEntityInWorld(new EntityItem(world, i, j+1, k, new ItemStack(Item.redstone, rand.nextInt(4) +1)));
								world.setBlockAndMetadataWithNotify(i1, j1, k1, Alptraum.nightmareStone.blockID, 2);
								blocks.remove(block);
							}
						}else
						{
							world.spawnEntityInWorld(new EntityItem(world, i, j+1, k, new ItemStack(Block.blocksList[a], 1, 0)));
							world.setBlockAndMetadataWithNotify(i1, j1, k1, Alptraum.nightmareStone.blockID, 2);
							blocks.remove(block);
						}
					}
					else
					{
						blocks.remove(block);
					}
				}	
			}
		}
		
		
		System.out.println(blocks.size());
	}
	public boolean func_77660_a(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLiving par7EntityLiving)
    {
		harvestOres(par7EntityLiving.worldObj, par7EntityLiving, par4, par5, par6);
        return super.func_77660_a(par1ItemStack, par2World, par3, par4, par5, par6, par7EntityLiving);
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
	
}
