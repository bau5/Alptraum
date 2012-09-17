package _bau5.alptraum.utility;

import java.util.Random;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.registry.GameRegistry;
import _bau5.alptraum.AlpRecipeManager;
import _bau5.alptraum.Alptraum;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemHoe;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemSword;
import net.minecraft.src.ItemTool;
import net.minecraft.src.Material;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class TileEntityDiscoverer extends TileEntity implements IInventory, ISidedInventory
{
	private ItemStack[] discovererStack = new ItemStack[12];
	private AlpRecipeManager recipeManager;
	
	private Random rand = new Random();
	
	private int residueNumberOfAttempts = 0;
	private int residueRequiredAttempts;
	
	public ItemStack currentItem;
	public float angle = 0.0F;
	//The number of ticks it will continue researching
	public int researchTime = 0;
	//The total number of ticks it will continue researching
	public int researchingFuelBurnTime = 0;
	//The current item's progress in time
	public int currentItemResearchTime = 0;
	public final static float move_speed = 0.1f;
	
	public TileEntityDiscoverer()
	{
//		residueRequiredAttempts = rand.nextInt(6) + 5;
		residueRequiredAttempts = 2;
		if(Alptraum.mc.theWorld != null)
		{
			recipeManager = Alptraum.alpWorldInfo.getRecipeManager();
		}
		System.out.println(residueNumberOfAttempts + " " +residueRequiredAttempts);
	}
	
	public void updateEntity()
	{	
		if(discovererStack[0] == null)
		{
			currentItemResearchTime = 0;
		}
		if(this.canReasearchItem() && currentItemResearchTime < 200)
		{
			++currentItemResearchTime;
			angle = currentItemResearchTime * 0.1F;
		}
		if(this.canReasearchItem() && currentItemResearchTime >= 200)
		{
			currentItemResearchTime = 0;

			if(residueNumberOfAttempts > 0)
			{
				if(residueNumberOfAttempts == residueRequiredAttempts)
				{
					this.researchItem();
					System.out.println("Smelted");
					residueNumberOfAttempts = 0;
					return;
				}
			} 
			++residueNumberOfAttempts;
			this.failResearch();
			System.out.println(residueNumberOfAttempts + " " +residueRequiredAttempts);
		}
	}
	
	private void failResearch()	
	{
		if(this.discovererStack[0] == null)
		{
			return;
		}
		else
		{
			int a = this.discovererStack[0].stackSize;
			if(a == 1)
			{
				 this.discovererStack[0] = null;
			}else --this.discovererStack[0].stackSize;
			if(Alptraum.mc.theWorld != null && !Alptraum.mc.theWorld.isRemote)
			{
				Alptraum.mc.theWorld.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "random.fizz", 1.0F, 1.0F);
			}
		}
	}
	private boolean canReasearchItem()
    {
        if (this.discovererStack[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack stack = this.getResearchResult(this.discovererStack[0]);
            if(stack == null)
            {
            	return false;
            }
            int slotIndex = 3; 
            while(slotIndex < discovererStack.length)
            {
            	if(discovererStack[slotIndex] != null)
            	{
            		if(discovererStack[slotIndex].isItemEqual(stack))
            		{
            			int result = discovererStack[slotIndex].stackSize + stack.stackSize;
            			if(result <= stack.getMaxStackSize())
            			{
            				return true;
            			}
            			else {
            				slotIndex++; 
            				break;
            			}
            		}
            		slotIndex++;
            		break;
            	}
            	return true;
            }
            return false;
        }
    }
	public ItemStack getResearchResult(ItemStack stack)
	{
		if(recipeManager != null)
		{
			return recipeManager.getResearchResult(stack);
		} else return null;
	}

    public void researchItem()
    {
        if (this.canReasearchItem())
        {
            ItemStack stack = this.getResearchResult(this.discovererStack[0]);

            for(int slotIndex = 3; slotIndex < discovererStack.length; slotIndex++)
            {
            	if(this.discovererStack[slotIndex] == null)
            	{
            		this.discovererStack[slotIndex] = stack.copy();
            		--this.discovererStack[0].stackSize;
            		if(this.discovererStack[0].stackSize <= 0)
            		{
            			this.discovererStack[0] = null;
            		}
            		break;
            	}
            	else if(this.discovererStack[slotIndex].isItemEqual(stack))
            	{
            		discovererStack[slotIndex].stackSize += stack.stackSize;
            		--this.discovererStack[0].stackSize;
            		if(this.discovererStack[0].stackSize <= 0)
            		{
            			this.discovererStack[0] = null;
            		}
            	}
            }
        }
    }

	public boolean isBurning()
	{
		return researchTime > 0;
	}
	
	@SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int par1)
    {
        return this.currentItemResearchTime * par1 / 200;
    }
	
	@SideOnly(Side.CLIENT)
    public int getBurnProgressScaled(int par1)
    {
        return this.currentItemResearchTime * par1 / 200;
    }

    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int par1)
    {
        if (this.researchingFuelBurnTime == 0)
        {
            this.researchingFuelBurnTime = 200;
        }

        return this.researchTime * par1 / this.researchingFuelBurnTime;
    }
    
    public static int getItemBurnTime(ItemStack par0ItemStack)
    {
        if (par0ItemStack == null)
        {
            return 0;
        }
        else
        {
            int itemId = par0ItemStack.getItem().shiftedIndex;
            Item item = par0ItemStack.getItem();
            
            if(itemId == Alptraum.shiftingResidue.shiftedIndex)
            {
            	return 200;
            }
            return 0;
        }
    }
    public static boolean isItemFuel(ItemStack par0ItemStack)
    {
        return getItemBurnTime(par0ItemStack) > 0;
    }
	@Override
	public int getSizeInventory() 
	{
		return discovererStack.length;
	}
	
	@Override
	@SideOnly (Side.CLIENT)	
	public ItemStack getStackInSlot(int var1) 
	{
		return discovererStack[var1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.discovererStack[par1] != null)
        {
            ItemStack var3;

            if (this.discovererStack[par1].stackSize <= par2)
            {
                var3 = this.discovererStack[par1];
                this.discovererStack[par1] = null;
                this.onInventoryChanged();
                return var3;
            }
            else
            {
                var3 = this.discovererStack[par1].splitStack(par2);

                if (this.discovererStack[par1].stackSize == 0)
                {
                    this.discovererStack[par1] = null;
                }

                this.onInventoryChanged();
                return var3;
            }
        }
        else
        {
            return null;
        }
    }

	@Override
	public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.discovererStack[par1] != null)
        {
            ItemStack var2 = this.discovererStack[par1];
            this.discovererStack[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

	
	@Override
	public void setInventorySlotContents(int var1, ItemStack stack) 
	{
		this.discovererStack[var1] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }
        onInventoryChanged();
	}

	@Override
	public String getInvName() 
	{
		return "discoveryInventory";
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) 
	{
		return true;
	}

	public void readFromNBT(NBTTagCompound nbtTagComp)
    {
        super.readFromNBT(nbtTagComp);
        NBTTagList var2 = nbtTagComp.getTagList("Items");
        
        this.discovererStack = new ItemStack[this.getSizeInventory()];
        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            byte var5 = var4.getByte("Slot");
            
            if (var5 >= 0 && var5 < this.discovererStack.length)
            {
                this.discovererStack[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
        
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound nbtTagComp)
    {
        super.writeToNBT(nbtTagComp);
        
        NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.discovererStack.length; ++var3)
        {
            if (this.discovererStack[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.discovererStack[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        nbtTagComp.setTag("Items", var2);
    }
	@Override
	public void openChest() 
	{
		
	}

	@Override
	public void closeChest() 
	{
		
	}

	@Override
	public int getStartInventorySide(ForgeDirection side)
	{
		if (side == ForgeDirection.DOWN) return 1;
        if (side == ForgeDirection.UP) return 0; 
        return 2;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) 
	{
		return 1;
	}
	
}
