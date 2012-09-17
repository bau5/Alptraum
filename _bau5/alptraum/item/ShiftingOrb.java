package _bau5.alptraum.item;

import java.util.Random;

import _bau5.alptraum.Alptraum;
import _bau5.alptraum.world.LeaderFX;

import cpw.mods.fml.client.FMLClientHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ShiftingOrb extends Item
{

	public ShiftingOrb(int par1) 
	{
		super(par1);
		setIconIndex(32);
		setMaxStackSize(1);
		setMaxDamage(50);
		this.setTabToDisplayOn(CreativeTabs.tabTools);
	}
	public String getTextureFile()
	{
		return Alptraum.textureFile;
	}
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if(par2World.isRemote)
		{
//			SanityEvents.instance().eliminateTorches(5);
			Alptraum.mc.effectRenderer.addEffect(new LeaderFX(par2World, 1, par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, 0, 0, 0));
		}
        return par1ItemStack;
    }
	public boolean tryPlaceIntoWorld(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int par7, float par8, float par9, float par10)
    {
		if(world.isRemote)
		{
			Alptraum.logP("Yes");
			return false;
		}
		if(!world.isRemote)
		{
			Alptraum.logP("No");
			Random rand = new Random();
			Minecraft mc = FMLClientHandler.instance().getClient();
			int itemDamage = itemstack.getItemDamage();
			if(itemDamage < 0)
			{
				itemstack.setItemDamage(0);
			}
			int i1 = world.getBlockId(i, j, k);
			if(i1 == Alptraum.nightmareStone.blockID)
			{
				if(world.getBlockMetadata(i, j, k) == 0)
				{
					if(itemDamage != 0)
					{
						world.setBlockAndMetadataWithNotify(i, j, k, Alptraum.nightmareStone.blockID, 1);
						world.spawnEntityInWorld(new EntityItem(world, i, j, k, new ItemStack(Alptraum.alpMultiItem, 3 +rand.nextInt(4), 2)));
						Alptraum.playSound("insanity");
						itemstack.damageItem(-5, entityplayer);
					}
					if(itemDamage == 0)
					{
						mc.sndManager.playSoundFX("random.fizz", 1.0F, 1.0F);			
					}
				}
			}
			if(i1 == Block.grass.blockID)
			{
				world.setBlockAndMetadataWithNotify(i, j, k, Alptraum.nightmareStone.blockID, 2);
			}
			if(i1 == Block.stone.blockID)
			{
				world.setBlockWithNotify(i, j, k, Alptraum.nightmareStone.blockID);
			}
			if(i1 == Block.cobblestone.blockID && itemDamage < 50)
			{
				world.setBlockWithNotify(i, j, k, Block.stone.blockID);
				itemstack.damageItem(1, entityplayer);
			}
			if(i1 == Alptraum.nightmareStone.blockID && world.getBlockMetadata(i, j, k) == 2 && itemDamage <=40)
			{
				world.setBlockWithNotify(i, j, k, Block.cobblestone.blockID);
				itemstack.damageItem(10, entityplayer);
				world.spawnEntityInWorld(new EntityItem(world, i, j, k, new ItemStack(Alptraum.alpMultiItem, 5 +rand.nextInt(4), 2)));
				Alptraum.playSound("insanity");
			}
			return true;
		}
		else return false;
    }
	
}
