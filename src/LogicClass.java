
import java.util.ArrayList;
import java.util.List;

import MaloW.NetworkChannel;
import MaloW.NetworkPacket;
import MaloW.Process;
import MaloW.ProcessEvent;

public class LogicClass extends Process
{
	List<NetworkChannel> myClients = new ArrayList<NetworkChannel>();

	public void addClient(NetworkChannel nc)
	{
		nc.SetNotifier(this);
		nc.Start();
		myClients.add(nc);
		nc.SendData("Welcome, this is server.");
	}

	@Override
	public void Life()
	{
		while(this.stayAlive)
		{
			ProcessEvent ev = this.WaitEvent();
			if(ev instanceof NetworkPacket)
			{
				NetworkPacket np = (NetworkPacket)ev;
				String msg = np.GetMessage();
				if(msg.equals("WoL"))
				{
					WoLSender.sendWoL();
				}
				
				
				for(NetworkChannel nc: myClients)
				{
					if(nc.GetChannelID() == np.GetSenderID())
					{
						nc.SendData("Received msg: " + msg);
					}
				}
			}
		}
	}

}
