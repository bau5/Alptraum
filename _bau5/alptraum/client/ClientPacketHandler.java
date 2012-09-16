package _bau5.alptraum.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import _bau5.alptraum.utility.TileEntityDiscoverer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class ClientPacketHandler implements IPacketHandler
{
	public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player) 
	{
		
	}


	public static void syncStack(TileEntity te, ItemStack stack, int slot) 
	{
		if(te instanceof TileEntityDiscoverer)
		{
			 ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		        DataOutputStream data = new DataOutputStream(bytes);
		        try {
		         data.writeInt(0);
		         data.writeInt(slot);
		         if (stack == null) {
		          data.writeInt(-1);
		         } else {
		             data.writeInt(stack.itemID);
		             data.writeInt(stack.stackSize);
		             data.writeInt(stack.getItemDamage());
		             data.writeBoolean(stack.getTagCompound() == null);
		             if (stack.getTagCompound() != null) {
		                 stack.getTagCompound().writeNamedTag(stack.getTagCompound(), data);
		             }
		         }
		        } catch(IOException e) {
		            e.printStackTrace();
		        }

		        Packet250CustomPayload packet = new Packet250CustomPayload();
		        packet.channel = "_bau5Alptraum";
		        packet.data = bytes.toByteArray();
		        packet.length = packet.data.length;
		        
		        PacketDispatcher.sendPacketToServer(packet);
		}
	}

}
