package _bau5.alptraum.utility;

import net.minecraft.src.Entity;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelDiscoverer extends ModelBase
{
	ModelRenderer Base1;
	ModelRenderer Base2;
	ModelRenderer Stem;
	ModelRenderer arm1;
	ModelRenderer arm2;
	ModelRenderer arm3;
	ModelRenderer arm4;
	ModelRenderer rotater1;
	ModelRenderer rotater2;
	ModelRenderer rotater3;
	ModelRenderer rotater4;
	  
	public ModelDiscoverer()
	{
		textureWidth = 64;
		textureHeight = 32;
	    
	    Base1 = new ModelRenderer(this, 0, 0);
	    Base1.addBox(0F, 0F, 0F, 16, 3, 16);
	    Base1.setRotationPoint(-8F, 21F, -8F);
	    Base1.setTextureSize(64, 32);
	    Base1.mirror = true;
	    setRotation(Base1, 0F, 0F, 0F);
	    Base2 = new ModelRenderer(this, 0, 0);
	    Base2.addBox(0F, 0F, 0F, 6, 1, 6);
	    Base2.setRotationPoint(-3F, 20F, -3F);
	    Base2.setTextureSize(64, 32);
	    Base2.mirror = true;
	    setRotation(Base2, 0F, 0F, 0F);
	    Stem = new ModelRenderer(this, 0, 0);
	    Stem.addBox(0F, 0F, 0F, 2, 6, 2);
	    Stem.setRotationPoint(-1F, 14F, -1F);
	    Stem.setTextureSize(64, 32);
	    Stem.mirror = true;
	    setRotation(Stem, 0F, 0F, 0F);
	    arm1 = new ModelRenderer(this, 0, 0);
	    arm1.addBox(0F, 0F, 0F, 2, 1, 1);
	    arm1.setRotationPoint(-3F, 14F, 0F);
	    arm1.setTextureSize(64, 32);
	    arm1.mirror = true;
	    setRotation(arm1, 0F, 0F, 0F);
	    arm2 = new ModelRenderer(this, 0, 0);
	    arm2.addBox(0F, 0F, 0F, 1, 1, 2);
	    arm2.setRotationPoint(0F, 14F, 1F);
	    arm2.setTextureSize(64, 32);
	    arm2.mirror = true;
	    setRotation(arm2, 0F, 0F, 0F);
	    arm3 = new ModelRenderer(this, 0, 0);
	    arm3.addBox(0F, 0F, 0F, 2, 1, 1);
	    arm3.setRotationPoint(1F, 14F, -1F);
	    arm3.setTextureSize(64, 32);
	    arm3.mirror = true;
	    setRotation(arm3, 0F, 0F, 0F);
	    arm4 = new ModelRenderer(this, 0, 0);
	    arm4.addBox(0F, 0F, 0F, 1, 1, 2);
	    arm4.setRotationPoint(-1F, 14F, -3F);
	    arm4.setTextureSize(64, 32);
	    arm4.mirror = true;
	    setRotation(arm4, 0F, 0F, 0F);
	    rotater1 = new ModelRenderer(this, 0, 0);
	    rotater1.addBox(-4F, 0F, 0F, 1, 1, 1);
	    rotater1.setRotationPoint(0F, 14F, 0F);
	    rotater1.setTextureSize(64, 32);
	    rotater1.mirror = true;
	    setRotation(rotater1, 0F, 0F, 0F);
	    rotater2 = new ModelRenderer(this, 0, 0);
	    rotater2.addBox(-1F, 0F, -4F, 1, 1, 1);
	    rotater2.setRotationPoint(0F, 14F, 0F);
	    rotater2.setTextureSize(64, 32);
	    rotater2.mirror = true;
	    setRotation(rotater2, 0F, 0F, 0F);
	    rotater3 = new ModelRenderer(this, 0, 0);
	    rotater3.addBox(0F, 0F, 3F, 1, 1, 1);
	    rotater3.setRotationPoint(0F, 14F, 0F);
	    rotater3.setTextureSize(64, 32);
	    rotater3.mirror = true;
	    setRotation(rotater3, 0F, 0F, 0F);
	    rotater4 = new ModelRenderer(this, 0, 0);
	    rotater4.addBox(3F, 0F, -1F, 1, 1, 1);
	    rotater4.setRotationPoint(0F, 14F, 0F);
	    rotater4.setTextureSize(64, 32);
	    rotater4.mirror = true;
	    setRotation(rotater4, 0F, 0F, 0F);
    }
	public void renderA()
	{
		float f = 0.0625f;
			
		Base1.render(f);
		Base2.render(f);
		Stem.render(f);
		arm1.render(f);
		arm2.render(f);
		arm3.render(f);
		arm4.render(f);
		rotater1.render(f);
		rotater2.render(f);
		rotater3.render(f);
		rotater4.render(f);
	}
	
	public void setRotation(float f)
	{
		rotater1.rotateAngleY = f;
		rotater2.rotateAngleY = f;
		rotater3.rotateAngleY = f;
		rotater4.rotateAngleY = f;
	}
	  
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5);
	    Base1.render(f5);
	    Base2.render(f5);
	    Stem.render(f5);
	    arm1.render(f5);
	    arm2.render(f5);
	    arm3.render(f5);
	    arm4.render(f5);
	    rotater1.render(f5);
	    rotater2.render(f5);
	    rotater3.render(f5);
	    rotater4.render(f5);
	}
	  
	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
	}
	 
	  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	  {
	    super.setRotationAngles(f, f1, f2, f3, f4, f5);
	  }

}
