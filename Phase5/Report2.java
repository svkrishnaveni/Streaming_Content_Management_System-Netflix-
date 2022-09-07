import java.io.IOException;
import java.sql.SQLException;

public class Report2 {
	public static void main(String[] argv) throws IOException, SQLException 
	{
		// Report 2--Identify the movies in particular year --- with dynamic queries
	Datahandler.Connection();
	Datahandler.Movies_given_year();
	Datahandler.connection.close();
	}

}
