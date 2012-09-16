package _bau5.alptraum.world;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;

public class EntityTorchRenderer extends RenderLiving
{

	public EntityTorchRenderer(ModelBase par1ModelBase, float par2) 
	{
		super(par1ModelBase, par2);
	}
	public void renderTorch(EntityTorch entitytorch, double d, double d1, double d2, float f, float f1)
	{
		super.doRenderLiving(entitytorch, d, d1, d2, f, f1);
	}
	public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) 
    { 
            renderTorch((EntityTorch)entityliving, d, d1, d2, f, f1); 
    } 

    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) 
    { 
            renderTorch((EntityTorch)entity, d, d1, d2, f, f1); 
    }

}
