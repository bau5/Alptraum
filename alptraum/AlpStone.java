package _bau5.alptraum;

import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class AlpStone extends Block
{
	public AlpStone(int i, int j)
	{
		super(i, j, Material.rock);
		this.blockIndexInTexture = j;
		this.blockHardness = 1.5F;
		setCreativeTab(CreativeTabs.tabBlock);
	    setRequiresSelfNotify();
	    if(j == 0)
	    {
	    	this.setLightValue(0.75F);
	    }
	}
	public String getTextureFile()
	{
		return Alptraum.textureFile;
	}
	public int idDropped(int par1, Random par2Random, int par3)
    {
		System.out.println(par1);
		switch(par1)
		{
		case 0: return Alptraum.shiftingOre.shiftedIndex; 
		case 1: return Alptraum.shiftingIngot.shiftedIndex;
		case 2: return Alptraum.shiftingResidue.shiftedIndex;
		}
        return this.blockID;
    }
	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) 
	{
		System.out.println(this.blockID);
		
	}
	protected int damageDropped(int metadata)
    {
        return metadata;
    }
	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) 
	{
		switch(par1World.getBlockMetadata(par2, par3, par4))
		{
		case 0: 
			par1World.setBlockAndMetadataWithNotify(par2, par3, par4, this.blockID, 1);
			break;
		case 1: 
			par1World.setBlockAndMetadataWithNotify(par2, par3, par4, this.blockID, 2);
			break;
		case 2: 
			par1World.setBlockAndMetadataWithNotify(par2, par3, par4, this.blockID, 0);
			break;
		}
	}
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) 
	{
		return blockIndexInTexture = meta;
	}
	@Override
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(this, 1, 0));
		par3List.add(new ItemStack(this, 1, 1));
		par3List.add(new ItemStack(this, 1, 2));
	}
}
