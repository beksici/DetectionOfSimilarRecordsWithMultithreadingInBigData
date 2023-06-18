

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Semaphore;

public class Baglanti {
	
	static Connection myConn;
	static Statement myStat;
	private static Semaphore sem = new Semaphore(1);
	 static void yap(){
		 
		
		try {			
			
			myConn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/kayitlarr","root","1234");
			sem.acquire();
			myStat =  myConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		sem.release();
		}
	 
	 static Connection baglantiKur() {
		 Connection myConn=null;
		 try {
			myConn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/kayitlarr","root","1234");
			 return myConn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 
			e.printStackTrace();
			return myConn;
		}
		
	 }
	 
	 
	 
	
//	private static Semaphore sem = new Semaphore(1);
	static ResultSet sorgula(String sql_sorgu) {
		
		/*try {
			sem.acquire();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		ResultSet myRs =null;		
		try {			
			myRs = myStat.executeQuery(sql_sorgu);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//sem.release();
		return myRs;
	}
	
	static void update(String sql_sorgu) {
		try {
			myStat.execute(sql_sorgu);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	
	
}