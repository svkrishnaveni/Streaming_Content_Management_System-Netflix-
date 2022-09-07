
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Datahandler {

	public static Connection connection;

//Method for JDBC connection
	public static void Connection() {
		System.out.println("-------- Oracle JDBC Connection Testing ------");

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			return;
		}

		System.out.println("Oracle JDBC Driver Registered!");

		try {
			// below include your login and your password
			connection = DriverManager.getConnection("jdbc:oracle:thin:@acaddbprod-2.uta.edu:1523/pcse1p.data.uta.edu",
					"sxd3125", "Dbsqlplus1234");
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;

		}

		if (connection != null) {
			System.out.println("JDBC Connection successful\n");
		} else {
			System.out.println("Failed to make connection!");
		}

	}

//Method for dropping  tables
	public static void Droptables() throws SQLException, IOException {
		try {

			Reader readerdrop = new BufferedReader(
					new FileReader("D:\\Fall 2021\\CSE 5330 Database Systems\\Project\\Phase 4\\myDBdrop.sql"));
			ScriptRunner sr = new ScriptRunner(connection, true, false);
			DatabaseMetaData dbm = connection.getMetaData();

			// Dropping tables
			sr.runScript(readerdrop);
			System.out.println("Tables dropped successfully");
		}

		catch (SQLException e) {
			System.out.println("error in dropping tables");
			e.printStackTrace();
			return;

		}
	}

//Method for creating tables
	public static void Createtables() throws SQLException, IOException {
		try {

			Reader readercreate = new BufferedReader(
					new FileReader("D:\\Fall 2021\\CSE 5330 Database Systems\\Project\\Phase 4\\myDBcreate.sql"));
			ScriptRunner sr = new ScriptRunner(connection, true, false);
			DatabaseMetaData dbm = connection.getMetaData();

			// Creating tables
			sr.runScript(readercreate);
			System.out.println("Tables created successfully");

		}

		catch (SQLException e) {
			System.out.println("error in dropping tables");
			e.printStackTrace();
			return;

		}
	}

//Method for Inserting rows in  tables
	public static void Insertrows() throws SQLException, IOException {
		try {
			ScriptRunner sr = new ScriptRunner(connection, true, false);
			Reader readerinsert = new BufferedReader(
					new FileReader("D:\\Fall 2021\\CSE 5330 Database Systems\\Project\\Phase 4\\myDBinsert.sql"));
			// Inserting rows in tables
			sr.runScript(readerinsert);
			System.out.println("Rows inserted in Tables successfully\n");
		} catch (SQLException e) {
			System.out.println("error in inserting rows in tables");
			e.printStackTrace();
			return;

		}

	}

