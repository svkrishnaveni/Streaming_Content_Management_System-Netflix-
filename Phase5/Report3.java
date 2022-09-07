import java.io.IOException;
import java.sql.SQLException;

public class Report3 {
	
	public static void main(String[] argv) throws IOException, SQLException 
	{
		//Report 3--Identify the count of movies in a particular language--- with dynamic queries
	Datahandler.Connection();
	Datahandler.Language_movies_count();
	Datahandler.connection.close();
	}

}

