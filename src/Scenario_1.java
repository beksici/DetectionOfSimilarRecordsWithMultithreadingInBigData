
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Scenario_1 {
	public static double startTime;
	public static double endTime;
	public static double estimatedTime;
	public static String currentThreadName;
	public static int newsayi;
	public static Connection[] myConn1;

	public static void main(String[] args) {
		int j = 0;
		Connection myConn = null;
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kayitlarr", "root", "1234");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		int sayi = Main.numberOfThread;

		if (sayi >= 36) {
			newsayi = 36;
			myConn1 = new Connection[newsayi];
		}
		newsayi = sayi;
		myConn1 = new Connection[newsayi];

		Thread[] threadler = new Thread[sayi];
		startTime = System.currentTimeMillis() / 1000;
		for (int i = 0; i < sayi; i++) {
			while (j < myConn1.length) {
				try {
					myConn1[j] = DriverManager.getConnection("jdbc:mysql://localhost:3306/kayitlarr", "root", "1234");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				j++;
			}
			Statement myStat1 = null;
			if (i >= 36) {
				try {
					myStat1 = myConn1[i - 1].createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				threadler[i] = new Thread(new Task_1("t" + i, Main.selectedSourceColumn, Main.rateOfSimilarity, sayi, i + 1, myStat1));
				threadler[i].start();
			} else {
				try {
					myStat1 = myConn1[i].createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				threadler[i] = new Thread(new Task_1("t" + i, Main.selectedSourceColumn, Main.rateOfSimilarity, sayi, i + 1, myStat1));
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
		endTime = System.currentTimeMillis() / 1000;

		estimatedTime = endTime - startTime;
		Main.threadTime.oluþtur("Total Time", estimatedTime);
		// System.out.println(estimatedTime/1000.0+"-------------------------");
		Main.admin.setVisible(true);
		Main.threadTime.setVisible(true);

	}

}
