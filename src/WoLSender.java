import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class WoLSender
{
	private static final String MAC_ADDRESS = "00-25-22-B4-2C-D7";
	private static final int PORT = 9;
	private static final String IP_ADDRESS = "192.168.1.110";
	
	public static void sendWoL()
	{
		WoLSender wl = new WoLSender();
		wl.go(IP_ADDRESS, MAC_ADDRESS);
	}	
	
	private class WakeThread implements Runnable
	{
		String ipStr;
		String macStr;
		
		public WakeThread(String ip, String mac)
		{
			ipStr = ip;
			macStr = mac;
		}
		
		@Override
		public void run()
		{
			try
			{
				byte[] macBytes = getMacBytes(macStr);
				byte[] bytes = new byte[6 + 16 * macBytes.length];
				for (int i = 0; i < 6; i++)
				{
					bytes[i] = (byte) 0xff;
				}
				for (int i = 6; i < bytes.length; i += macBytes.length)
				{
					System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
				}

				InetAddress address = InetAddress.getByName(ipStr);
				DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, PORT);
				DatagramSocket socket = new DatagramSocket();
				socket.send(packet);
				socket.close();

				System.out.println("Wake-on-LAN packet sent.");
			} 
			catch (Exception e)
			{
				System.out.println("Failed to send Wake-on-LAN packet: " + e);
			}
		}
	}
	
	private void go(String ipStr, String macStr)
	{
		WakeThread wt = new WakeThread(ipStr, macStr);
		Thread t = new Thread(wt);
		t.start();
	}
	
	
	private static byte[] getMacBytes(String macStr) throws IllegalArgumentException
	{
		byte[] bytes = new byte[6];
		String[] hex = macStr.split("(\\:|\\-)");
		if (hex.length != 6)
		{
			throw new IllegalArgumentException("Invalid MAC address.");
		}
		try
		{
			for (int i = 0; i < 6; i++)
			{
				bytes[i] = (byte) Integer.parseInt(hex[i], 16);
			}
		} 
		catch (NumberFormatException e)
		{
			throw new IllegalArgumentException("Invalid hex digit in MAC address.");
		}
		return bytes;
	}
}
