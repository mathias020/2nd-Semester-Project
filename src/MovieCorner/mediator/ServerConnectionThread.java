package MovieCorner.mediator;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import MovieCorner.model.Client;

public class ServerConnectionThread extends Thread {
   private Map<Integer, PacketHandler> packetHandlers;

   private List<Client> clients;

   private ExecutorService tPool;

   private MovieCornerModel model;

   private int port;
   
   /**
    * The ServerConnectionThread works as the Server-instance.<br />
    * This class takes care of executing packet handlers and creates threads for
    * receiving and sending packets.
    */
   public ServerConnectionThread(MovieCornerModel model, int port) {
      packetHandlers = new HashMap<>();

      clients = new CopyOnWriteArrayList<Client>();

      tPool = Executors.newCachedThreadPool();

      this.model = model;
      this.port = port;
   }

   /**
    * Adds a client to the list of clients
    * 
    * @param client
    *           The client object to add
    */
   public void addClient(Client client) {
      if (client != null) clients.add(client);
   }

   /**
    * This method handles executing the handlers appropriate for the individual
    * packets.<br />
    * If no handler for a received packet is found, the packet will be
    * discarded.<br />
    * 
    * @param client
    *           The client who sent the packet
    * @param packet
    *           The packet that was received
    */
   public void handlePacket(Client client, ByteBuffer packet) {
      tPool.execute(() -> {
         int packetID = packet.getInt();

         if (!packetHandlers.containsKey(packetID)) {
            System.out.println("No handler found... packet discarded.");
            ByteBuffer ackPacket = ByteBuffer.allocate(4);

            ackPacket.putInt(404);
            client.enqueuePacket(ackPacket);
         } else {
            try {
               packetHandlers.get(packetID).handlePacket(client, packet, model);
            }
            catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   /**
    * Get a client by the SocketAddress
    * 
    * @param socketAddr
    *           The socket address for the user that should be looked for
    * @return If found a Client-object for the user is returned, otherwise null
    *         is returned
    */
   public Client clientBySocketAddr(SocketAddress socketAddr) {
      for (Client client : clients) {
         if (client.getSocketAddr().equals(socketAddr)) return client;
      }

      return null;
   }

   /**
    * Adds a handler to the map of handlers.
    * 
    * @param handlerID
    *           The ID of the handler
    * @param handler
    *           An instance of the PacketHandler interface
    */
   public void addHandler(int handlerID, PacketHandler handler) {
      if (handler != null) packetHandlers.put(handlerID, handler);
   }

   /**
    * Gets a specific handler by the ID
    * 
    * @param handlerID
    *           The ID of the handler
    * @return
    */
   public PacketHandler getHandler(int handlerID) {
      return packetHandlers.get(handlerID);
   }

   public void run() {
      try {
         ServerSocket wSocket = new ServerSocket(port);

         ReceiverThread commThread = new ReceiverThread(this, wSocket);

         Thread senderThread = new Thread(() -> {
            while (true) {
               for (Client client : clients) {

                  ByteBuffer packetToSend = client.nextPacket();
                  Socket clientSocket = client.getClientSocket();

                  if (packetToSend != null) {
                     try {
                        DataOutputStream outToClient = new DataOutputStream(
                              clientSocket.getOutputStream());
                        byte[] data = packetToSend.array();

                        outToClient.write(data, 0, data.length);
                        clientSocket.shutdownOutput();
                     }
                     catch (Exception e) {
                        e.printStackTrace();
                     }

                     if (client.packetsRemaining() == 0) // No more packets for
                                                         // this client - remove
                                                         // client record on
                                                         // server
               clients.remove(client);
            }

         }
      }
   }     );

         commThread.start();
         senderThread.start();

      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }
}
