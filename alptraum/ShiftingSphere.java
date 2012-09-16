package _bau5.alptraum;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityChicken;
import net.minecraft.src.EntityCow;
import net.minecraft.src.EntityCreeper;
import net.minecraft.src.EntityPig;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.EntitySpider;
import net.minecraft.src.EntitySquid;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;

public class ShiftingSphere extends Item
{
	private String[] validEntities = 
		{
			"Sheep", "Pig", "Skeleton", "Zombie", "Creeper", "Cow", "Chicken", 
			"Spider", "Squid"
		};
	protected ShiftingSphere(int par1)
	{
		super(par1);
		setIconIndex(33);
		setMaxStackSize(1);
		setMaxDamage(100);
		this.setTabToDisplayOn(CreativeTabs.tabTools);
	}
	public String getTextureFile()
	{
		return Alptraum.textureFile;
	}
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) 
    {
		if(entity instanceof EntityLiving)
		{
			stack.setItemDamage(0);
			NBTTagCompound tag = stack.getTagCompound();
			if(tag == null)
			{
				tag = new NBTTagCompound();
			}
			String a = entity.getClass().toString();
			System.out.println(a);
			if(a.contains("EntitySheep"))
			{
				EntitySheep es = (EntitySheep) entity;
				System.out.println("true");
				tag.setInteger("extraInfo1", es.getFleeceColor());
			}
			if(a.contains("EntityCreeper"))
			{
				EntityCreeper e = (EntityCreeper) entity;
				System.out.println("true");
				tag.setBoolean("extraInfo1", e.getPowered());
			}
			
			entity.setDead();
			tag.setString("entityType", a);
			stack.setTagCompound(tag);
			stack.setItemDamage(0);
		}
        return true;
    }
	public boolean tryPlaceIntoWorld(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int par7, float par8, float par9, float par10)
    {
		if(itemstack.getTagCompound() != null && itemstack.getItemDamage() == 0 && !world.isRemote)
		{
			j++;
			String a = itemstack.getTagCompound().getString("entityType");
			if(a.equals(EntityCow.class.toString()))
			{
				EntityCow ec = new EntityCow(world);
				ec.setPosition(i, j, k);
				world.spawnEntityInWorld(ec);
			}
			if(a.equals(EntityPig.class.toString()))
			{
				EntityPig ep = new EntityPig(world);
				ep.setPosition(i, j, k);
				world.spawnEntityInWorld(ep);
			}
			if(a.equals(EntityChicken.class.toString()))
			{
				EntityChicken ec = new EntityChicken(world);
				ec.setPosition(i, j, k);
				world.spawnEntityInWorld(ec);
			}
			if(a.equals(EntitySheep.class.toString()))
			{
				EntitySheep es = new EntitySheep(world);
				es.setPosition(i, j, k);
				es.setFleeceColor(itemstack.getTagCompound().getInteger("extraInfo1"));
				world.spawnEntityInWorld(es);
				System.out.println(itemstack.getTagCompound().getInteger("extraInfo1"));
			}
			if(a.equals(EntityCreeper.class.toString()))
			{
				EntityCreeper e = new EntityCreeper(world);
				e.setPosition(i, j, k);
				System.out.println(itemstack.getTagCompound().getBoolean("extraInfo1"));
				if(itemstack.getTagCompound().getBoolean("extraInfo1"))
				{
					e.getDataWatcher().updateObject(17, Byte.valueOf((byte)1));
				}
				world.spawnEntityInWorld(e);
			}
			if(a.equals(EntitySkeleton.class.toString()))
			{
				EntitySkeleton e = new EntitySkeleton(world);
				e.setPosition(i, j, k);
				world.spawnEntityInWorld(e);
			}
			if(a.equals(EntityZombie.class.toString()))
			{
				EntityZombie e = new EntityZombie(world);
				e.setPosition(i, j, k);
				world.spawnEntityInWorld(e);
			}
			if(a.equals(EntitySpider.class.toString()))
			{
				EntitySpider e = new EntitySpider(world);
				e.setPosition(i, j, k);
				world.spawnEntityInWorld(e);
			}
			if(a.equals(EntitySquid.class.toString()))
			{
				EntitySquid e = new EntitySquid(world);
				e.setPosition(i, j, k);
				world.spawnEntityInWorld(e);
			}
			itemstack.setTagCompound(null);
			itemstack.setItemDamage(100);
		}
		return true;
	}

}