//Method for updating rows in  tables
	public static void Updaterows() throws SQLException, IOException {
		try {
			String sql = "UPDATE F21_S001_6_MOVIES " + "SET genre = 'Action'"
					+ "WHERE MOVIEID = 'm005' OR MOVIEID = 'm002' OR MOVIEID = 'm012'";
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Rows updated successfully");
			System.out.println("After update rows in Movies table are:\n ");
			ResultSet rs = stmt.executeQuery("SELECT movieid,movie_name,genre FROM F21_S001_6_MOVIES");
			System.out.println("MOVIEID" + "\t " + ("GENRE") + "\t" + ("MOVIE_NAME"));
			while (rs.next()) {
				System.out.println(
						rs.getString("movieid") + "\t" + rs.getString("genre") + "\t" + rs.getString("movie_name"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println("error in updating rows in tables");
			e.printStackTrace();
			return;

		}
	}

//Method to Report 1--Identify the most liked movie by the viewers --- with dynamic queries
	public static void MostLikedMoview() throws SQLException {

		String select_stmt;
		PreparedStatement ps = null;
		Scanner myObj = new Scanner(System.in);

		select_stmt = "SELECT MOVIEID,MOVIE_NAME FROM F21_S001_6_MOVIES WHERE MOVIEID IN (SELECT MOVIEID FROM F21_S001_6_STREAMING_ACTIVITY WHERE REVIEW = :search_cond"
				+ "	GROUP BY MOVIEID" + "	HAVING COUNT(*) = (SELECT MAX(COUNT(*)) FROM F21_S001_6_STREAMING_ACTIVITY"
				+ "	WHERE REVIEW =:search_cond" + "	GROUP BY MOVIEID))";

		ps = connection.prepareStatement(select_stmt);
		System.out.println("Enter a search condition for where--Review based on following options \n"
				+ "1.Type Like to find most liked movie \n" + "2.Type Dislike to find most disliked movie\n"
				+ "3.Type Null to find the most movie where user did nothing\n");
		String search_cond = myObj.nextLine();
		ps.setNString(1, search_cond);
		ps.setNString(2, search_cond);
		ResultSet rs = ps.executeQuery();
		System.out.println("MOVIEID" + "\t " + "MOVIE_NAME");
		while (rs.next()) {
			System.out.println(rs.getString("MOVIEID") + "\t" + rs.getString("MOVIE_NAME"));
		}
		rs.close();
		ps.close();
	}

//Method to Report 2--Identify the movies in particular year --- with dynamic queries
	public static void Movies_given_year() throws SQLException {

		String select_stmt = null;
		PreparedStatement ps = null;
		Scanner myObj = new Scanner(System.in);
		System.out.println("Order by based on following options \n" + "1.order by MOVIEID \n"
				+ "2.order by RELEASE_DATE \n" + "3.order by MOVIE_NAME \n");
		int orderby_choice = Integer.parseInt(myObj.nextLine());
		System.out.println("Enter a search condition for where--Release date based on following options \n"
				+ "1.Get movies released in 2020 \n" + "2.Get movies released in 2019 \n");
		int search_cond = Integer.parseInt(myObj.nextLine());

		switch (orderby_choice) {
		case 1:
			if (search_cond == 1) {
				select_stmt = "SELECT MOVIEID,RELEASE_DATE,MOVIE_NAME FROM F21_S001_6_MOVIES WHERE RELEASE_DATE LIKE '%20' ORDER BY MOVIEID";
				ps = connection.prepareStatement(select_stmt);
			} else if (search_cond == 2) {
				select_stmt = "SELECT MOVIEID,RELEASE_DATE,MOVIE_NAME FROM F21_S001_6_MOVIES WHERE RELEASE_DATE LIKE '%19' ORDER BY MOVIEID";
				ps = connection.prepareStatement(select_stmt);
			}
			break;

		case 2:
			if (search_cond == 1) {
				select_stmt = "SELECT MOVIEID,RELEASE_DATE,MOVIE_NAME FROM F21_S001_6_MOVIES WHERE RELEASE_DATE LIKE '%20' ORDER BY RELEASE_DATE";
				ps = connection.prepareStatement(select_stmt);
			} else if (search_cond == 2) {
				select_stmt = "SELECT MOVIEID,RELEASE_DATE,MOVIE_NAME FROM F21_S001_6_MOVIES WHERE RELEASE_DATE LIKE '%19' ORDER BY RELEASE_DATE";
				ps = connection.prepareStatement(select_stmt);
			}
			break;

		case 3:
			if (search_cond == 1) {
				select_stmt = "SELECT MOVIEID,RELEASE_DATE,MOVIE_NAME FROM F21_S001_6_MOVIES WHERE RELEASE_DATE LIKE '%20' ORDER BY MOVIE_NAME";
				ps = connection.prepareStatement(select_stmt);
			} else if (search_cond == 2) {
				select_stmt = "SELECT MOVIEID,RELEASE_DATE,MOVIE_NAME FROM F21_S001_6_MOVIES WHERE RELEASE_DATE LIKE '%19' ORDER BY MOVIE_NAME";
				ps = connection.prepareStatement(select_stmt);
			}
			break;

		}
		ResultSet rs = ps.executeQuery();
		System.out.println("MOVIEID" + "\t " + "RELEASE_DATE" + "\t" + "MOVIE_NAME");
		while (rs.next()) {
			System.out.println(
					rs.getString("MOVIEID") + "\t" + rs.getDate("RELEASE_DATE") + "\t" + rs.getString("MOVIE_NAME"));
		}
		rs.close();
		ps.close();
	}

//Method to Report 3--Identify the count of movies in a particular language--- with dynamic queries
	public static void Language_movies_count() throws SQLException {

		String select_stmt = null;
		PreparedStatement ps = null;
		Scanner myObj = new Scanner(System.in);
		System.out.println("Select Audio language based on following options \n"
				+ "1.Type Yes for audio present in that language \n"
				+ "2.Type No for audio not present in that language \n");
		String Audio_choice = myObj.nextLine();
		System.out.println("Select Subtitle language based on following options \n"
				+ "1.Type Yes for subtitles present in that language \n"
				+ "2.Type No for subtitles not present in that language \n");
		String Subtitle_choice = myObj.nextLine();

		System.out.println("Enter any number for having condition:\n");
		String having_cond = myObj.nextLine();

		select_stmt = "SELECT lm.language_name,count(lm.movieid) as TOT FROM F21_S001_6_LANGUAGE_MOVIES lm,F21_S001_6_LANGUAGE l\r\n"
				+ "WHERE l.language_name = lm.language_name\r\n"
				+ "AND l.audio_language =:Audio_choice and l.subtitle_language = :Subtitle_choice\r\n"
				+ "GROUP BY lm.language_name\r\n" + "HAVING count(lm.movieid) > :having_cond\r\n"
				+ "ORDER BY lm.language_name";

		ps = connection.prepareStatement(select_stmt);
		ps.setNString(1, Audio_choice);
		ps.setNString(2, Subtitle_choice);
		ps.setNString(3, having_cond);
		ResultSet rs = ps.executeQuery();
		System.out.println("language_name" + "\t " + "movieid");
		while (rs.next()) {
			System.out.println(rs.getString("language_name") + "\t\t\t" + rs.getInt("TOT"));
		}
		rs.close();
		ps.close();
	}

//Method to Report 4--Roll up--- with dynamic queries
	public static void Rollup() throws SQLException {
		String select_stmt;
		PreparedStatement ps = null;
		Scanner myObj = new Scanner(System.in);
		System.out.println("Enter search condition for where---activity type based on following options \n"
				+ "1.Type Watching \n" + "2.Type Login \n" + "3.Type logout \n");
		String search_cond = myObj.nextLine();
		select_stmt = "SELECT S.USERID,S.MOVIEID,COUNT(S.USERID) as TOT FROM F21_S001_6_STREAMING_ACTIVITY S \r\n"
				+ "WHERE S.USERID IN (SELECT USERID FROM F21_S001_6_ACTIVITY_LOG A WHERE A.ACTIVITY_TYPE = :search_cond) \r\n"
				+ "GROUP BY ROLLUP(S.MOVIEID,S.USERID)";
		ps = connection.prepareStatement(select_stmt);
		ps.setNString(1, search_cond);
		ResultSet rs = ps.executeQuery();
		System.out.println("USERID" + "\t " + "MOVIEID" + "\t " + "TOT" + "\t ");
		while (rs.next()) {
			System.out.println(rs.getString("USERID") + "\t" + rs.getString("MOVIEID") + "\t" + rs.getInt("TOT"));
		}
		rs.close();
		ps.close();
	}

//Method to Report 5--Cube--- with dynamic queries
	public static void Cube() throws SQLException {
		String select_stmt;
		PreparedStatement ps = null;
		Scanner myObj = new Scanner(System.in);
		System.out.println("Enter search condition for where---activity type based on following options \n"
				+ "1.Type Watching \n" + "2.Type Login \n" + "3.Type logout \n");
		String search_cond = myObj.nextLine();
		select_stmt = "SELECT S.USERID,S.MOVIEID,COUNT(S.USERID) as TOT FROM F21_S001_6_STREAMING_ACTIVITY S \r\n"
				+ "WHERE S.USERID IN (SELECT USERID FROM F21_S001_6_ACTIVITY_LOG A WHERE A.ACTIVITY_TYPE = :search_cond)\r\n"
				+ "GROUP BY CUBE(S.MOVIEID,S.USERID)";
		ps = connection.prepareStatement(select_stmt);
		ps.setNString(1, search_cond);
		ResultSet rs = ps.executeQuery();
		System.out.println("USERID" + "\t " + "MOVIEID" + "\t " + "TOT" + "\t ");
		while (rs.next()) {
			System.out.println(rs.getString("USERID") + "\t" + rs.getString("MOVIEID") + "\t" + rs.getInt("TOT"));
		}
		rs.close();
		ps.close();
	}

//Method to Report 6 --Roll up2----with dynamic queries
	public static void Rollup2() throws SQLException
	{
		
		String select_stmt;
		PreparedStatement ps = null;
		Scanner myObj = new Scanner(System.in); 			
		
	    System.out.println("Find user count based on following options \n"
	    		+ "1. User count by city \n"
	    		+ "2. User count by state \n" 
	    		+ "3. User count by country \n"
	    		+ "4. User count by city and state \n"
	    		+ "5. User count by city and country \n"
	    		+ "6. User count by state and country \n"
	    		+ "7. User count by city, state and country \n"
	    );
		
		int cubeChoice = Integer.parseInt(myObj.nextLine());
		
		String options = null;
		
		
			
		switch(cubeChoice) {
		case 1: options = "CITY";
		break;
		case 2: options = "STATE";
		break;
		case 3: options = "COUNTRY";
		break;
		case 4: options = "CITY,STATE";
		break;
		case 5: options = "CITY, COUNTRY";
		break;
		case 6: options = "COUNTRY,STATE,";
		break;
		case 7:
		default: options = "CITY,STATE,COUNTRY";
		
		}

		select_stmt = "SELECT "+ options +",COUNT(USERNAME) FROM F21_S001_6_USERS GROUP BY CUBE("+ options +")";
				
		ps = connection.prepareStatement(select_stmt);
		
			
	    ResultSet rs = ps.executeQuery();
	    System.out.println("List of column names in the current table:\n ");
	    ResultSetMetaData rsMetaData = rs.getMetaData();

	    int count = rsMetaData.getColumnCount();
	    for(int i = 1; i<=count; i++) {
	      System.out.println(rsMetaData.getColumnName(i));
	    }
	   
	    switch(cubeChoice) {
		case 1: System.out.println("CITY");
			while(rs.next())
	    {
	   	 System.out.println(rs.getString("CITY")+"\t\t"+rs.getString("COUNT(USERNAME)"));
	    }
		break;
		case 2: System.out.println("STATE");
			while(rs.next())
	    {
	   	 System.out.println(rs.getString("STATE")+"\t\t"+rs.getString("COUNT(USERNAME)"));
	    }
		break;
		case 3:System.out.println("COUNTRY");
			while(rs.next())
	    {
	   	 System.out.println(rs.getString("COUNTRY")+"\t\t"+rs.getString("COUNT(USERNAME)"));
	    }
		break;
		case 4: System.out.println("CITY"+"\t " + "STATE");
			while(rs.next())
	    {
	   	 System.out.println(rs.getString("CITY")+"\t\t"+rs.getString("STATE")+"\t\t"+rs.getString("COUNT(USERNAME)"));
	    }
		break;
		case 5:System.out.println("CITY"+"\t "+"COUNTRY"+"\t ");
			while(rs.next())
	    {
	   	 System.out.println(rs.getString("CITY")+"\t\t"+rs.getString("COUNTRY")+"\t\t"+rs.getString("COUNT(USERNAME)"));
	    }
		break;
		case 6: while(rs.next())
	    {
	   	 System.out.println(rs.getString("STATE")+"\t\t"+rs.getString("COUNTRY")+"\t\t"+rs.getString("COUNT(USERNAME)"));
	    }
		break;
		case 7:
		default: while(rs.next())
	    {
	   	 System.out.println(rs.getString("CITY")+"\t\t"+rs.getString("STATE")+"\t\t"+rs.getString("COUNTRY")+"\t\t"+rs.getString("COUNT(USERNAME)"));
	    }
		
		}
	    
	        rs.close();
	        ps.close();
	   } 

}
