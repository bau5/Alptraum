package _bau5.alptraum;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

import net.minecraft.src.EntityFX;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;
import net.minecraftforge.client.ForgeHooksClient;

public class LeaderFX extends EntityFX
{
	private int coolDown;
	private double distToGoX;
	private double distToGoZ;
	private double hypotDist;
	public static double destX;
	public static double destY;
	public static double destZ;
	
	
	public LeaderFX(World par1World, int index, double par2, double par4, double par6, double par8, double par10, double par12) 
	{
		super(par1World, par2, par4, par6, 0, 0, 0);
		this.particleMaxAge = 150;
		this.worldObj = par1World;
		this.setParticleTextureIndex(0);
		this.noClip = true;
		coolDown = 0;
		this.destX = WorldSensitiveInfo.locOfGoods[0] + .5;
		this.destY = WorldSensitiveInfo.locOfGoods[1];
		this.destZ = WorldSensitiveInfo.locOfGoods[2] + .5;
		this.posX = par2;
        this.posY = par4;
        this.posZ = par6;
        this.motionX = 0;
        this.motionY = 0;
        this.motionZ = 0;
	}
	public String getTexture()
	{
		return Alptraum.particlesTextureFile;
	}
    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
    {
    	Tessellator tessellator1 = new Tessellator();
        tessellator1.startDrawingQuads();
        tessellator1.setBrightness(getBrightnessForRender(par2));
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Alptraum.mc.renderEngine.getTexture(getTexture()));
        float f = (float)(getParticleTextureIndex() % 16) / 16F;
        float f1 = f + 0.0624375F;
        float f2 = (float)(getParticleTextureIndex() / 16) / 16F;
        float f3 = f2 + 0.0624375F;
        float f4 = 0.1F * particleScale;
        float f5 = (float)((prevPosX + (posX - prevPosX) * (double)par2) - interpPosX);
        float f6 = (float)((prevPosY + (posY - prevPosY) * (double)par2) - interpPosY);
        float f7 = (float)((prevPosZ + (posZ - prevPosZ) * (double)par2) - interpPosZ);
        float f8 = 1.0F;
        tessellator1.setColorOpaque_F(particleRed * f8, particleGreen * f8, particleBlue * f8);
        tessellator1.addVertexWithUV(f5 - par3 * f4 - par6 * f4, f6 - par4 * f4, f7 - par5 * f4 - par7 * f4, f1, f3);
        tessellator1.addVertexWithUV((f5 - par3 * f4) + par6 * f4, f6 + par4 * f4, (f7 - par5 * f4) + par7 * f4, f1, f2);
        tessellator1.addVertexWithUV(f5 + par3 * f4 + par6 * f4, f6 + par4 * f4, f7 + par5 * f4 + par7 * f4, f, f2);
        tessellator1.addVertexWithUV((f5 + par3 * f4) - par6 * f4, f6 - par4 * f4, (f7 + par5 * f4) - par7 * f4, f, f3);
   
        tessellator1.draw();
   
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Alptraum.mc.renderEngine.getTexture("/particles.png")); 
    }
    public void onUpdate()
    {
		this.prevPosX = this.posX;
	    this.prevPosY = this.posY;
	    this.prevPosZ = this.posZ;
	    
	    if (this.particleAge++ >= this.particleMaxAge)
	    {
	        this.setDead();
	    }
	    this.distToGoX = Math.abs(this.posX - this.destX);
	    this.distToGoZ = Math.abs(this.posZ - this.destZ);
	    this.hypotDist = Math.sqrt((Math.pow(this.distToGoX, 2) + Math.pow(this.distToGoZ, 2)));
	    
	    if(this.posX > this.destX)
    	{
    		if(motionX == 0)
    		{
    			motionX -= 0.01D;
    		}
    		if(motionX > 0)
    		{
    			motionX = 0;
    		}
    		this.motionX -= 0.004 * (Math.abs(posX - destX) /10);
    	}
    	if(this.posX < this.destX)
    	{
    		
    		if(motionX == 0)
    		{
    			motionX += 0.01D;
    		}
    		if(motionX < 0)
    		{
    			motionX = 0;
    		}
    		this.motionX += 0.004 * (Math.abs(destX - posX) /10);
    	}
    	if(this.posZ > this.destZ)
    	{
    		if(motionZ == 0)
    		{
    			motionZ -= 0.01D;
    		}
    		if(motionZ > 0)
    		{
    			motionZ = 0;
    		}

    		this.motionZ -= 0.004 * (Math.abs(posZ - destZ) /10);
    	}
    	if(this.posZ < this.destZ)
    	{
    		if(motionZ == 0)
    		{
    			motionZ += 0.01D;
    		}
    		if(motionZ < 0)
    		{
    			motionZ = 0;
    		}
    		this.motionZ += 0.004 * (Math.abs(destZ - posZ) /10);
    	}
	    if(hypotDist <= 1)
	    {
	    	this.motionY -= 0.005D;
	    	if(this.posX > this.destX)
	    	{
	    		this.motionX -= 0.0001D;
	    	}
	    	if(this.posX < this.destX)
	    	{
	    		this.motionX += 0.0001D;
	    	}
	    	if(this.posZ > this.destZ)
	    	{
	    		this.motionZ -= 0.0001D;
	    	}
	    	if(this.posZ < this.destZ)
	    	{
	    		this.motionZ += 0.0001D;
	    	}
	    }
    	this.moveEntity(motionX, motionY, motionZ);
    }
}
