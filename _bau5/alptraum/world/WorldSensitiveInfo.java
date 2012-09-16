package _bau5.alptraum.world;

import _bau5.alptraum.AlpRecipeManager;
import _bau5.alptraum.Alptraum;
import _bau5.alptraum.AlptraumSaver;
import _bau5.alptraum.SanityEvents;
import _bau5.alptraum.SanityMeter;

public class WorldSensitiveInfo 
{
	private static WorldSensitiveInfo instance;
	private Alptraum core;
	private AlpRecipeManager recipeManager;
	private AlptraumSaver worldInfoSaver;
	public String currentWorld = "";
	
	public static boolean generatedLoot;
	public static boolean foundLoot = false;
	public static int[] locOfGoods = new int[3];
	public static SanityMeter sanityMeter;
	public static SanityEvents sanityEvents;
	
	public WorldSensitiveInfo(Alptraum coreInstance)
	{
		if(Alptraum.mc.inGameHasFocus)
		{
			this.loadWorldInfoFromFile();
		}
		locOfGoods[0] = 0;
		locOfGoods[1] = 0;
		locOfGoods[2] = 0;
		instance = this;
		core = coreInstance;
		recipeManager = new AlpRecipeManager(this);
		
		worldInfoSaver = new AlptraumSaver(this);
		sanityEvents = new SanityEvents(this);
		sanityMeter = new SanityMeter();
        sanityEvents.setVariables();
        sanityMeter.initializeSanity();
	}
	public void updateWorldInUse()
	{
		if(!currentWorld.equals(core.mc.getIntegratedServer().getFolderName()))
		{
			System.out.println(currentWorld);
			currentWorld = core.mc.getIntegratedServer().getFolderName();
			System.out.println(currentWorld);
			recipeManager = new AlpRecipeManager(this);
		} else System.out.println("Already aligned to correct world.");
	}
	public AlpRecipeManager getRecipeManager()
	{
		return recipeManager;
	}
	public void loadWorldInfoFromFile()
	{
		updateWorldInUse();
		worldInfoSaver.readNBTFromFile(currentWorld);
	}
	public void saveWorldInfoToFile()
	{
		updateWorldInUse();
		worldInfoSaver.writeNBTToFile(currentWorld);
	}
	public static WorldSensitiveInfo instance()
	{
		return instance;
	}
}
