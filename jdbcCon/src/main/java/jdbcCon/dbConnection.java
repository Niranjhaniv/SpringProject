package jdbcCon;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.JSONException;



public class dbConnection {
	
	 
	/**
	 * @author Niranjhani 
	 *
	 * 
	 */
	 
	 
		static Connection dbConn = null;
		static PreparedStatement prepStat = null;
	 
		public static void main(String[] argv) throws JSONException {
	 
			try {
				makeJDBCConnection();
				
				getDataFromDB();
				
				insertJson();
	 
				prepStat.close();
				dbConn.close(); 
	 
			} catch (SQLException e) {
	 
			e.printStackTrace();
			}
			

 
		}
	 
		private static void makeJDBCConnection() {
	 
			try {
				Class.forName("com.mysql.jdbc.Driver");
				log("Congrats");
			} catch (ClassNotFoundException e) {
				log("Sorry, couldn't found JDBC driver.");
				e.printStackTrace();
				return;
			}
	 
			try {
				dbConn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testing", "niru", "niru");
				if (dbConn != null) {
					log("Connection Successful");
				} else {
					log("Failed to make connection!");
				}
			} catch (SQLException e) {
				log("MySQL Connection Failed!");
				e.printStackTrace();
				return;
			}
	 
		}
	 
	private static void addDataToDB(long id, String name, long mark, long age) {
	 
			try {
				String insertQueryStatement = "INSERT  INTO  student  VALUES  (?,?,?,?)";
	 
				prepStat = dbConn.prepareStatement(insertQueryStatement);
				prepStat.setLong(1, id);
				prepStat.setString(2, name);
				prepStat.setLong(3, mark);
				prepStat.setLong(4, age);
	 
				// execute insert SQL statement
				prepStat.executeUpdate();
				log("Student" + name + " added successfully");
			} catch (
	 
			SQLException e) {
				e.printStackTrace();
			}
		}
	 
		private static void getDataFromDB() throws JSONException {
	 
			try {
				String getQueryStatement = "SELECT * FROM student";
	 
				prepStat = dbConn.prepareStatement(getQueryStatement);
	 
				// Execute the Query, and get a java ResultSet
				ResultSet rs = prepStat.executeQuery();
	 
				// Let's iterate through the java ResultSet
				Map<String, String> myMap;
				List myList = new ArrayList();
				
				JSONArray json = new JSONArray();
				ResultSetMetaData rsmd = rs.getMetaData();
				
				while (rs.next()) {
					
					int numColumns = rsmd.getColumnCount();
					JSONObject obj = new JSONObject();
					for (int i=1; i<=numColumns; i++) {
					    String column_name = rsmd.getColumnName(i);
					    obj.put(column_name, rs.getObject(column_name));
					 }
//					 ((Object) json).put(obj);
					 
					 System.out.println(json);
					
					myMap = new HashMap<String, String>();	
					String id = rs.getString("id");
					String name = rs.getString("name");
					String age = rs.getString("age");
					String mark = rs.getString("mark");				
				    myMap.put("id", id);
				    myMap.put("name", name);
				    myMap.put("age", age);
				    myMap.put("mark", mark);
				    myList.add(myMap);
					// Simply Print the results
					System.out.format("%s, %s, %s, %s\n", id, name, age, mark);
				}
	 
			} catch (
	 
			SQLException e) {
				e.printStackTrace();
			}
	 
		}
		
		public static void insertJson() throws JSONException
		{
			JSONParser parser = new JSONParser();
			try {
				JSONArray array=(JSONArray) parser.parse(new FileReader("G:\\jsonArray.json"));
				
				for(Object o : array)
				{
					JSONObject student=(JSONObject) o;
					long id=(Long)student.get("id");
					//System.out.println("id"+id);
					 String name=(String) student.get("name");
					 long mark=(Long) student.get("mark");
					 long age=(Long) student.get("age");
					 addDataToDB(id, name, mark, age);
				}
	 
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 
		// Simple log utility
		private static void log(String string) {
			System.out.println(string);
	 
		}

}
