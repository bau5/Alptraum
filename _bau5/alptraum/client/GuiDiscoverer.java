package _bau5.alptraum.client;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import _bau5.alptraum.Alptraum;
import _bau5.alptraum.utility.ContainerDiscoverer;
import _bau5.alptraum.utility.TileEntityDiscoverer;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiScreenBook;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.StatCollector;

public class GuiDiscoverer extends GuiContainer 
{	
	private TileEntityDiscoverer discovererInventory;
	
	public GuiDiscoverer(InventoryPlayer playerInv, TileEntityDiscoverer discoverer) 
	{
		super(new ContainerDiscoverer(playerInv, discoverer));
		this.discovererInventory = discoverer;
	}
	public boolean doesGuiPauseGame()
	{
		return false;
	}
	protected void drawGuiContainerForegroundLayer()
    {
        this.fontRenderer.drawString("\u00A7d" + "Discoverer", 22, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
    	int var4 = this.mc.renderEngine.getTexture("/_bau5/alptraum/Textures/GUI/discovery.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var4);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        
        int var1;
        
        if(this.discovererInventory.isBurning())
        {
        	var1 = this.discovererInventory.getBurnTimeRemainingScaled(12);
//        	this.drawTexturedModalRect(var5 + 56, var6 + 36 + 12 - var1, 176, 12 - var1, 14, var1 + 2);
        }
        var1 = this.discovererInventory.getCookProgressScaled(24);
        this.drawTexturedModalRect(var5 + 79, var6 + 34, 176, 1, 9, var1);
    }
}
