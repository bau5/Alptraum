package _bau5.alptraum;

import java.util.Random;

import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class AlpWorldGen implements IWorldGenerator
{
	private AlpLoot lootGen;
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		switch(world.provider.worldType)
		{
		case 0: generateSurface(world, random, chunkX*16, chunkZ*16);
			break;
		}
	}
	private void generateSurface(World world, Random random, int i, int j)
	{
		for (int k = 0; k < 75; k++)
        { 
            int x = i + random.nextInt(16);
            int y = random.nextInt(128);
            int z = j + random.nextInt(16);
            new AlpBlockGen(Alptraum.nightmareStone.blockID, 0, 4).generate(world, random, x, y, z);
        }
		lootGen = new AlpLoot(world);
		if(!Alptraum.alpWorldInfo.generatedLoot && lootGen.getCanGen()) 
		{
			int x1 = world.getSpawnPoint().posX + random.nextInt(30);
	        int y1 = 55 +random.nextInt(20);
	        int z1 = world.getSpawnPoint().posZ + random.nextInt(30);
	        lootGen.create(x1, y1, z1);
		}
		
	}
}
