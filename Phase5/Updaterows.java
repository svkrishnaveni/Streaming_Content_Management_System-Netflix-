import java.io.IOException;
import java.sql.SQLException;

public class Updaterows {

	public static void main(String[] argv) throws IOException, SQLException 
	{
	Datahandler.Connection();
	Datahandler.Updaterows();
	Datahandler.connection.close();
	}

}
