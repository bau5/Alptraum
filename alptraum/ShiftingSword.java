package _bau5.alptraum;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemSword;
import net.minecraft.src.World;

public class ShiftingSword extends ItemSword
{

	public ShiftingSword(int par1, EnumToolMaterial par2EnumToolMaterial) 
	{
		super(par1, par2EnumToolMaterial);
		setIconIndex(66);
	}	
	public String getTextureFile()
	{
		return Alptraum.textureFile;
	}
	public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
    {
		if(par2EntityLiving instanceof EntityLiving)
		{
			par2EntityLiving.motionY = 1F;
		}
        return true;
    }
	public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) 
	{
		par1ItemStack.setItemDamage(this.getMaxDamage());
	}
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) 
    {
        if(stack.getItemDamage() == stack.getMaxDamage())
        {
        	return true;
        }
        else return false;
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
	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
		if(par1ItemStack.getItemDamage() == this.getMaxDamage())
		{
			return 0.1F;
		}	
		else return super.getStrVsBlock(par1ItemStack, par2Block);
    }
	
}
