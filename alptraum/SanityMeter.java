package _bau5.alptraum;

import net.minecraft.src.EntityPlayer;
import cpw.mods.fml.client.FMLClientHandler;

public class SanityMeter 
{
	private static int saveTimer;
	private static double sanityLevelIC;
	private static Alptraum core;

	private static boolean toStage2;
	private static boolean toStage3;
	private static boolean toStage4;
	private static boolean toStage5;
	
	public static SanityMeter sanityMeter;
	
	public SanityMeter()
	{
		core = Alptraum.instance();
		sanityLevelIC = 100;
		System.out.println("Sanity Class");
	}
	public static void initializeSanity()
	{
		core.logP("Initialized: " +sanityLevelIC); 
		System.out.println("Initialized: "+sanityLevelIC);
	}
	public void affectSanity(float f) 
	{
		EntityPlayer ep = FMLClientHandler.instance().getClient().thePlayer;
		f = ep.getBrightness(f);
		switch(getSanityStage())
		{
		case 1: 
			if(f >= .5F && sanityLevelIC < 100)
			{
				increaseSanity(.004D);
				break;
			}
			if(f < .5F && sanityLevelIC > 80.1)
			{
				decreaseSanity(.0001D);
				break;
			}
			if(toStage2)
			{
				System.out.println("Check");
				sanityLevelIC = 79.9;
			}
			break;
		case 2: 
			System.out.println("Greater than 60");
			if(f >= .5F)
			{
				increaseSanity(.004D);
			}
			if(f < .5F && sanityLevelIC > 60)
			{
				decreaseSanity(.0001D);
			}
			break;
		case 3: 
			System.out.println("Greater than 40");
			if(f >= .5F)
			{
				increaseSanity(.004D);
			}
			if(f < .5F && sanityLevelIC > 40)
			{
				decreaseSanity(.0001D);
			}
			break;
		case 4: 
			System.out.println("Greater than 20");
			if(f >= .5F)
			{
				increaseSanity(.004D);
			}
			if(f < .5F && sanityLevelIC > 20)
			{
				decreaseSanity(.0001D);
			}
			break;
		case 5: 
			System.out.println("Greater than 0");
			if(f >= .5F)
			{
				increaseSanity(.004D);
			}
			if(f < .5F && sanityLevelIC > 0)
			{
				decreaseSanity(.0001D);
			}
			break;
		}
		if(saveTimer >= 10000)
		{
			core.logP("Saved");
			WorldSensitiveInfo.instance().saveWorldInfoToFile();
			saveTimer = 0;
		}
		saveTimer++;
	}
	public static double setSanityLevel(double i)
	{
		sanityLevelIC = i;
		return sanityLevelIC;
	}
	public static double getSanityLevel()
	{	
		return sanityLevelIC;
	}
	public static void enableStageSwitch()
	{
		int a = getSanityStage();
		switch(a)
		{
		case 1:
			toStage2 = true;
			toStage3 = false;
			toStage4 = false;
			toStage5 = false;
			System.out.println("toStage2 = " +toStage2);
			break;
		case 2:
			toStage2 = false;
			toStage3 = true;
			toStage4 = false;
			toStage5 = false;
			System.out.println("toStage3 = " +toStage3);
			break;
		case 3:
			toStage2 = false;
			toStage3 = false;
			toStage4 = true;
			toStage5 = false;
			System.out.println("toStage4 = " +toStage4);
			break;
		case 4:
			toStage2 = false;
			toStage3 = false;
			toStage4 = false;
			toStage5 = true;
			System.out.println("toStage5 = " +toStage5);
			break;
		}
	}
	public static void setActiveStage(int stage)
	{
		switch(stage)
		{
		case 1:
			toStage2 = true;
			toStage3 = false;
			toStage4 = false;
			toStage5 = false;
			core.logP("toStage2 = " +toStage2);
			break;
		case 2:
			toStage2 = false;
			toStage3 = true;
			toStage4 = false;
			toStage5 = false;
			core.logP("toStage3 = " +toStage3);
			break;
		case 3:
			toStage2 = false;
			toStage3 = false;
			toStage4 = true;
			toStage5 = false;
			core.logP("toStage4 = " +toStage4);
			break;
		case 4:
			toStage2 = false;
			toStage3 = false;
			toStage4 = false;
			toStage5 = true;
			core.logP("toStage5 = " +toStage5);
			break;
		}
	}
	public static int getActiveStage()
	{
		if(toStage2)
		{
			return 2;
		}
		else if(toStage3)
		{
			return 3;
		}
		else if(toStage4)
		{
			return 4;
		}
		else if(toStage5)
		{
			return 5;
		}
		else 
		{
			return 1;
		}
		
	}
	public static int getSanityStage()
	{	
		if(sanityLevelIC >= 80) return 1;
		if(sanityLevelIC < 80 && sanityLevelIC >= 60) return 2;
		if(sanityLevelIC < 60 && sanityLevelIC >= 40) return 3;
		if(sanityLevelIC < 40 && sanityLevelIC >= 20) return 4;
		if(sanityLevelIC < 20 && sanityLevelIC >= 0) return 5;
		else return -1;
	}
	public static void decreaseSanity(double i) 
	{
		if(getSanityLevel() > 0)
		{
			sanityLevelIC = sanityLevelIC - i;
		} else sanityLevelIC = 0;
	}
	public static void increaseSanity(double i)
	{
		if(sanityLevelIC < 100)
		{
			sanityLevelIC = sanityLevelIC + i;
		} else sanityLevelIC = 100;
	}
	public static boolean isInsane()
	{
		if(sanityLevelIC >= 50)
		{
			return false;
		}
		else 
		{
			return true;
		}
	}
}
