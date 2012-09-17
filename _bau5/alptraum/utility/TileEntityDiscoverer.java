
package _bau5.alptraum.utility;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.registry.GameRegistry;
import _bau5.alptraum.Alptraum;
import net.minecraft.src.Block;
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
		discovererStack[0] = new ItemStack(Block.dirt);
		discovererStack[1] = new ItemStack(Alptraum.shiftingResidue);
	}
	
	public void updateEntity()
	{
		if(discovererStack[0] == null)
		{
			currentItemResearchTime = 0;
		}
		angle += move_speed;
		if(this.canSmelt() && currentItemResearchTime < 200)
		{
			++currentItemResearchTime;
			System.out.println("Cooking");
		}
		if(this.canSmelt() && currentItemResearchTime >= 200)
		{
			currentItemResearchTime = 0;
			this.smeltItem();
			System.out.println("Smelted");
		}
	}
	
	private boolean canSmelt()
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
		if(stack.itemID == Alptraum.shiftingResidue.shiftedIndex)
		{
			return new ItemStack(Alptraum.bookOne);
		}
		return null;
	}

    public void smeltItem()
    {
        if (this.canSmelt())
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
//            if (this.discovererStack[4] == null)
//            {
//                this.discovererStack[4] = stack.copy();
//            }
//            else if (this.discovererStack[4].isItemEqual(stack))
//            {
//                discovererStack[4].stackSize += stack.stackSize;
//            }
//
//            --this.discovererStack[0].stackSize;
//
//            if (this.discovererStack[0].stackSize <= 0)
//            {
//                this.discovererStack[0] = null;
//            }
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

	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
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
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
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

        par1NBTTagCompound.setTag("Items", var2);
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








//package _bau5.alptraum.utility;
//
//import java.util.Random;
//
//import cpw.mods.fml.common.Side;
//import cpw.mods.fml.common.asm.SideOnly;
//import cpw.mods.fml.common.registry.GameRegistry;
//import _bau5.alptraum.AlpRecipeManager;
//import _bau5.alptraum.Alptraum;
//import net.minecraft.src.Block;
//import net.minecraft.src.Entity;
//import net.minecraft.src.EntityPlayer;
//import net.minecraft.src.FurnaceRecipes;
//import net.minecraft.src.IInventory;
//import net.minecraft.src.Item;
//import net.minecraft.src.ItemBlock;
//import net.minecraft.src.ItemHoe;
//import net.minecraft.src.ItemStack;
//import net.minecraft.src.ItemSword;
//import net.minecraft.src.ItemTool;
//import net.minecraft.src.Material;
//import net.minecraft.src.NBTTagCompound;
//import net.minecraft.src.NBTTagList;
//import net.minecraft.src.TileEntity;
//import net.minecraftforge.common.ForgeDirection;
//import net.minecraftforge.common.ISidedInventory;
//
//public class TileEntityDiscoverer extends TileEntity implements IInventory, ISidedInventory
//{
//	private ItemStack[] discovererStack = new ItemStack[12];
//	private AlpRecipeManager recipeManager;
//	
//	private Random rand = new Random();
//	
//	private int residueNumberOfAttempts = 0;
//	private int residueRequiredAttempts;
//	
//	public ItemStack currentItem;
//	public float angle = 0.0F;
//	//The number of ticks it will continue researching
//	public int researchTime = 0;
//	//The total number of ticks it will continue researching
//	public int researchingFuelBurnTime = 0;
//	//The current item's progress in time
//	public int currentItemResearchTime = 0;
//	public final static float move_speed = 0.1f;
//	
//	public TileEntityDiscoverer()
//	{
////		residueRequiredAttempts = rand.nextInt(6) + 5;
//		residueRequiredAttempts = 2;
//		if(Alptraum.mc.theWorld != null)
//		{
//			recipeManager = Alptraum.alpWorldInfo.getRecipeManager();
//		}
//		System.out.println(residueNumberOfAttempts + " " +residueRequiredAttempts);
//	}
//	
//	public void onInventoryChanged()
//	{
//		if(worldObj != null)
//		{
//			worldObj.markBlockAsNeedsUpdate(xCoord, yCoord, zCoord);
//		}
//		super.onInventoryChanged();
//	}
//	public void updateEntity()
//	{	
//		if(worldObj.isRemote)
//		{
//			if(discovererStack[0] == null)
//			{
//				currentItemResearchTime = 0;
//				if(angle != 0.0F)
//				{
//					angle -= 0.1F * Math.abs(angle/0.1F); 
//				}
//			}
//			if(this.canReasearchItem() && currentItemResearchTime < 200)
//			{
//				++currentItemResearchTime;
//				angle = currentItemResearchTime * 0.1F;
//			}
//			if(this.canReasearchItem() && currentItemResearchTime >= 200)
//			{
//				currentItemResearchTime = 0;
//				this.researchItem();
////				if(residueNumberOfAttempts > 0)
////				{
////					if(residueNumberOfAttempts == residueRequiredAttempts)
////					{
////						this.researchItem();
////						System.out.println("Smelted");
////						residueNumberOfAttempts = 0;
////						return;
////					}
////				} 
////				++residueNumberOfAttempts;
////				this.failResearch();
////				System.out.println(residueNumberOfAttempts + " " +residueRequiredAttempts);
//			}
//		}
//	}
//	
//	private void failResearch()	
//	{
//		if(this.discovererStack[0] == null)
//		{
//			return;
//		}
//		else
//		{
//			int a = this.discovererStack[0].stackSize;
//			if(a == 1)
//			{
//				this.discovererStack[0] = null;
//				this.onInventoryChanged();
//			}else --this.discovererStack[0].stackSize;
//			this.onInventoryChanged();
//			if(Alptraum.mc.theWorld != null && !Alptraum.mc.theWorld.isRemote)
//			{
//				Alptraum.mc.theWorld.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "random.fizz", 1.0F, 1.0F);
//			}
//		}
//	}
//	private boolean canReasearchItem()
//    {
//        if (this.discovererStack[0] == null)
//        {
//            return false;
//        }
//        else
//        {
//            ItemStack stack = this.getResearchResult(this.discovererStack[0]);
//            if(stack == null)
//            {
//            	return false;
//            }
//            int slotIndex = 3; 
//            while(slotIndex < discovererStack.length)
//            {
//            	if(discovererStack[slotIndex] != null)
//            	{
//            		if(discovererStack[slotIndex].isItemEqual(stack))
//            		{
//            			int result = discovererStack[slotIndex].stackSize + stack.stackSize;
//            			if(result <= stack.getMaxStackSize())
//            			{
//            				return true;
//            			}
//            			else {
//            				slotIndex++; 
//            				break;
//            			}
//            		}
//            		slotIndex++;
//            		break;
//            	}
//            	return true;
//            }
//            return false;
//        }
//    }
//	public ItemStack getResearchResult(ItemStack stack)
//	{
//		if(recipeManager != null)
//		{
//			return recipeManager.getResearchResult(stack);
//		} else return null;
//	}
//
//    public void researchItem()
//    {
//        if (this.canReasearchItem())
//        {
//            ItemStack stack = this.getResearchResult(this.discovererStack[0]);
//
//            for(int slotIndex = 3; slotIndex < discovererStack.length; slotIndex++)
//            {
//            	if(this.discovererStack[slotIndex] == null)
//            	{
//            		this.discovererStack[slotIndex] = stack.copy();
//            		--this.discovererStack[0].stackSize;
//            		if(this.discovererStack[0].stackSize <= 0)
//            		{
//            			this.discovererStack[0] = null;
//            		}
//            		break;
//            	}
//            	else if(this.discovererStack[slotIndex].isItemEqual(stack))
//            	{
//            		discovererStack[slotIndex].stackSize += stack.stackSize;
//            		--this.discovererStack[0].stackSize;
//            		if(this.discovererStack[0].stackSize <= 0)
//            		{
//            			this.discovererStack[0] = null;
//            		}
//            	}
//            }
//
//            this.onInventoryChanged();
//        }
//    }
//    public void receiveClientEvent(int par1, int par2) 
//    {
//    	
//    }
//	public boolean isBurning()
//	{
//		return researchTime > 0;
//	}
//	
//	@SideOnly(Side.CLIENT)
//    public int getCookProgressScaled(int par1)
//    {
//        return this.currentItemResearchTime * par1 / 200;
//    }
//	
//	@SideOnly(Side.CLIENT)
//    public int getBurnProgressScaled(int par1)
//    {
//        return this.currentItemResearchTime * par1 / 200;
//    }
//
//    @SideOnly(Side.CLIENT)
//    public int getBurnTimeRemainingScaled(int par1)
//    {
//        if (this.researchingFuelBurnTime == 0)
//        {
//            this.researchingFuelBurnTime = 200;
//        }
//
//        return this.researchTime * par1 / this.researchingFuelBurnTime;
//    }
//    
//    public static int getItemBurnTime(ItemStack par0ItemStack)
//    {
//        if (par0ItemStack == null)
//        {
//            return 0;
//        }
//        else
//        {
//            int itemId = par0ItemStack.getItem().shiftedIndex;
//            Item item = par0ItemStack.getItem();
//            
//            if(itemId == Alptraum.shiftingResidue.shiftedIndex)
//            {
//            	return 200;
//            }
//            return 0;
//        }
//    }
//    public static boolean isItemFuel(ItemStack par0ItemStack)
//    {
//        return getItemBurnTime(par0ItemStack) > 0;
//    }
//	@Override
//	public int getSizeInventory() 
//	{
//		return discovererStack.length;
//	}
//	
//	@Override
//	@SideOnly (Side.CLIENT)	
//	public ItemStack getStackInSlot(int var1) 
//	{
//		return discovererStack[var1];
//	}
//
//	@Override
//	public ItemStack decrStackSize(int par1, int par2)
//    {
//        if (this.discovererStack[par1] != null)
//        {
//            ItemStack var3;
//
//            if (this.discovererStack[par1].stackSize <= par2)
//            {
//                var3 = this.discovererStack[par1];
//                this.discovererStack[par1] = null;
//                this.onInventoryChanged();
//                return var3;
//            }
//            else
//            {
//                var3 = this.discovererStack[par1].splitStack(par2);
//
//                if (this.discovererStack[par1].stackSize == 0)
//                {
//                    this.discovererStack[par1] = null;
//                }
//
//                this.onInventoryChanged();
//                return var3;
//            }
//        }
//        else
//        {
//            return null;
//        }
//    }
//
//	@Override
//	public ItemStack getStackInSlotOnClosing(int par1)
//    {
//        if (this.discovererStack[par1] != null)
//        {
//            ItemStack var2 = this.discovererStack[par1];
//            this.discovererStack[par1] = null;
//			super.onInventoryChanged();
//            return var2;
//        }
//        else
//        {
//            return null;
//        }
//    }
//
//	
//	@Override
//	public void setInventorySlotContents(int var1, ItemStack stack) 
//	{
//		this.discovererStack[var1] = stack;
//
//        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
//        {
//            stack.stackSize = this.getInventoryStackLimit();
//        }
//        onInventoryChanged();
//	}
//
//	@Override
//	public String getInvName() 
//	{
//		return "discoveryInventory";
//	}
//
//	@Override
//	public int getInventoryStackLimit() 
//	{
//		return 64;
//	}
//
//	@Override
//	public boolean isUseableByPlayer(EntityPlayer var1) 
//	{
//		return true;
//	}
//
//	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
//    {
//        super.readFromNBT(par1NBTTagCompound);
//        NBTTagList tagList = par1NBTTagCompound.getTagList("Items");
//        this.discovererStack = new ItemStack[this.getSizeInventory()];
//
//        for (int itemIndex = 0; itemIndex < tagList.tagCount(); ++itemIndex)
//        {
//            NBTTagCompound nbtTagCompound = (NBTTagCompound)tagList.tagAt(itemIndex);
//            byte slotId = nbtTagCompound.getByte("Slot");
//
//            if (slotId >= 0 && slotId < this.discovererStack.length)
//            {
//                this.discovererStack[slotId] = ItemStack.loadItemStackFromNBT(nbtTagCompound);
//            }
//        }
//
//        this.researchTime = par1NBTTagCompound.getShort("researchTime");
//        this.currentItemResearchTime = par1NBTTagCompound.getShort("currentItemTimeResearched");
//        this.researchingFuelBurnTime = getItemBurnTime(this.discovererStack[1]);
//    }
//	
//    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
//    {
//        super.writeToNBT(par1NBTTagCompound);
//        par1NBTTagCompound.setShort("researchTime", (short)this.researchTime);
//        par1NBTTagCompound.setShort("currentItemTimeResearched", (short)this.currentItemResearchTime);
//        NBTTagList tagList = new NBTTagList();
//
//        for (int stackIndex = 0; stackIndex < this.discovererStack.length; ++stackIndex)
//        {
//            if (this.discovererStack[stackIndex] != null)
//            {
//            	System.out.println(discovererStack[stackIndex].toString());
//                NBTTagCompound nbtTagCompound = new NBTTagCompound();
//                nbtTagCompound.setByte("Slot", (byte)stackIndex);
//                this.discovererStack[stackIndex].writeToNBT(nbtTagCompound);
//                tagList.appendTag(nbtTagCompound);
//            }
//        }
//
//        par1NBTTagCompound.setTag("Items", tagList);
//    }
//
//	@Override
//	public void openChest() 
//	{
//		
//	}
//
//	@Override
//	public void closeChest() 
//	{
//		
//	}
//
//	@Override
//	public int getStartInventorySide(ForgeDirection side)
//	{
//		if (side == ForgeDirection.DOWN) return 1;
//        if (side == ForgeDirection.UP) return 0; 
//        return 2;
//	}
//
//	@Override
//	public int getSizeInventorySide(ForgeDirection side) 
//	{
//		return 1;
//	}
//	
//}
