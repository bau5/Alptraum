package _bau5.alptraum;

import java.util.List;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemEditableBook;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class AlpBookOrb extends ItemEditableBook
{
	NBTTagCompound tag;
	public AlpBookOrb(int par1)
    {
        super(par1);
        this.setMaxStackSize(1);
        setItemName("nmBook1");
        setIconIndex(48);
        setTabToDisplayOn(CreativeTabs.tabMisc);
    }
	public String getTextureFile()	
	{
		return Alptraum.textureFile;
	}
    public String getItemDisplayName(ItemStack par1ItemStack)
    {
    	super.getItemDisplayName(par1ItemStack);
        return "\u00A7d" +"Mysterious Book";
    }
    public void addInformation(ItemStack par1ItemStack, List par2List)
    {
    	if((Boolean) Alptraum.instance().alpWorldInfo.getRecipeManager().getRecipesHM().get("Nightmare Orb"))
    	{
    		par2List.add("\u00A79" + "Shifting Orb");
    	}else par2List.add("\u00A78" + "<unknown>");
    }
	public boolean doesGuiPauseGame()
	{
		return false;
	}
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        ModLoader.openGUI(par3EntityPlayer, new NMGuiBook(par3EntityPlayer, par1ItemStack));
        Alptraum.instance().alpWorldInfo.getRecipeManager().enableRecipe("Nightmare Orb", true);
        Alptraum.saveInfo();
        return par1ItemStack;
    }
    public boolean getShareTag()
    {
        return true;
    }
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return true;
    }
}
