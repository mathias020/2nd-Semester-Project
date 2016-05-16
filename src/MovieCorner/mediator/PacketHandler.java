package MovieCorner.mediator;

import java.io.IOException;
import java.nio.ByteBuffer;

import MovieCorner.model.Client;

public interface PacketHandler {
	/**
	 * The handle packet method
	 * @param client The Client that sent the packet
	 * @param packet A packet of type ByteBuffer
	 * @throws IOException 
	 */
	public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException;
}
