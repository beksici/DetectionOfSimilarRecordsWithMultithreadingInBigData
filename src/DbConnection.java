import java.sql.*;

public class DbConnection {
	
	static Connection myConn;
	static Statement myStat;
	
	static ResultSet yap() {
		ResultSet myRs = null;
		try {			
			myConn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/kayitlarr","root","1234");
			Statement myStatt;
			myStatt =  myConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			myStat=myStatt;
			myRs = myStat.executeQuery("select * from rows3");
			
			
		} catch (Exception e) {
			System.out.println("Database Connection Failed!!!");
		}
		return myRs;
	}
	
	static void ekle(String sql_sorgu) {
		
		try {
			myStat.executeUpdate(sql_sorgu);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void update(String sql_sorgu) {
		try {
			myStat.executeUpdate(sql_sorgu);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void sil(String sql_sorgu) {
		try {
			myStat.executeUpdate(sql_sorgu);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static ResultSet sorgula(String sql_sorgu) {
		ResultSet myRs =null;		
		try {			
			myRs = myStat.executeQuery(sql_sorgu);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return myRs;
	}
}