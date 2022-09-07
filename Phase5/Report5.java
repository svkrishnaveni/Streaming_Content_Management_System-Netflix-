import java.io.IOException;
import java.sql.SQLException;

public class Report5 {
	public static void main(String[] argv) throws IOException, SQLException 
	{
		//Report 5--Cube--- with dynamic queries
	Datahandler.Connection();
	Datahandler.Cube();
	Datahandler.connection.close();
	}

}
