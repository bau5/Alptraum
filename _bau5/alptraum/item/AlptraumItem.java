package _bau5.alptraum.item;

import java.util.List;

import _bau5.alptraum.Alptraum;
import _bau5.alptraum.world.TestEffect;

import cpw.mods.fml.client.FMLClientHandler;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityFX;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class AlptraumItem extends Item	
{
	public static final String itemNames[] = 
		{
			"Shifting Ore", "Shifting Ingot", "Shifting Residue"
		};
	protected AlptraumItem(int id) 
	{
		super(id);
		this.setTabToDisplayOn(CreativeTabs.tabMaterials);
		this.setMaxDamage(0);
	}
	public String getTextureFile()
	{
		return Alptraum.textureFile;
	}
	public String getItemNameIS(ItemStack itemstack)
	{
		return new StringBuilder().append("item").append(itemNames[itemstack.getItemDamage()]).toString();
	}
	public int getIconFromDamage(int par1)
    {
		switch(par1)
		{
		case 0:
			this.setIconIndex(16+par1);
			break;
		case 1:
			this.setIconIndex(16+par1);
			break;
		case 2:
			this.setIconIndex(16+par1);
			break;
		}
        return this.iconIndex;
    }
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
	        par3List.add(new ItemStack(par1, 1, 0));
	        par3List.add(new ItemStack(par1, 1, 1));
	        par3List.add(new ItemStack(par1, 1, 2));
    }
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World world, EntityPlayer ep)
    {
		switch(par1ItemStack.getItemDamage())
		{
		case 2:
			EntityFX var0 = new TestEffect(Alptraum.mc.theWorld, Alptraum.rand.nextInt(5), Alptraum.mc.thePlayer.posX, Alptraum.mc.thePlayer.posY, Alptraum.mc.thePlayer.posZ, 0F, 0F, 0F);
			if(!world.isRemote)
			{
				Alptraum.mc.effectRenderer.addEffect((EntityFX)var0);
			}
			break;
		}
		return par1ItemStack;
    }
	
}
