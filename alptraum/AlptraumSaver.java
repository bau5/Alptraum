package _bau5.alptraum;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;

import cpw.mods.fml.common.FMLLog;

import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.NBTTagCompound;

public class AlptraumSaver 
{
	private WorldSensitiveInfo worldInfo;
	private Alptraum core;
	
	private String currentWorld;

	public AlptraumSaver(WorldSensitiveInfo info)
	{
		worldInfo = info;
		core = Alptraum.instance();
	}
	public File getSaveFile()
	{
		File f = new File((Alptraum.mc.getMinecraftDir()) +"/saves/" +(Alptraum.mc.getIntegratedServer().getFolderName()) +"/Alptraum.dat");
		return f;
	}
	public void writeNBTToFile(String worldName)
	{
		try
		{
			File f = getSaveFile();
			if (f == null || !f.exists())
			{
				f.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(f);
			NBTTagCompound nbtc = new NBTTagCompound();
			
			nbtc.setIntArray("locOfGoods", worldInfo.locOfGoods);
			nbtc.setBoolean("generatedLoot", worldInfo.generatedLoot);
			nbtc.setDouble("sanityLevel", SanityMeter.getSanityLevel());
			nbtc.setBoolean("foundLoot", worldInfo.foundLoot);
			
			Set<Map.Entry<String, Boolean>> set = worldInfo.getRecipeManager().getRecipesHM().entrySet();
			for(Map.Entry<String, Boolean> recipe : set)
			{
				Alptraum.logP(recipe.getKey() +" " +recipe.getValue());
				nbtc.setBoolean(recipe.getKey(), recipe.getValue());
			}
			
			CompressedStreamTools.writeCompressed(nbtc, fos);
			fos.close();
			Alptraum.logP("Alptraum: Info saved.");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public void readNBTFromFile(String worldName)
	{
		try
		{
			File f = getSaveFile();
			if(!f.exists())
			{
				writeNBTToFile(worldInfo.currentWorld);
			}
			FileInputStream fis = new FileInputStream(f);
			NBTTagCompound nbtc = CompressedStreamTools.readCompressed(fis);
			if(nbtc.hasKey("locOfGoods"))
			{
				worldInfo.locOfGoods = nbtc.getIntArray("locOfGoods");
				worldInfo.generatedLoot = nbtc.getBoolean("generatedLoot");
				worldInfo.foundLoot = nbtc.getBoolean("foundLoot");
				SanityMeter.setSanityLevel(nbtc.getDouble("sanityLevel"));
			}
			Set<Map.Entry<String, Boolean>> set = core.alpWorldInfo.getRecipeManager().getRecipesHM().entrySet();
			for(Map.Entry<String, Boolean> recipe : set)
			{
				if(nbtc.hasKey(recipe.getKey()))
				{
					core.alpWorldInfo.getRecipeManager().enableRecipe(recipe.getKey(), nbtc.getBoolean(recipe.getKey()));
				}
			}
			Alptraum.logP("Alptraum: Info loaded.");
		} catch (Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Extra info file not found, generating...");
			writeNBTToFile(worldInfo.currentWorld);
		}
	}
}
