package _bau5.alptraum;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

import net.minecraft.src.EntityFX;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;
import net.minecraftforge.client.ForgeHooksClient;

public class TestEffect extends EntityFX
{
	private double field_70568_aq;
    private double field_70567_ar;
    private double field_70566_as;
	private int coolDown;
	
	public TestEffect(World par1World, int index, double par2, double par4, double par6, double par8, double par10, double par12) 
	{
		super(par1World, par2, par4, par6, par8, par10, par12);
		this.particleMaxAge = 100;
		this.worldObj = par1World;
		this.setParticleTextureIndex(index);
		this.noClip = true;
		coolDown = 0;
		this.field_70568_aq = this.posX = par2;
        this.field_70567_ar = this.posY = par4;
        this.field_70566_as = this.posZ = par6;
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

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
    	 this.prevPosX = this.posX;
         this.prevPosY = this.posY;
         this.prevPosZ = this.posZ;
         float var1 = (float)this.particleAge / (float)this.particleMaxAge;
         var1 = 1.0F - var1;
         float var2 = 1.0F - var1;
         var2 *= var2;
         var2 *= var2;
         this.posX = this.field_70568_aq + this.motionX * (double)var1;
         this.posY = this.field_70567_ar + this.motionY * (double)var1 - (double)(var2 * 1.2F);
         this.posZ = this.field_70566_as + this.motionZ * (double)var1;

         if (this.particleAge++ >= this.particleMaxAge)
         {
             this.setDead();
         }
//    	double goodsX = WorldSensitiveInfo.locOfGoods[0];
//    	double goodsY = WorldSensitiveInfo.locOfGoods[1];
//    	double goodsZ = WorldSensitiveInfo.locOfGoods[2];
//        this.prevPosX = this.posX;
//        this.prevPosY = this.posY;
//        this.prevPosZ = this.posZ;
//
//        coolDown++;
//        if(coolDown >= 50)
//        {
//        	coolDown = 0;
//        }
//        if (this.particleAge++ >= this.particleMaxAge)
//        {
//            this.setDead();
//        }
//        
//        this.motionY += 0.004D;
//        this.moveEntity(this.motionX, this.motionY, this.motionZ);
//
//        if (this.posY == this.prevPosY)
//        {
//            this.motionX *= 1.1D;
//            this.motionZ *= 1.1D;
//        }
//
//        this.motionX *= 0.9599999785423279D;
//        this.motionY *= 0.9599999785423279D;
//        this.motionZ *= 0.9599999785423279D;
//
//        if (this.onGround)
//        {
//            this.motionX *= 0.699999988079071D;
//            this.motionZ *= 0.699999988079071D;
//        }
    }
}
