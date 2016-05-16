package MovieCorner.mediator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientCommunicationThread extends Thread {
	private int port = 1337;
	private String host = "localhost";
	
	public ClientCommunicationThread(String host, int port)
	{
	   this.host = host;
	   this.port = port;
	}

	/**
	 * Handles executing commands that result in sending a packet to the server
	 * @param what The command to be executed
	 */
	public Object exec(String what, Object... args) throws IOException
	{
		ByteBuffer packetToSend = ByteBuffer.allocate(8);
		
		Object rObject = null;
		
		switch(what)
		{
		case "getAllItems":
			packetToSend.putInt(100); // Packet code for specific action
			rObject = sendPacket(packetToSend);
			break;
			
		case "login":
			String[] loginInfo = {(String) args[0], (String) args[1]};
			byte[] packetData = PacketUtilities.objectToBytes(loginInfo);
			
			packetToSend = PacketUtilities.constructPacket(packetData, 200);
			rObject = sendPacket(packetToSend);
			break;
			
		case "getAllUsers":
			packetToSend.putInt(300);
			rObject = sendPacket(packetToSend);
			break;
			
		case "saveUser":
			Object[] userInfo = {args[0], args[1]};
			byte[] userData = PacketUtilities.objectToBytes(userInfo);
			
			packetToSend = PacketUtilities.constructPacket(userData, 400);
			rObject = sendPacket(packetToSend);
			break;

		case "saveItem":
			byte[] itemData = PacketUtilities.objectToBytes(args[0]);
			
			packetToSend = PacketUtilities.constructPacket(itemData, 500);
			rObject = sendPacket(packetToSend);
			break;
			
		case "getComments":
			byte[] itemD = PacketUtilities.objectToBytes(args[0]);
			
			packetToSend = PacketUtilities.constructPacket(itemD, 600);
			rObject = sendPacket(packetToSend);
			
			break;
			
		case "addComment":
			Object[] commentInfo = {args[0], args[1], args[2]};
			
			byte[] commentData = PacketUtilities.objectToBytes(commentInfo);
			
			
			packetToSend = PacketUtilities.constructPacket(commentData, 700);
			rObject = sendPacket(packetToSend);
			break;
			
		case "addWatchedEpisode":
			Object[] watchedEpisodeInfo = {args[0], args[1]};
			
			byte[] watchedEpisodeData = PacketUtilities.objectToBytes(watchedEpisodeInfo);
			
			packetToSend = PacketUtilities.constructPacket(watchedEpisodeData, 800);
			rObject = sendPacket(packetToSend);
			break;
			
		case "addWatchedMovie":
			Object[] watchedMovieInfo = {args[0], args[1]};
			
			byte[] watchedMovieData = PacketUtilities.objectToBytes(watchedMovieInfo);
			
			packetToSend = PacketUtilities.constructPacket(watchedMovieData, 900);
			rObject = sendPacket(packetToSend);
			break;
			
		case "getWatchedEpisodes":
			byte[] pUser = PacketUtilities.objectToBytes(args[0]);
			
			packetToSend = PacketUtilities.constructPacket(pUser, 1000);
			rObject = sendPacket(packetToSend);
			break;
			
		case "getWatchedMovies":
			byte[] pUser2 = PacketUtilities.objectToBytes(args[0]);
			
			packetToSend = PacketUtilities.constructPacket(pUser2, 1100);
			rObject = sendPacket(packetToSend);
			break;
			
		case "addFavoriteTvShow":
			Object[] favoriteShowInfo = {args[0], args[1]};
			
			byte[] favoriteShowData = PacketUtilities.objectToBytes(favoriteShowInfo);
			
			packetToSend = PacketUtilities.constructPacket(favoriteShowData, 1200);
			rObject = sendPacket(packetToSend);
			break;
			
		case "addFavoriteMovie":
			Object[] favoriteMovieInfo = {args[0], args[1]};
			
			byte[] favoriteMovieData = PacketUtilities.objectToBytes(favoriteMovieInfo);
			
			packetToSend = PacketUtilities.constructPacket(favoriteMovieData, 1300);
			rObject = sendPacket(packetToSend);
			break;
			
		case "getFavoriteMovies":
			Object getFavoriteMoviesInfo = args[0];
			
			byte[] getFavoriteMovies = PacketUtilities.objectToBytes(getFavoriteMoviesInfo);
			
			packetToSend = PacketUtilities.constructPacket(getFavoriteMovies, 1400);
			rObject = sendPacket(packetToSend);
			break;
			
		case "getFavoriteShows":
			Object getFavoriteShowsInfo = args[0];
			
			byte[] getFavoriteShows = PacketUtilities.objectToBytes(getFavoriteShowsInfo);
			
			packetToSend = PacketUtilities.constructPacket(getFavoriteShows, 1500);
			rObject = sendPacket(packetToSend);
			break;
		case "addEpisode":
			Object[] episodeAndID= args;
			
			byte[] episodeAndIDSend= PacketUtilities.objectToBytes(episodeAndID);
			
			packetToSend= PacketUtilities.constructPacket(episodeAndIDSend, 1600);
			rObject= sendPacket(packetToSend);
			break;
		case "addEpisodeReview":
			Object review= args;
			
			byte[] reviewSend= PacketUtilities.objectToBytes(review);
			
			packetToSend= PacketUtilities.constructPacket(reviewSend,1700);
			rObject=sendPacket(packetToSend);
			break;
		case "addMovieReview":
			byte[] movieReviewSend= PacketUtilities.objectToBytes(args[0]);
			
			packetToSend= PacketUtilities.constructPacket(movieReviewSend, 1800);
			rObject=sendPacket(packetToSend);
			break;
			
		case "getEpisodeReviews":
			byte[] reviewEpisodeData = PacketUtilities.objectToBytes(args[0]);
			
			packetToSend = PacketUtilities.constructPacket(reviewEpisodeData, 1900);
			rObject = sendPacket(packetToSend);
			break;
			
		case "getMovieReviews":
			byte[] reviewMovieData = PacketUtilities.objectToBytes(args[0]);
			
			packetToSend = PacketUtilities.constructPacket(reviewMovieData, 2000);
			rObject = sendPacket(packetToSend);
			break;
	    case "removeComment":
	         Object[] commentInfoToRemove = {args[0], args[1]};
	         
	         byte[] commentDataToRemove = PacketUtilities.objectToBytes(commentInfoToRemove);
	         
	         packetToSend = PacketUtilities.constructPacket(commentDataToRemove, 2100);
	         rObject = sendPacket(packetToSend);
	         break;
	    case "removeUser":
          Object[] userInfoToRemove = {args[0]};
          
          byte[] userDataToRemove = PacketUtilities.objectToBytes(userInfoToRemove);
          
          packetToSend = PacketUtilities.constructPacket(userDataToRemove, 2200);
          rObject = sendPacket(packetToSend);
          break;
	    case "updateUser":
          Object[] userInfoToUpdate = {args[0], args[1], args[2], args[3]};
          
          byte[] userDataToUpdate = PacketUtilities.objectToBytes(userInfoToUpdate);
          
          packetToSend = PacketUtilities.constructPacket(userDataToUpdate, 2300);
          rObject = sendPacket(packetToSend);
          break;
	    case "manageItem":
	         byte[] updatedItemData = PacketUtilities.objectToBytes(args[0]);
	         
	         packetToSend = PacketUtilities.constructPacket(updatedItemData, 2400);
	         rObject = sendPacket(packetToSend);
	         break;
	    case "getFriendIDsByUserID":
          byte[] userID = PacketUtilities.objectToBytes(args[0]);
          
          packetToSend = PacketUtilities.constructPacket(userID, 2500);
          rObject = sendPacket(packetToSend);
          break;
	    case "addFriendForUserID":
	       Object[] userIDAndFriendIDinfo = {args[0], args[1]};
          
          byte[] userIDAndFriendIDdata = PacketUtilities.objectToBytes(userIDAndFriendIDinfo);
          
          packetToSend = PacketUtilities.constructPacket(userIDAndFriendIDdata, 2600);
          rObject = sendPacket(packetToSend);
          break;
		}
		
		return rObject;
	}
	
	/**
	 * Handles receiving packets and converting them to a ByteBuffer object
	 * @param socket The socket on which to receive the packet
	 * @return Returns the ByteBuffer object as the packet
	 */
	private ByteBuffer receivePacket(Socket socket)
	{
		try {
			InputStream inFromServer = socket.getInputStream();
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			int dataReceived;
			
			while((dataReceived = inFromServer.read()) != -1)
				out.write(dataReceived);

			ByteBuffer rPacket = ByteBuffer.wrap(out.toByteArray());
			
			return rPacket;
		} catch (SocketTimeoutException e)
		{
		   return null;
		} catch (IOException e) {
		   e.printStackTrace();
		}
		
		return null;
		
	}
	
	/**
	 * Handles sending a packet to the server using a Socket connection
	 * @param packet The packet that should be sent to the server
	 * @throws IOException 
	 */
	private Object sendPacket(ByteBuffer packet) throws IOException
	{
			Socket socket = new Socket(host, port);
			OutputStream outToServer = socket.getOutputStream();
			

			outToServer.write(packet.array());
			socket.shutdownOutput();
			
			ByteBuffer rPacket;
			if( (rPacket = receivePacket(socket)) != null )
			{
				return handlePacket(rPacket);
			}
		
		return null;
	}
	
	/**
	 * Handling received packets, performing an action depending on the packet header ID of the received packet.
	 * @param packet The packet that should be handled
	 */
	private Object handlePacket(ByteBuffer packet)
	{
		int packetID = packet.getInt();

		Object o = null;
		
		switch(packetID)
		{
		case 100: // Handler for getting all items
			o = PacketUtilities.packetToObject(packet);
			break;
			
			
		case 200: // Handler for logging in
			o = PacketUtilities.packetToObject(packet);
			break;

		case 300: // Handler for getting all users
			o = PacketUtilities.packetToObject(packet);
			break;
			
		case 400: // Handler for saving a user
			int actionCode = packet.getInt();
			
			if(actionCode==1)
				o = new Integer(1);
			else if(actionCode==2)
				o = new Integer(2);
			else
				o = new Integer(0);
			break;
			
		case 500: // Handler for saving an item
			int itemActionCode = packet.getInt();
			
			if(itemActionCode==1)
				o = new Integer(1);
			else if(itemActionCode==2)
				o = new Integer(2);
			else
				o = new Integer(0);
			break;
			
		case 600: // Handler for getting comments
			o = PacketUtilities.packetToObject(packet);
			break;
			
		case 700: // Handler for adding comments
			int itemActionCode2 = packet.getInt();
			
			if(itemActionCode2 == 2)
				o = new Integer(2);
			else
				o = new Integer(0);
			break;
			
		case 800:
			int itemActionCode3 = packet.getInt();
			
			if(itemActionCode3 == 2)
				o = new Integer(2);
			else
				o = new Integer(0);
			break;

		case 900:
			int itemActionCode4 = packet.getInt();
			
			if(itemActionCode4 == 2)
				o = new Integer(2);
			else
				o = new Integer(0);
			break;
			
		case 1000:
			o = PacketUtilities.packetToObject(packet);
			break;
			
		case 1100:
			o = PacketUtilities.packetToObject(packet);
			break;
			
		case 1200:
			int itemActionCode5 = packet.getInt();
			
			if(itemActionCode5 == 2)
				o = new Integer(2);
			else
				o = new Integer(0);
			break;

		case 1300:
			int itemActionCode6 = packet.getInt();
			
			if(itemActionCode6 == 2)
				o = new Integer(2);
			else
				o = new Integer(0);
			break;
			
		case 1400:
			o = PacketUtilities.packetToObject(packet);
			break;
			
		case 1500:
			o = PacketUtilities.packetToObject(packet);
			break;
		case 1600:
			int itemActionCode7= packet.getInt();
			
			
			
			if(itemActionCode7==2)
				o=new Integer(2);
			else
				o=new Integer(0);
			break;
		case 1700:
			int itemActionCode8= packet.getInt();
			
			if(itemActionCode8==2)
				o=new Integer(2);
			else
				o=new Integer(0);
			break;
		case 1800:
			int itemActionCode9= packet.getInt();
			
			if(itemActionCode9==2)
				o=new Integer(2);
			else
				o=new Integer(0);
			break;
		case 1900:
			o = PacketUtilities.packetToObject(packet);
			break;
		case 2000:
			o = PacketUtilities.packetToObject(packet);
			break;
		case 2100:
		   int itemActionCode10= packet.getInt();
         
         if(itemActionCode10==2)
            o=new Integer(2);
         else
            o=new Integer(0);
         break;
		case 2200:
         int itemActionCode11= packet.getInt();
         if(itemActionCode11==2)
            o=new Integer(2);
         else
            o=new Integer(0);
         break;
		case 2300:
         int itemActionCode12= packet.getInt();
         
         if(itemActionCode12==2)
            o=new Integer(2);
         else
            o=new Integer(0);
         break;
		case 2400:
         int itemActionCode13 = packet.getInt();
         
         if(itemActionCode13 == 1)
            o = new Integer(1);
         else
            o = new Integer(0);
         break;
		case 2500:
		   o = PacketUtilities.packetToObject(packet);
         break;
		case 2600:
         int itemActionCode15 = packet.getInt();
         
         if(itemActionCode15 == 1)
            o = new Integer(1);
         else
            o = new Integer(0);
         break;
      }
		
      if(packetID == 404)
         o = new Integer(404);
		
		return o;
	}
	
	
}
