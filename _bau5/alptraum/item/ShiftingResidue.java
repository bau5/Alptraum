package _bau5.alptraum.item;

import _bau5.alptraum.Alptraum;
import net.minecraft.src.Item;

public class ShiftingResidue extends Item
{
	public ShiftingResidue(int i)
	{
		super(i);
		setMaxStackSize(65);
		setIconIndex(18);
	}
	public String getTextureFile()	
	{
		return Alptraum.textureFile;
	}
}
