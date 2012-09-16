package _bau5.alptraum;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public class SanityEvents 
{
	private static SanityEvents instance;
	private Alptraum core;
	private WorldSensitiveInfo worldInfo;

	private World leWorld;
	private EntityPlayer lePlayer;
	
	private ArrayList<AlpBlockPlaceHolder> replacedBlocks;
	private Random rand = new Random();

	private long coolDown;
	private int intensity;
	private boolean replaceOresEnabled;
	private boolean doneOnce;
	
	public SanityEvents(WorldSensitiveInfo info)
	{
		instance = this;
		worldInfo = info;
		core = Alptraum.instance();
		
		replacedBlocks = new ArrayList<AlpBlockPlaceHolder>();
		doneOnce = false;
		replaceOresEnabled = true;
		coolDown = 0;
	}
	
	public void doBySanityLevel()
	{
		intensity = (int)SanityMeter.getSanityLevel() / 20;
		if(SanityMeter.getSanityLevel() < 80)
		{
			if(SanityMeter.getSanityLevel() <20)
			{
				eliminateTorches(5);
			}
			coolDown = coolDown + 5;
			if(coolDown >= 1000 && SanityMeter.getSanityLevel() < 10)
			{
				replaceOres(Block.grass);
				coolDown = 0;
			}else if(coolDown == 100 || coolDown == 200 || coolDown == 300 || coolDown == 400 || coolDown == 500 || coolDown == 600 || coolDown == 700 || coolDown == 800 || coolDown == 900)
			{
				addBackOres(false);
			}else if(coolDown >= 1000) coolDown = 0;
		} 
		else 
		{
			addBackOres(true);
		}
	}
	
	public void addBackOres(boolean flag)
	{
		if(!flag && replacedBlocks.size() != 0)
		{
			int i = replacedBlocks.size();
			int j = rand.nextInt(i);
			replacedBlocks.get(j).putBack();
			replacedBlocks.remove(j);
		} else if (flag && replacedBlocks.size() != 0)
		{
			for(int i = 0; i <= replacedBlocks.size() -1; i++)
			{
				replacedBlocks.get(i).putBack();
				replacedBlocks.remove(i);
			}
		}
	}
	
	public void eliminateTorches(int a) 
	{
		int x = (int) lePlayer.posX;	
		int y = (int) lePlayer.posY;
		int z = (int) lePlayer.posZ;
		for(int i = x - a; i < a + x; i++)
		{
			for(int j = y - a ; j < a + y; j++)
			{
				for(int k = z - a; k < a + z; k++)
				{
					if(leWorld.getBlockId(i, j, k) == Block.torchWood.blockID)
					{
						leWorld.editingBlocks = true;
						if(leWorld.isRemote)
						{
							leWorld.setBlockWithNotify(i, j, k, 0);
							Alptraum.logP("Setting");
						}
						leWorld.editingBlocks = false;
						EntityTorch et = new EntityTorch(leWorld, i, j, k, 75 +rand.nextInt(125));
						leWorld.spawnEntityInWorld(et);
					}
				}
			}
		}
	}
	
	public void replaceOres(Block blockToReplace)
	{
		if(replaceOresEnabled)
		{			
			Alptraum.log("yes");
			
			int i = (int)lePlayer.posX;
			int j = (int)lePlayer.posY;
			int k = (int)lePlayer.posZ;
			
			int i1 = blockToReplace.blockID;
			for(int x = 0; x < 5; x++)
			{
				for(int y = 0; y < 5; y++)
				{
					for(int z = 0; z < 5; z++)
					{
						if(leWorld.getBlockId(i+x, j, k) == i1 && rand.nextBoolean() && !rand.nextBoolean())
						{
							leWorld.setBlockAndMetadataWithNotify(i+x, j, k, Alptraum.nightmareStone.blockID, 2);
							replacedBlocks.add(new AlpBlockPlaceHolder(i+x, j, k, 10, i1));
						}
						if(leWorld.getBlockId(i-x, j, k) == i1 && rand.nextBoolean() && !rand.nextBoolean())
						{
							leWorld.setBlockAndMetadataWithNotify(i-x, j, k, Alptraum.nightmareStone.blockID, 2);
							replacedBlocks.add(new AlpBlockPlaceHolder(i-x, j, k, 10, i1));
						}
						if(leWorld.getBlockId(i+x, j, k+z) == i1 && rand.nextBoolean() && !rand.nextBoolean())
						{
							leWorld.setBlockAndMetadataWithNotify(i+x, j, k+z, Alptraum.nightmareStone.blockID, 2);
							replacedBlocks.add(new AlpBlockPlaceHolder(i+x, j, k+z, 10, i1));
						}
						if(leWorld.getBlockId(i+x, j, k-z) == i1 && rand.nextBoolean() && !rand.nextBoolean())
						{
							leWorld.setBlockAndMetadataWithNotify(i+x, j, k-z, Alptraum.nightmareStone.blockID, 2);
							replacedBlocks.add(new AlpBlockPlaceHolder(i+x, j, k-z, 10, i1));
						}
						if(leWorld.getBlockId(i-x, j, k+z) == i1 && rand.nextBoolean() && !rand.nextBoolean())
						{
							leWorld.setBlockAndMetadataWithNotify(i-x, j, k+z, Alptraum.nightmareStone.blockID, 2);
							replacedBlocks.add(new AlpBlockPlaceHolder(i-x, j, k+z, 10, i1));
						}
						if(leWorld.getBlockId(i-x, j, k-z) == i1 && rand.nextBoolean() && !rand.nextBoolean())
						{
							leWorld.setBlockAndMetadataWithNotify(i-x, j, k-z, Alptraum.nightmareStone.blockID, 2);
							replacedBlocks.add(new AlpBlockPlaceHolder(i-x, j, k-z, 10, i1));
						}

						if(leWorld.getBlockId(i+x, j+y, k) == i1 && rand.nextBoolean() && !rand.nextBoolean())
						{
							leWorld.setBlockAndMetadataWithNotify(i+x, j+y, k, Alptraum.nightmareStone.blockID, 2);
							replacedBlocks.add(new AlpBlockPlaceHolder(i+x, j+y, k, 10, i1));
						}
						if(leWorld.getBlockId(i-x, j+y, k) == i1 && rand.nextBoolean() && !rand.nextBoolean())
						{
							leWorld.setBlockAndMetadataWithNotify(i-x, j+y, k, Alptraum.nightmareStone.blockID, 2);
							replacedBlocks.add(new AlpBlockPlaceHolder(i-x, j+y, k, 10, i1));
						}
						if(leWorld.getBlockId(i+x, j+y, k+z) == i1 && rand.nextBoolean() && !rand.nextBoolean())
						{
							leWorld.setBlockAndMetadataWithNotify(i+x, j+y, k+z, Alptraum.nightmareStone.blockID, 2);
							replacedBlocks.add(new AlpBlockPlaceHolder(i+x, j+y, k+z, 10, i1));
						}
						if(leWorld.getBlockId(i+x, j+y, k-z) == i1 && rand.nextBoolean() && !rand.nextBoolean())
						{
							leWorld.setBlockAndMetadataWithNotify(i+x, j+y, k-z, Alptraum.nightmareStone.blockID, 2);
							replacedBlocks.add(new AlpBlockPlaceHolder(i+x, j+y, k-z, 10, i1));
						}
						if(leWorld.getBlockId(i-x, j+y, k+z) == i1 && rand.nextBoolean() && !rand.nextBoolean())
						{
							leWorld.setBlockAndMetadataWithNotify(i-x, j+y, k+z, Alptraum.nightmareStone.blockID, 2);
							replacedBlocks.add(new AlpBlockPlaceHolder(i-x, j+y, k+z, 10, i1));
						}
						if(leWorld.getBlockId(i-x, j+y, k-z) == i1 && rand.nextBoolean() && !rand.nextBoolean())
						{
							leWorld.setBlockAndMetadataWithNotify(i-x, j+y, k-z, Alptraum.nightmareStone.blockID, 2);
							replacedBlocks.add(new AlpBlockPlaceHolder(i-x, j+y, k-z, 10, i1));
						}
						if(leWorld.getBlockId(i+x, j-y, k) == i1 && rand.nextBoolean() && !rand.nextBoolean())
						{
							leWorld.setBlockAndMetadataWithNotify(i+x, j-y, k, Alptraum.nightmareStone.blockID, 2);
							replacedBlocks.add(new AlpBlockPlaceHolder(i+x, j-y, k, 10, i1));
						}
						if(leWorld.getBlockId(i-x, j-y, k) == i1 && rand.nextBoolean() && !rand.nextBoolean())
						{
							leWorld.setBlockAndMetadataWithNotify(i-x, j-y, k, Alptraum.nightmareStone.blockID, 2);
							replacedBlocks.add(new AlpBlockPlaceHolder(i-x, j-y, k, 10, i1));
						}
						if(leWorld.getBlockId(i+x, j-y, k+z) == i1 && rand.nextBoolean() && !rand.nextBoolean())
						{
							leWorld.setBlockAndMetadataWithNotify(i+x, j-y, k+z, Alptraum.nightmareStone.blockID, 2);
							replacedBlocks.add(new AlpBlockPlaceHolder(i+x, j-y, k+z, 10, i1));
						}
						if(leWorld.getBlockId(i+x, j-y, k-z) == i1 && rand.nextBoolean() && !rand.nextBoolean())
						{
							leWorld.setBlockAndMetadataWithNotify(i+x, j-y, k-z, Alptraum.nightmareStone.blockID, 2);
							replacedBlocks.add(new AlpBlockPlaceHolder(i+x, j-y, k-z, 10,i1));
						}
						if(leWorld.getBlockId(i-x, j-y, k+z) == i1 && rand.nextBoolean() && !rand.nextBoolean())
						{
							leWorld.setBlockAndMetadataWithNotify(i-x, j-y, k+z, Alptraum.nightmareStone.blockID, 2);
							replacedBlocks.add(new AlpBlockPlaceHolder(i-x, j-y, k+z, 10, i1));
						}
						if(leWorld.getBlockId(i-x, j-y, k-z) == i1 && rand.nextBoolean() && !rand.nextBoolean())
						{
							leWorld.setBlockAndMetadataWithNotify(i-x, j-y, k-z, Alptraum.nightmareStone.blockID, 2);
							replacedBlocks.add(new AlpBlockPlaceHolder(i-x, j-y, k-z, 10, i1));
						}
					}
				}
			}
		}
	}
	public static SanityEvents instance()
	{
		return instance;
	}
	public void setVariables()
	{
		leWorld = Alptraum.instance().mc.theWorld;
		lePlayer = Alptraum.instance().mc.thePlayer;
		core = Alptraum.instance();
	}
}
