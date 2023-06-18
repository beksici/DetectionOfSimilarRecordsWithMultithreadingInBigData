
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import static javax.swing.JOptionPane.showMessageDialog;



public class Scenario_3 {
	
	public static double startTime;
	public static double endTime;
	public static double estimatedTime;
	public static String currentThreadName;
	public static Connection[] myConn1;
	public static int newsayi;
	
	public static void main(String[] args)  {
		
		int j=0;
		 
		int sayi=Main.numberOfThread;
		int id = IdInput.id;
		if (sayi >= 36) {
			newsayi = 36;
			myConn1 = new Connection[newsayi];
		}
		newsayi = sayi;
		myConn1 = new Connection[newsayi];
		//Connection myConn=Baglanti.baglantiKur();
		Thread[] threadler =  new Thread[sayi];
		String sorgu;
		String row1="";
		sorgu = "select "+Main.selectedSourceColumn+" from rows3 where  complaint_id="+id;
		//System.out.println(sorgu);
		Baglanti.yap();
		ResultSet res = Baglanti.sorgula(sorgu);
		try {
			while(res.next()) {
				row1=res.getString(Main.selectedSourceColumn);
				
			}
		} catch (SQLException e) {
			
		}
		if(row1=="") {
			showMessageDialog(null,"Hatalý ID Giriþi!!");
			Main.idInput.setVisible(false);
			IdInput idInput=new IdInput();
			idInput.setVisible(true);
			return;
		}
		startTime = System.currentTimeMillis()/1000;
	for (int i = 0; i < sayi; i++) {
		while (j < myConn1.length) {
			try {
				myConn1[j] = DriverManager.getConnection("jdbc:mysql://localhost:3306/kayitlarr", "root", "1234");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			j++;
		}
		if (i >= 36) {
		threadler[i]= new Thread(new Task_3("t"+i,row1,Main.selectedSourceColumn,Main.rateOfSimilarity,sayi,i+1, myConn1[i-1]));
		threadler[i].start();}
		else {
			threadler[i]= new Thread(new Task_3("t"+i,row1,Main.selectedSourceColumn,Main.rateOfSimilarity,sayi,i+1, myConn1[i]));
			threadler[i].start();
		}
		
	
		
	}
	for (int i = 0; i < sayi; i++) {
		try {
			threadler[i].join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	endTime = System.currentTimeMillis()/1000;

	estimatedTime = endTime - startTime;
	Main.threadTime.oluþtur("Total Time", estimatedTime);
	//System.out.println(estimatedTime/1000.0+"-------------------------");
	Main.admin.setVisible(true);
	Main.threadTime.setVisible(true);
	
	
	/*if(executor.isTerminated()) {
	executor.shutdown();	
	}*/
	
	}

}
