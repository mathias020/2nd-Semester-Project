package MovieCorner.model;

import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

import utility.collection.LinkedQueue;
import utility.collection.QueueADT;


public class Client {
	private SocketAddress socketAddr;
	private QueueADT<ByteBuffer> packetQueue;
	private Socket clientSocket;
	
	/**
	 * Client class holding information about a specific client
	 * @param socketAddr The Socket Address of the client
	 * @param clientSocket The Socket for the client
	 */
	public Client(SocketAddress socketAddr, Socket clientSocket)
	{
		this.socketAddr = socketAddr;
		this.clientSocket = clientSocket;
		packetQueue = new LinkedQueue<ByteBuffer>();
	}
	
	/**
	 * Adds a packet to the queue of packets to be sent to this client
	 * @param packet The packet to enqueue
	 */
	public void enqueuePacket(ByteBuffer packet)
	{
		packetQueue.enqueue(packet);
	}
	
	/**
	 * Gets and removes the next packet from the queue
	 * @return Returns the front packet in the queue
	 */
	public ByteBuffer nextPacket()
	{
		return packetQueue.dequeue();
	}
	
	/**
	 * 
	 * @return Returns the socket address
	 */
	public SocketAddress getSocketAddr()
	{
		return socketAddr;
	}
	
	/**
	 * 
	 * @return Returns the client socket 
	 */
	public Socket getClientSocket()
	{
		return clientSocket;
	}
	
	/**
	 * 
	 * @return Returns the amount of packets in the queue
	 */
	public int packetsRemaining() {
		return packetQueue.size();
	}
}
