package _bau5.alptraum.world;

import net.minecraft.src.Item;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class AlpStoneItem extends ItemBlock
{

	public AlpStoneItem(int par1) 
	{
		super(par1);
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	@Override
	public int getMetadata(int metadata)
	{
		return metadata;
	}

	private static final String blockNames[] =
	{
		"Nightmare Stone", "Depleted Nightmare Stone", "Shifting Cobblestone"
	};

	@Override
	public String getItemNameIS(ItemStack itemstack)
	{
		return new StringBuilder().append("block").append(blockNames[itemstack.getItemDamage()]).toString();
	}
}
