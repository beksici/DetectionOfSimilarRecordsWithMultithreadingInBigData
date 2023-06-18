
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Scenario_2 {

	public static double startTime;
	public static double endTime;
	public static double estimatedTime;
	public static String currentThreadName;
	public static int newsayi;
	public static Connection[] myConn1;

	public static void main(String[] args) {
		int j = 0;

		int kayitSayisi = 0;
		Connection myConn = null;
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kayitlarr", "root", "1234");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Statement stm = null;
		try {
			stm = myConn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
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
		// ExecutorService executor= Executors.newFixedThreadPool(5);
		String sorgu;
		sorgu = "SET @rownr=0;";
		// System.out.println(sorgu);
		// Baglanti.yap();
		try {
			stm.execute(sorgu);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sorgu = " CREATE TABLE kayitlarr.filtrelenmis AS SELECT @rownr:=@rownr+1 AS new_id ,id , "
				+ Main.selectedSourceColumn + " from kayitlarr.rows3 where " + GetRequest.selectedSameColumn + "='"
				+ GetRequest.dataInColumn + "';";
		// System.out.println(sorgu);
		try {
			stm.execute(sorgu);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sorgu = "ALTER TABLE `kayitlarr`.`filtrelenmis` CHANGE COLUMN `new_id` `new_id` INT NOT NULL ,ADD UNIQUE INDEX `new_id_UNIQUE` (`new_id` ASC); "
				+ "\n" + ";";
		try {
			stm.execute(sorgu);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sorgu = "select count(*) as kayitsayisi from filtrelenmis ";
		ResultSet resKayit = null;
		try {
			resKayit = stm.executeQuery(sorgu);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			while (resKayit.next()) {
				kayitSayisi = resKayit.getInt("kayitsayisi");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO BURADAKÝ KAYIT SAYISI FÝLTRELENMÝÞ TABLODAKÝ KAYIT SAYISI
		 //kayitSayisi= kayitSayisi/128;
		 System.out.println(kayitSayisi);
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
			// executor.submit(new Task("t"+i,"product",60,sayi,i+1));
			Statement myStat = null;
			Statement myStat1 = null;
			if (i >= 36) {
				try {
					myStat = myConn1[i - 1].createStatement();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					myStat1 = myConn1[i - 1].createStatement();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				threadler[i] = new Thread(new Task_2("t" + i, GetRequest.dataThatWillBePrinted,Main.selectedSourceColumn, Main.rateOfSimilarity, sayi, i + 1, kayitSayisi, myStat, myStat1));
				threadler[i].start();
			} else {
				try {
					myStat = myConn1[i].createStatement();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					myStat1 = myConn1[i].createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				threadler[i] = new Thread(new Task_2("t" + i, GetRequest.dataThatWillBePrinted,Main.selectedSourceColumn, Main.rateOfSimilarity, sayi, i + 1, kayitSayisi, myStat, myStat1));
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
		// System.out.println(estimatedTime+"-------------------------");
		Main.threadTime.setVisible(true);
		Main.resultTask_2.setVisible(true);
		sorgu = "DROP TABLE filtrelenmis";
		try {
			stm.execute(sorgu);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
