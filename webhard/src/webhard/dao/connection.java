package webhard.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class connection {
	private static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";
    private static final String URL = "jdbc:derby://localhost:1527/webhard;create=true;user=user;password=123";

    public static Connection conn(){
    	Connection con = null;
    	try {
			Class.forName(DRIVER).newInstance();
			con = DriverManager.getConnection(URL);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return con;
    }
}
 