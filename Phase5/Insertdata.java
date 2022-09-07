import java.io.IOException;
import java.sql.SQLException;

public class Insertdata {
	
	public static void main(String[] argv) throws IOException, SQLException 
	{
	Datahandler.Connection();
	Datahandler.Insertrows();
	Datahandler.connection.close();
	}
}
