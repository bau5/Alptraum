package _bau5.alptraum;

import java.io.File;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.Configuration;

public class Props 
{

	public static Configuration config;
	
	public static int BnmStoneID;
	
	public static int InmAxe;
	public static int InmPickAxe;
	public static int InmSword;
	public static int InmMulti1;
	public static int InmOrb;
	public static int InmSphere;
	
	public static void loadProperties(String mod)
	{
		initProps(mod);
	
		if(config != null)
		{
			BnmStoneID = config.getOrCreateBlockIdProperty("Nightmare Stone Block", 145).getInt(145);
			InmAxe = config.getOrCreateIntProperty("Shifting Axe", Props.config.CATEGORY_ITEM, 6200).getInt(6200);
			InmPickAxe = config.getOrCreateIntProperty("Shifting Pick Axe", Props.config.CATEGORY_ITEM, 6201).getInt(6201);
			InmSword = config.getOrCreateIntProperty("Shifting Sword", Props.config.CATEGORY_ITEM, 6202).getInt(6202);
			InmMulti1 = config.getOrCreateIntProperty("Shifting Residue", Props.config.CATEGORY_ITEM, 6203).getInt(6203);
			InmOrb = config.getOrCreateIntProperty("Shifting Orb", Props.config.CATEGORY_ITEM, 6204).getInt(6204);
			InmSphere = config.getOrCreateIntProperty("Shifting Sphere", Props.config.CATEGORY_ITEM, 6205).getInt(6205);
		}
	}
	public static void initProps(String mod)
	{
		File f = new File(Minecraft.getMinecraftDir() +"/config/" +mod);
		f.mkdir();
		File newFile = new File(Minecraft.getMinecraftDir() +"/config/" +mod +"/main.cfg");
		try
		{
			newFile.createNewFile();
			System.out.println("Config found.");
		} catch (IOException ex)
		{
			System.out.println("Config not found.");
			System.out.println(ex);
		}
		config = new Configuration(newFile);
		config.load();
		
		config.save();
	}
	
}
