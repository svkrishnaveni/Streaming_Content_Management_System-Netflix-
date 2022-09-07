import java.io.IOException;
import java.sql.SQLException;

public class Report1 {
	public static void main(String[] argv) throws IOException, SQLException 
	{
		// Report 1--Identify the most liked movie by the viewers --- with dynamic queries
	Datahandler.Connection();
	Datahandler.MostLikedMoview();
	Datahandler.connection.close();
	}

}
