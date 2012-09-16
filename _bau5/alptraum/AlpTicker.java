package _bau5.alptraum;

import java.util.EnumSet;
import java.util.Random;

import _bau5.alptraum.utility.TileEntityDiscoverer;
import _bau5.alptraum.world.LeaderFX;
import _bau5.alptraum.world.TestEffect;
import _bau5.alptraum.world.WorldSensitiveInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSelectWorld;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class AlpTicker implements ITickHandler 
{
	public static boolean inGame = false;
	private GuiScreen curScreen;
	private World world;
	private int coolDown;
	private boolean first;
	private boolean previousPause;
	private boolean switchedSaves;
	private boolean spawned;
	Minecraft mc = Alptraum.mc;

	public AlpTicker()
	{
		spawned = false;
		switchedSaves = false;
	}
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) 
	{
		
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) 
	{
		if(type.equals(EnumSet.of(TickType.CLIENT)))
		{
			curScreen = Minecraft.getMinecraft().currentScreen;
			if(curScreen != null)
			{
				inGame = false;
				onTickInGui();
			}
			else 
			{
				inGame = true;
				onTickInGame();
			}
		}
		
	}

	private void onTickInGame() 
	{
		int a[] = null;
		if(first && mc.inGameHasFocus)
		{
			Alptraum.loadInfo();
			WorldSensitiveInfo.instance().sanityEvents.setVariables();
			Alptraum.logP("First!");
			Alptraum.logP("Sanity Level: " +SanityMeter.getSanityLevel());
			if(WorldSensitiveInfo.locOfGoods != null)
			{
				Alptraum.logP("Location: " +WorldSensitiveInfo.locOfGoods[0] +" " +WorldSensitiveInfo.locOfGoods[1] +" " +WorldSensitiveInfo.locOfGoods[2]);
			}
			first = false;
			coolDown = 0;
			switchedSaves = false;
		}
		if(!WorldSensitiveInfo.foundLoot)
		{
			if(mc.thePlayer.inventory.hasItem(Alptraum.bookOne.shiftedIndex))
			{
				WorldSensitiveInfo.foundLoot = true;
			}
		}
		if(coolDown >= 20)
		{
			WorldSensitiveInfo.instance().saveWorldInfoToFile();
		}
		if(WorldSensitiveInfo.locOfGoods != null && !spawned && !WorldSensitiveInfo.foundLoot)
		{
			a = WorldSensitiveInfo.locOfGoods;
			coolDown++;
			if(coolDown >= 20)
			{
				mc.effectRenderer.addEffect(new LeaderFX(mc.theWorld, mc.theWorld.rand.nextInt(4), mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, 0, 0, 0));
				coolDown = 0;
				if(mc.theWorld.isRemote)
				{				
					for (int var6 = a[0] - 2; var6 <= a[0] + 2; ++var6)
			        {
			            for (int var7 = a[2] - 2; var7 <= a[2] + 2; ++var7)
			            {
			                if (var6 > a[0] - 2 && var6 < a[0] + 2 && var7 == a[2] - 1)
			                {
			                    var7 = a[2] + 2;
			                }

			                if (Alptraum.rand.nextInt(16) == 0 && Alptraum.rand.nextBoolean() == true)
			                {
			                    for (int var8 = a[1]; var8 <= a[1] + 1; ++var8)
			                    {
			                            mc.effectRenderer.addEffect(new TestEffect(mc.theWorld, Alptraum.rand.nextInt(5), (double)a[0] + 0.5D , (double)a[1] + 2.0D, (double)a[2] + 0.5D, (double)((float)(var6 - a[0]) + Alptraum.rand.nextFloat()) - 0.5D, (double)((float)(var8 - a[1]) - Alptraum.rand.nextFloat() - 1.0F), (double)((float)(var7 - a[2]) + Alptraum.rand.nextFloat()) - 0.5D));
			                    }
			                }
			            }
			        }	
				}
			}
		}
	}

	private void onTickInGui() 
	{
		if(mc.isGamePaused)
		{
			if(mc.theWorld != null && mc.isGamePaused && !previousPause)
			{
				Alptraum.saveInfo();
			}
			previousPause = true;
		}
		if((mc.currentScreen instanceof GuiMainMenu || mc.currentScreen instanceof GuiSelectWorld) && !switchedSaves)
		{
			new WorldSensitiveInfo(Alptraum.instance());
			first = true;
			previousPause = true;
			switchedSaves = true;
			Alptraum.alpWorldInfo.generatedLoot = false;
			Alptraum.alpWorldInfo.foundLoot = false;
			spawned = false;
		}
	}

	@Override
	public EnumSet<TickType> ticks() 
	{
		return EnumSet.of(TickType.RENDER, TickType.CLIENT);
	}

	@Override
	public String getLabel() 
	{
		return "Alptraum Ticker";
	}
	

}
