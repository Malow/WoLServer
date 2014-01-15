import MaloW.NetworkChannel;
import MaloW.NetworkServer;


public class ConnectionListener extends NetworkServer
{
	LogicClass lc = new LogicClass();

	public ConnectionListener(int port)
	{
		super(port);
		lc.Start();
	}

	@Override
	public void ClientConnected(NetworkChannel nc)
	{
		lc.addClient(nc);
	}

}
