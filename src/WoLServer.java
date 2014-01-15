import MaloW.NetworkServer;


public class WoLServer
{
	public static void main(String[] args)
	{
		NetworkServer ns = new ConnectionListener(8888);
		ns.Start();
		ns.WaitUntillDone();
	}
}
