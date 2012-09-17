package _bau5.alptraum;

import java.io.File;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


import _bau5.alptraum.client.ClientPacketHandler;
import _bau5.alptraum.item.AlpBookOrb;
import _bau5.alptraum.item.ShiftingAxe;
import _bau5.alptraum.item.ShiftingIngot;
import _bau5.alptraum.item.ShiftingOrb;
import _bau5.alptraum.item.ShiftingOre;
import _bau5.alptraum.item.ShiftingPickAxe;
import _bau5.alptraum.item.ShiftingResidue;
import _bau5.alptraum.item.ShiftingSphere;
import _bau5.alptraum.item.ShiftingSword;
import _bau5.alptraum.utility.BlockUtility;
import _bau5.alptraum.utility.TileEntityDiscoverer;
import _bau5.alptraum.world.AlpStone;
import _bau5.alptraum.world.AlpWorldGen;
import _bau5.alptraum.world.EntityTorchModel;
import _bau5.alptraum.world.EntityTorchRenderer;
import _bau5.alptraum.world.WorldSensitiveInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderBlocks;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ICraftingHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;

@Mod(modid = "_bau5Alptraum", name = "Alptraum", version = "0.01") 
@NetworkMod(clientSideRequired=true, serverSideRequired=false,
clientPacketHandlerSpec =
@SidedPacketHandler(channels = {"_bau5Alptraum" }, packetHandler = ClientPacketHandler.class),
serverPacketHandlerSpec =
@SidedPacketHandler(channels = {"_bau5Alptraum" }, packetHandler = ServerPacketHandler.class))
public class Alptraum 
{
	@SidedProxy(clientSide = "_bau5.alptraum.client.ClientProxy", serverSide = "_bau5.alptraum.CommonProxy")
	public static CommonProxy proxy;
	
	@Instance
	private static Alptraum instance;
	public static Minecraft mc = FMLClientHandler.instance().getClient();
	public static Random rand = new Random();

	public static String baseTextureFile = "/_bau5/alptraum/Textures/";
	public static String textureFile = baseTextureFile + "AlptraumSheet.png";
	public static String particlesTextureFile = baseTextureFile + "particles.png";
	public String[] alpSounds = {
			"insanity"
	};
	
	public static WorldSensitiveInfo alpWorldInfo;
	private AlpWorldGen worldGenerator;
	
	public static Block nightmareStone;
	public static Block blockUtility;
	public static Item itemBlockUtility;
	
	public static int BnmStoneID;
	public static int BalpUtilityID;
	
	public static int InmAxe;
	public static int InmPickAxe;
	public static int InmSword;
	public static int InmMulti1;
	public static int InmOrb;
	public static int InmSphere;
	public static int Inmbook1;
	public static int InmOre;
	public static int InmIngot;
	public static int InmResidue;
	
	public static Item shiftingOrb;
	public static Item shiftingAxe;
	public static Item shiftingPickaxe;
	public static Item shiftingSword;
	public static Item alpMultiItem;
	
	public static Item shiftingOre;
	public static Item shiftingIngot;
	public static Item shiftingResidue;
	
	public static Item shiftingSphere;
	public static Item bookOne;
	
	static EnumToolMaterial matShifting = EnumHelper.addToolMaterial("SHIFTING", 3, 1000, 8.0F, 4, 10);

	@PreInit
	public void preLoad(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		try 
		{
			config.load();

			BnmStoneID = config.getOrCreateBlockIdProperty("Nightmare Stone Block", 145).getInt(145);
			BalpUtilityID = config.getOrCreateBlockIdProperty("Utility Block", 146).getInt(146);
			
			InmAxe = config.getOrCreateIntProperty("Shifting Axe", Props.config.CATEGORY_ITEM, 6200).getInt(6200);
			InmPickAxe = config.getOrCreateIntProperty("Shifting Pick Axe", Props.config.CATEGORY_ITEM, 6201).getInt(6201);
			InmSword = config.getOrCreateIntProperty("Shifting Sword", Props.config.CATEGORY_ITEM, 6202).getInt(6202);
			InmMulti1 = config.getOrCreateIntProperty("Shifting Residue", Props.config.CATEGORY_ITEM, 6203).getInt(6203);
			InmOrb = config.getOrCreateIntProperty("Shifting Orb", Props.config.CATEGORY_ITEM, 6204).getInt(6204);
			InmSphere = config.getOrCreateIntProperty("Shifting Sphere", Props.config.CATEGORY_ITEM, 6205).getInt(6205);
			
			InmOre = config.getOrCreateIntProperty("Shifting Ore", Props.config.CATEGORY_ITEM, 6230).getInt(6230);
			InmIngot = config.getOrCreateIntProperty("Shifting Ingot", Props.config.CATEGORY_ITEM, 6231).getInt(6231);
			InmResidue = config.getOrCreateIntProperty("Shifting Residue", Props.config.CATEGORY_ITEM, 6230).getInt(6232);
			
			Inmbook1 = config.getOrCreateIntProperty("Book One", Props.config.CATEGORY_ITEM, 6220).getInt(6220);
		} catch(Exception e)
		{
			FMLLog.log(Level.SEVERE, e, "Alptraum: Difficulties loading config.");
		} finally
		{
			config.save();
		}
	}
	@Init
	public void initAlptraum(FMLInitializationEvent event)
	{
		MinecraftForgeClient.preloadTexture(textureFile);
		proxy.registerRenderInformation();
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		
		registerBlocks();
		registerItems();
		initAlpVars();
		initOthers();
		initSounds();
	}
     
