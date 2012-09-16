package _bau5.alptraum.utility;

import java.util.Random;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import _bau5.alptraum.Alptraum;
import _bau5.testing.ItemTesting;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockUtility extends BlockContainer
{
	public BlockUtility(int id, int meta)
	{
		super(id, meta, Material.rock);
		this.setBlockName("alpBlockUtility");
		this.setHardness(0.6F);
		this.blockIndexInTexture = 16;
		setCreativeTab(CreativeTabs.tabBlock);
	}
	@SideOnly(Side.CLIENT) 
	public boolean onBlockActivated(World par1World, int i, int j, int k, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
		par5EntityPlayer.openGui(Alptraum.instance(), 0, par1World, i, j, k);
        return true;
        
    }
	
	public int damageDropped(int metadata)
	{
		return metadata;
	}
	public void breakBlock(World world, int i, int j, int k, int par4, int par5, int par6)
	{
		TileEntityDiscoverer ted = null;
		TileEntity te = world.getBlockTileEntity(i, j, k);
		if(te instanceof TileEntityDiscoverer)
		{
			ted = (TileEntityDiscoverer)te;
		}
		if(ted != null)
		{
			for(int slotIndex = 0; slotIndex < ted.getSizeInventory(); ++slotIndex)
			{
				ItemStack stack = ted.getStackInSlot(slotIndex);
				if(stack != null)
				{
					float var2 = Alptraum.mc.theWorld.rand.nextFloat() * 0.8F + 0.1F;
                    float var3 = Alptraum.mc.theWorld.rand.nextFloat() * 0.8F + 0.1F;
                    float var4 = Alptraum.mc.theWorld.rand.nextFloat() * 0.8F + 0.1F;
					while(stack.stackSize > 0)
					{
						int var1 = Alptraum.mc.theWorld.rand.nextInt(21) + 10;
						if(var1 > stack.stackSize)
						{
							var1 = stack.stackSize;
						}
						stack.stackSize -= var1;
						EntityItem entItem = new EntityItem(world, (double)((float)i + var2), (double)((float)j + var3), (double)((float)k + var4), new ItemStack(stack.itemID, var1, stack.getItemDamage()));
						if(stack.hasTagCompound())
						{
							entItem.item.setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
						}
						float var5 = 0.05F;
						entItem.motionX = (double)((float)Alptraum.mc.theWorld.rand.nextGaussian() * var5);
						entItem.motionY = (double)((float)Alptraum.mc.theWorld.rand.nextGaussian() * var5 + 0.2F);
						entItem.motionZ = (double)((float)Alptraum.mc.theWorld.rand.nextGaussian() * var5);
						world.spawnEntityInWorld(entItem);
					}
				}
			}
		}
	}
	
	public String getTextureFile()	
	{
		return Alptraum.textureFile;
	}
	public TileEntity createNewTileEntity(World var1) 
	{
		return new TileEntityDiscoverer();
	}
	
	public boolean isOpaqueCube()
    {
        return false;	
    }

    public int getRenderType()
    {
        return -1;
    }
}

