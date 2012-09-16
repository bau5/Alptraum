package _bau5.alptraum.world;

import java.util.Iterator;

import _bau5.alptraum.Alptraum;

import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityTorch extends EntityLiving
{
	private double originX;
	private double originY;
	private double originZ;
	private boolean wontDie;
	private int life;
	private int age;
	private boolean hitPlayer;
	
	public EntityTorch(World par1World, double par1, double par2, double par3, int lifeExpectancy) 
	{
		super(par1World);
		this.setPosition(par1 +.5, par2, par3 +.5);
		this.setSize(.5F, 1F);
		originX = par1;
		originY = par2;
		originZ = par3;
		texture = Alptraum.baseTextureFile +"/Entities/EntityTorch.png";
		hitPlayer = false;
		wontDie = false;
		if(lifeExpectancy == -1)
		{
			wontDie = true;
		}
		life = lifeExpectancy;
		if(par1World != null)
		{
			par1World.playSoundAtEntity(this, "mods.Alptraum.insanity", 1F, 1F);
		}
	}
	public boolean canBeCollidedWith()
	{
		return false; 	
	}
	public boolean canBePushed()
	{
		return false;
	}
	public float getShadowSize()
    {
        return 3.0F;
    }
	public void tryReplaceTorch()
	{
        if(worldObj.getClosestPlayer(originX, originY, originZ, 10D) != null)
        {
        	return;
        }
        if(worldObj.isRemote)
        {
        	worldObj.setBlockWithNotify((int)originX, (int)originY, (int)originZ, Block.torchWood.blockID);
        	System.out.println("Resetting torch.");
        }
        setDead();
        Alptraum.logP("Deaddd");
	}
	public void onUpdate()
	{
		if(wontDie || !hitPlayer)
		{
			return;
		}
		age = age + 1;
		if(age >= life && !wontDie)
		{
			tryReplaceTorch();
		}
		double d = 8D;
        EntityPlayer entityplayer = Alptraum.mc.thePlayer;
        if(entityplayer != null && worldObj != null)
        {
        	entityplayer = worldObj.getClosestPlayerToEntity(this, d);
        }
        if (entityplayer != null && !hitPlayer)
        {
            double d1 = (entityplayer.posX - posX) / d;
            double d2 = ((entityplayer.posY + (double)entityplayer.getEyeHeight()) - posY) / d;
            double d3 = (entityplayer.posZ - posZ) / d;
            double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
            double d5 = 1.0D - d4;

            if (d5 > 0.0D)
            {
                d5 *= d5;
                motionX += (d1 / d4) * d5 * 0.10000000000000001D;
                motionY += (d2 / d4) * d5 * 0.10000000000000001D;
                motionZ += (d3 / d4) * d5 * 0.10000000000000001D;
            }
            moveEntity(motionX /4, motionY /4, motionZ /4);
        }
        else if (entityplayer != null && hitPlayer)
        {
        	 double d1 = (entityplayer.posX - posX) / d;
             double d2 = ((entityplayer.posY + (double)entityplayer.getEyeHeight()) - posY) / d;
             double d3 = (entityplayer.posZ - posZ) / d;
             double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
             double d5 = 1.0D - d4;

             if (d5 > 0.0D)
             {
                 d5 *= d5;
                 motionX += (d1 / d4) * d5 * 0.10000000000000001D;
                 motionY += (d2 / d4) * d5 * 0.10000000000000001D;
                 motionZ += (d3 / d4) * d5 * 0.10000000000000001D;
             }
             moveEntity(-motionX /4, -motionY /4, -motionZ /4);
        }
        else if(hitPlayer)moveEntity(0, motionY += 0.100000000000001D, 0);
        
	}
	public void onCollideWithPlayer(EntityPlayer entityplayer)
    {		
		hitPlayer = true;
    }
	@Override
	protected void entityInit()
    {
       super.entityInit();
    }
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setInteger("life", life);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
    	life = par1NBTTagCompound.getInteger("life");
    }
    public void performHurtAnimation()
    {
    	return;
    }
    public String getHurtSound()
    {
		return null;
    }
	public int getMaxHealth() 
	{
		return 4;
	}

}
