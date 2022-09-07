import java.io.IOException;
import java.sql.SQLException;

public class Report4 {

	public static void main(String[] argv) throws IOException, SQLException 
	{
		//Report 4--Roll up--- with dynamic queries
	Datahandler.Connection();
	Datahandler.Rollup();
	Datahandler.connection.close();
	}
}
