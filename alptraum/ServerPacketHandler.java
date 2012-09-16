package _bau5.alptraum;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ServerPacketHandler implements IPacketHandler
{

	@Override
	public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		 if (packet.channel.equals("_bau5Alptraum")) 
		  {
			  DataInputStream dataStream = new DataInputStream(new ByteArrayInputStream(packet.data));
			  EntityPlayer entityplayer = (EntityPlayer) player;
		      int id = -1;
		      try {
		    	  id = dataStream.readInt();
		                switch (id) {
		                case -1:
		                 break;
		                case 0:
		                 ItemStack stack;
		                 int slot = dataStream.readInt();
		                 int itemID = dataStream.readInt();
		                 if (itemID < 0) {
		                  stack = null;
		                 } else {
		                     int stacksize = dataStream.readInt();
		                     int damage = dataStream.readInt();
		                     boolean bool = dataStream.readBoolean();
		                     stack = new ItemStack(itemID, stacksize, damage);
		                     if (!bool) {
		                         NBTTagCompound tags = (NBTTagCompound) NBTTagCompound.readNamedTag(dataStream);
		                         stack.setTagCompound(tags);
		                     }
		                 }
		                 break;
		                }
		         } catch(IOException e) {
		                e.printStackTrace();
		         }
		  }
	}
}
