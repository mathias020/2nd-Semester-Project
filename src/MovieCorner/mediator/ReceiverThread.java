package MovieCorner.mediator;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

import MovieCorner.model.Client;

public class ReceiverThread extends Thread {
	private DataInputStream inFromClient;
	
	private ServerSocket wSocket;
	
	private ServerConnectionThread server;
	
	/**
	 * Creates the receiving thread for the server.<br />
	 * Handles receiving packets and uses a reference to the ServerConnectionThread to handle received packets.
	 * @param server An instance of the ServerConnectionThread
	 * @param wSocket The ServerSocket object
	 * @throws IOException Throws an IO Exception if socket fails.
	 */
	public ReceiverThread(ServerConnectionThread server, ServerSocket wSocket) throws IOException
	{
		this.wSocket = wSocket;
		this.server = server;
	}
	
	public void run()
	{
		while(true)
		{
			try {
				Socket cSocket = wSocket.accept();
				
				inFromClient = new DataInputStream(cSocket.getInputStream());
				
				SocketAddress socketAddr = cSocket.getRemoteSocketAddress();
				Client client = server.clientBySocketAddr(socketAddr);
				
				
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				
				int dataReceived;
				while((dataReceived = inFromClient.read()) != -1)
					out.write(dataReceived);
				
				
				if(client == null)
				{
					client = new Client(socketAddr, cSocket);
					server.addClient(client);
				}
				
				server.handlePacket(client, ByteBuffer.wrap(out.toByteArray()));
				
			} catch (SocketException e ) {
				/* ok */
			} catch ( Exception e )
			{
				e.printStackTrace();
			}
		}
	}
}
