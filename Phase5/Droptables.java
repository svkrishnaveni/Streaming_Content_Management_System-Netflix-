import java.io.IOException;
import java.sql.SQLException;

public class Droptables {	
	
	public static void main(String[] argv) throws IOException, SQLException 
	{	
		Datahandler.Connection();
		Datahandler.Droptables();	
		Datahandler.connection.close();
	}

}
