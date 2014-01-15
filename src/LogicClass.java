import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import MaloW.NetworkChannel;
import MaloW.NetworkPacket;
import MaloW.Process;
import MaloW.ProcessEvent;

public class LogicClass extends Process
{
	List<NetworkChannel> myClients = new ArrayList<NetworkChannel>();
	WoLSender myWoL = new WoLSender();

	public void addClient(NetworkChannel nc)
	{
		nc.SetNotifier(this);
		nc.Start();
		myClients.add(nc);
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
				if(msg.equals("WOL"))
				{
					myWoL.sendWoL();
				}
			}
		}
	}

}
