package utility;

import java.sql.SQLException;

public class PersistentConnection extends Thread 
{
	private Database database;
	
	public PersistentConnection(Database database)
	{
		this.database = database;
	}
	
	public void run()
	{
		while(true)
		{
			
			try {
				database.query("SELECT 1");
			} catch (SQLException e) {
				try {
					database.reconnect();
					System.out.println("Reconnected");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
