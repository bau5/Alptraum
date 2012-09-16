package _bau5.alptraum.utility;

import _bau5.alptraum.Alptraum;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class BlockUtilityItem extends ItemBlock
{

	public BlockUtilityItem(int par1) 
	{
		super(par1);
		setMaxDamage(0);
		setHasSubtypes(true);
		Alptraum.itemBlockUtility = this;
	}
	@Override
	public int getMetadata(int metadata)
	{
		return metadata;
	}

	private static final String blockNames[] =
	{
		"Discovery Table"
	};

	@Override
	public String getItemNameIS(ItemStack itemstack)
	{
		return new StringBuilder().append("blockUtil").append(blockNames[itemstack.getItemDamage()]).toString();
	}
	
}
