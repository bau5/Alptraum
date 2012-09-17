package _bau5.alptraum.item;

import _bau5.alptraum.Alptraum;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ShiftingIngot extends Item	
{
	public ShiftingIngot(int i) {
		super(i);
		setMaxStackSize(64);
		setIconIndex(17);
	}
	public String getTextureFile()	
	{
		return Alptraum.textureFile;
	}
}
