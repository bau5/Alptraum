package _bau5.alptraum;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ShiftingOre extends Item	
{
	protected ShiftingOre(int i) {
		super(i);
		setMaxStackSize(65);
		setIconIndex(16);
	}
	public String getTextureFile()	
	{
		return Alptraum.textureFile;
	}
}
