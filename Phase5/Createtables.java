import java.io.IOException;
import java.sql.SQLException;

public class Createtables {
	public static void main(String[] argv) throws IOException, SQLException 
	{	
		Datahandler.Connection();
		Datahandler.Createtables();	
		Datahandler.connection.close();
	}


}
