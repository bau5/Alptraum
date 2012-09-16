package _bau5.alptraum.utility;

import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntitySpecialRenderer;

import org.lwjgl.opengl.GL11;

import _bau5.testing.ModelTesting3;

public class TileEntityDiscovererRenderer extends TileEntitySpecialRenderer
{
	public ModelTesting3 model;
	
	public TileEntityDiscovererRenderer()
	{
		model = new ModelTesting3();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f)
	{
		// TODO Auto-generated method stub
		if(tileentity instanceof TileEntity)
		{
			render((TileEntityDiscoverer)tileentity, d, d1, d2, f);
		}
		else
		{
			System.out.println("Wrong tile Entity");
		}
	}
	private void render(TileEntityDiscoverer entity, double x, double y, double z, float f)
	{
		bindTextureByName("/_bau5/t/b/nightmarecobble.png");
		GL11.glPushMatrix();
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float)x, (float)y + 2.0F, (float)z + 1.0F);
        GL11.glScalef(1.0F, -1F, -1F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        
        model.renderA();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.4F);
        model.setRotation(entity.angle);
//        model.setRotation(entity.angle);
//        model.render();
		
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}