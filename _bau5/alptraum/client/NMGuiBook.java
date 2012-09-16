package _bau5.alptraum.client;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import _bau5.alptraum.Alptraum;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiScreenBook;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ScaledResolution;

public class NMGuiBook extends GuiScreenBook 
{
	public NMGuiBook(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) 
	{
		super(par1EntityPlayer, par2ItemStack, false);
	}
	public void drawScreen(int i, int j, float f)
	{
	    mc.renderEngine.bindTexture(mc.renderEngine.getTexture(Alptraum.baseTextureFile + "GUI/nmbook1.png"));
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int var5 = (this.width-150) / 2;
        int var6 = (this.height-200) / 2;
        this.drawTexturedModalRect(var5, var6, 20, 0, 176, 180);
        ScaledResolution var7 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glViewport((var7.getScaledWidth() - 320) / 2 * var7.getScaleFactor(), (var7.getScaledHeight() - 240) / 2 * var7.getScaleFactor(), 320 * var7.getScaleFactor(), 240 * var7.getScaleFactor());
        GL11.glTranslatef(-0.34F, 0.23F, 0.0F);
        GLU.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
	}
}
