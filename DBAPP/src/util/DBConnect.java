package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import model.Credentials;

public class DBConnect {

	private static Connection dbConnection;
	
	public static void connectDB() throws SQLException
	{
		//connect to SQL Server using LibrarySystemLogin and password
		dbConnection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;instance=MSSQLSERVER;databaseName=LibrarySystem","LibrarySystemLogin","librarypassword");
		//dbConnection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;instance=MSSQLSERVER;integratedSecurity = true; databaseName=LibrarySystem");
		
	}
	
	public static void connectDB(Credentials credentials) throws SQLException
	{
		String connectionString = "jdbc:sqlserver://localhost:1433;instance=MSSQLSERVER;databaseName=LibrarySystem";
		dbConnection = DriverManager.getConnection(connectionString, credentials.getLoginName(), credentials.getLoginPassword());
		
	}
	
	public static Connection getConnection()
	{
		return dbConnection;
	}
	
	public static void closeConnection() throws SQLException
	{
		try {
			if(dbConnection != null) 
			{
				dbConnection.close();
			}
		}
		catch(Exception e){
			
		}
	}
}