	public void initSounds()
	{
		mc.installResource("newsound/mods/Alptraum/" + alpSounds[0] +".ogg", new File((mc.getMinecraftDir().getAbsolutePath()), "/resources/newsound/mods/Alptraum/" +alpSounds[0] +".ogg"));
		log("Sound Installed: " +alpSounds[0]);
//		System.out.println(alpSounds.length);
//		for(int i = 0; i < alpSounds.length; i++)
//		{
//			log("Sound Installed: " +alpSounds[i]);
//		}
	}
	private void registerBlocks() 
	{
		nightmareStone = new AlpStone(BnmStoneID, 0);
		blockUtility = new BlockUtility(BalpUtilityID, 0);
		
		GameRegistry.registerBlock(nightmareStone, _bau5.alptraum.world.AlpStoneItem.class);	
		GameRegistry.registerBlock(blockUtility, _bau5.alptraum.utility.BlockUtilityItem.class);
		GameRegistry.registerTileEntity(TileEntityDiscoverer.class, "Alp Discoverer");
		
		LanguageRegistry.instance().addStringLocalization("blockUtilDiscovery Table.name", "Discovery Block");
		
		LanguageRegistry.instance().addStringLocalization("blockNightmare Stone.name", "Nightmare Stone");
		LanguageRegistry.instance().addStringLocalization("blockDepleted Nightmare Stone.name", "Depleted Nightmare Stone");
		LanguageRegistry.instance().addStringLocalization("blockShifting Cobblestone.name", "Shifting Cobblestone");
	}
	private void registerItems()
	{
		shiftingIngot = new ShiftingIngot(InmIngot).setItemName("alpShiftingIngot").setTabToDisplayOn(CreativeTabs.tabMaterials);
		shiftingOre = new ShiftingOre(InmOre).setItemName("alpShiftingOre").setTabToDisplayOn(CreativeTabs.tabMaterials);
		shiftingResidue = new ShiftingResidue(InmResidue).setItemName("alpShiftingResidue").setTabToDisplayOn(CreativeTabs.tabMaterials);
//		alpMultiItem = new AlptraumItem(InmMulti1).setItemName("alpMulitItem1");
		
		shiftingAxe = new ShiftingAxe(InmAxe, matShifting).setItemName("shiftingAxe");
		shiftingPickaxe = new ShiftingPickAxe(InmPickAxe, matShifting).setItemName("shiftingPickAxe");
		shiftingSword = new ShiftingSword(InmSword, matShifting).setItemName("shiftingSword");
		shiftingOrb = new ShiftingOrb(InmOrb).setItemName("shiftingOrb");
		shiftingSphere = new ShiftingSphere(InmSphere).setItemName("shiftingSphere");
		bookOne = new AlpBookOrb(Inmbook1).setItemName("alpBookOne");
		
		GameRegistry.addSmelting(shiftingOre.shiftedIndex, new ItemStack(shiftingIngot, 1), 5F);
		
		LanguageRegistry.instance().addName(shiftingOre, "Shifting Ore");
		LanguageRegistry.instance().addName(shiftingIngot, "Shifting Ingot");
		LanguageRegistry.instance().addName(shiftingResidue, "Shifting Residue");
		
		LanguageRegistry.instance().addName(shiftingSphere, "Shifting Sphere");
		LanguageRegistry.instance().addName(shiftingOrb, "Shifting Orb");
		LanguageRegistry.instance().addName(shiftingAxe, "Shifting Axe");
		LanguageRegistry.instance().addName(shiftingPickaxe, "Shifting Pickaxe");
		LanguageRegistry.instance().addName(shiftingSword, "Shifting Sword");
	}
	private void initAlpVars()
	{
		alpWorldInfo = new WorldSensitiveInfo(this);
	}
	private void initOthers()
	{
		GameRegistry.registerWorldGenerator(new AlpWorldGen());
		TickRegistry.registerTickHandler(new AlpTicker(), Side.CLIENT);
		EntityRegistry.registerGlobalEntityID(_bau5.alptraum.world.EntityTorch.class, "Torch", EntityRegistry.findGlobalUniqueEntityId());
		RenderingRegistry.registerEntityRenderingHandler(_bau5.alptraum.world.EntityTorch.class, new EntityTorchRenderer(new EntityTorchModel(), 0.5F));
	}
	public static Alptraum instance()
	{
		return instance;
	}
	public static void saveInfo()
	{
		alpWorldInfo.saveWorldInfoToFile();
	}
	public static void loadInfo()
	{
		alpWorldInfo.loadWorldInfoFromFile();
	}
	public static Logger logger = FMLLog.getLogger();
	public static void logP(String message)
	{
		if(mc.thePlayer != null)
		{
			mc.thePlayer.addChatMessage(message);
		}
	}
	public static void playSound(String soundName)
	{
		mc.sndManager.playSoundFX("mods.Alptraum." + soundName, 1F, 1F);
	}
    public static void log(String message)
    {
        logger.log(Level.FINER, "Alptraum: " + message +"\n");
        System.out.printf("Alptraum: " + message +'\n');
    }
    public static void log(int message)
    {
        logger.log(Level.FINER, "Alptraum: " + message +"\n");
        System.out.printf("Alptraum: " + message +'\n');
    }
}
