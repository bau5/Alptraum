package _bau5.alptraum.world;

import java.util.Random;

import _bau5.alptraum.Alptraum;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.World;

public class AlpLoot 
{
	Random rand = new Random();
	private World world;
	private static boolean canGen;
	
	public AlpLoot(World worldObj)
	{
		world = worldObj;
		canGen = true;
	}
	public boolean getCanGen()
	{
		return canGen;
	}
	public boolean create(int i, int j, int k) 
	{
		if(world.getBlockId(i, j, k) != Block.grass.blockID || !canGen)
		{
			System.out.println("ineligible location.");
			return false;
		}
		j = j +1;
		world.editingBlocks = true;
		world.setBlockAndMetadataWithNotify(i, j, k, 98, 1);
		world.setBlockAndMetadataWithNotify(i+1, j, k, 98, 2);
		System.out.println("creating");

		for(int a = 0; a < 5; a++)
		{
			int y = j-a;
			world.setBlockWithNotify(i, y-1, k, 0);
			world.setBlockWithNotify(i+1, y-1, k, 0);
			if(a > 0)
			{
				world.setBlockWithNotify(i-1, y, k, 4);
				world.setBlockWithNotify(i, y, k+1, 4);
				world.setBlockWithNotify(i, y, k-1, 4);
				world.setBlockWithNotify(i+1, y, k+1, 4);
				world.setBlockWithNotify(i+1, y, k-1, 4);
				world.setBlockWithNotify(i+2, y, k, 4);
				if(rand.nextBoolean())
				{
					world.setBlockWithNotify(i, y, k, Block.web.blockID);
				}
				else if(rand.nextInt(2)==1)
					{
						world.setBlockWithNotify(i+1, y, k, Block.web.blockID);
					}else world.setBlockWithNotify(i+1, y, k, Block.gravel.blockID);
			}
			if(a == 4)
			{
				TileEntityChest tec = new TileEntityChest();
				int f = rand.nextInt(27);
				int g = rand.nextInt(27);
				{
					if (g != f)
					{
						tec.setInventorySlotContents(g, new ItemStack(Item.paper, rand.nextInt(5)));
						if(g+f != f && g+f <= 27 && g-f >= 0)
						{
							tec.setInventorySlotContents(g+f-rand.nextInt(3), new ItemStack(Item.bone, rand.nextInt(5)));
							tec.setInventorySlotContents(g-f+rand.nextInt(3), new ItemStack(Item.bone, rand.nextInt(5)));
						}
					}
				}
				tec.setInventorySlotContents(f, new ItemStack(Alptraum.shiftingResidue, f+g +10));
				world.setBlockWithNotify(i, y, k, Block.chest.blockID);
				world.setBlockTileEntity(i, y, k, tec);
				tec.updateEntity();
				world.setBlockAndMetadataWithNotify(i, y-1, k, Alptraum.nightmareStone.blockID, 2);
				world.setBlockAndMetadataWithNotify(i+1, y-1, k, Alptraum.nightmareStone.blockID,2 );
			}
		}
		world.editingBlocks = false;
		canGen = false;
		Alptraum.alpWorldInfo.generatedLoot = true;
		System.out.println("Generated");
		int[] loc = new int[3];
		loc[0] = i;
		loc[1] = j;
		loc[2] = k;
		System.out.println("" +i +" " +j +" " +k);
		Alptraum.alpWorldInfo.locOfGoods = loc;
		
		return true;
	}
}
