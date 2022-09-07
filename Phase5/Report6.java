import java.io.IOException;
import java.sql.SQLException;

public class Report6 {
	public static void main(String[] argv) throws IOException, SQLException 
	{
		//Report 6 --Roll up2----with dynamic queries
	Datahandler.Connection();
	Datahandler.Rollup2();
	Datahandler.connection.close();
	}

}
