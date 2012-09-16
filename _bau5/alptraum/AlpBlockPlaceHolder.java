package _bau5.alptraum;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.src.Block;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;

public class AlpBlockPlaceHolder 
{
	private int locX;
	private int locY;
	private int locZ;
	private int life;
	private int blockReplaced;
	
	public AlpBlockPlaceHolder(int i, int j, int k, int i1, int blockID)
	{
		locX = i;
		locY = j;
		locZ = k;
		life = i1;
		blockReplaced = blockID;
		System.out.println("New " +locX +" " +locY +" " +locZ +" " +" " +blockReplaced +" " +i1);
	}
	public int[] getLoc()
	{
		int [] a = new int[3];
		a[0] = locX;
		a[1] = locY;
		a[2] = locZ;
		return a;
	}
	public int getBlockReplaced()
	{
		return blockReplaced;
	}
	public void putBack()
	{
		System.out.println("Put" +" " +locX +" " +locY +" " +locZ +" " +" " +blockReplaced);
		World world = FMLClientHandler.instance().getClient().theWorld;
		world.setBlockWithNotify(locX, locY, locZ, Block.grass.blockID);	
	}
}
