
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.ArrayList;
import java.util.StringTokenizer;
//import java.util.concurrent.Semaphore;

public class Task_3 implements Runnable {
	private String name1;
	private String name2;
	private double benzerlikOrani;
	private int threadSayisi;
	private String threadName;
	private int count;
	public int kayitSayisi =Main.totalSave;
	public static double startTime;
	public static double endTime;
	public static double estimatedTime;
	public static String currentThreadName;
	Connection myConn;

//ArrayList<String> row1 = new ArrayList<>();
	public Task_3(String threadName, String name1, String name2, double benzerlikOrani, int threadSayisi, int count, Connection myConn) {
		this.name1 = name1;
		this.name2 = name2;
		this.benzerlikOrani = benzerlikOrani;

		this.threadSayisi = threadSayisi;
		this.threadName = threadName;
		this.count = count;
		this.myConn=myConn;

		// Baglanti.yap();

	}

	synchronized void function() throws SQLException {
		// System.out.println("thread çalýþýyor.. " + threadName );
		
		Statement myStat1 = myConn.createStatement();

		String row1 = this.name1;
		String row2 = "";
		// int id;
		int payda;
		String tut1;
		String tut2;
		int pay = 0;
		double benzerlik = 0;

		int artisMiktar;

		int lastIndex;
		int beginIndex;
		String sorgu = "";

		/*
		 * if(threadName.equalsIgnoreCase("t0")) {
		 * 
		 * 
		 * 
		 * artisMiktar=kayitSayisi/threadSayisi; beginIndex=1; lastIndex=artisMiktar;
		 * 
		 * }else { artisMiktar=kayitSayisi/threadSayisi; lastIndex=(count*artisMiktar);
		 * beginIndex=(lastIndex - artisMiktar);
		 * 
		 * 
		 * }
		 */
		int baslangýcIndex = 1;
		int kayitKontrol = (kayitSayisi - baslangýcIndex) / threadSayisi;
		if (threadName.equalsIgnoreCase("t0")) {

			beginIndex = baslangýcIndex;
			if ((kayitSayisi - baslangýcIndex) % threadSayisi != 0) {
				artisMiktar = kayitKontrol + 1;
				lastIndex = beginIndex + artisMiktar;

			} else {
				artisMiktar = kayitKontrol;
				lastIndex = beginIndex + artisMiktar;
			}
		} else {
			if ((kayitSayisi - baslangýcIndex) % threadSayisi != 0) {

				artisMiktar = kayitKontrol + 1;
				lastIndex = baslangýcIndex + (count * artisMiktar);
				beginIndex = (lastIndex - artisMiktar) + 1;
				if (threadName.equalsIgnoreCase("t" + (threadSayisi - 1))) {
					lastIndex = kayitSayisi;
				}

			} else {
				artisMiktar = kayitKontrol;
				lastIndex = baslangýcIndex + (count * artisMiktar);
				beginIndex = (lastIndex - artisMiktar) + 1;
			}

		}

		for (int i = beginIndex; i <= lastIndex; i++) {

			StringTokenizer st1 = new StringTokenizer(row1, " ");
			sorgu = "select " + this.name2 + " from rows3 where id=" + i;
			// Baglanti.yap();
			ResultSet myres1 = myStat1.executeQuery(sorgu); // Baglanti.sorgula(sorgu);
			try {
				while (myres1.next()) {
					row2 = myres1.getString(name2);

				}
			} catch (SQLException e) {
				System.out.println(sorgu);
				e.printStackTrace();
			}
			if (row1.equalsIgnoreCase(row2)) {
				benzerlik = 100.0;
				if (benzerlik >= benzerlikOrani) {
					Main.admin.oluþtur(row1, row2, benzerlik);
				}
				continue;
			}
			StringTokenizer st2 = new StringTokenizer(row2, " ");

			if (st2.countTokens() > st1.countTokens()) {
				payda = st2.countTokens();
			} else {
				payda = st1.countTokens();
			}

			while (st1.hasMoreTokens()) {
				tut1 = st1.nextToken();

				while (st2.hasMoreTokens()) {
					tut2 = st2.nextToken();

					if (tut1.equalsIgnoreCase(tut2)) {
						pay++;
					}

				}
				if (st2.hasMoreTokens() == false) {
					st2 = new StringTokenizer(row2, " ");
				}
			}
			if (st1.hasMoreTokens() == false) {
				st1 = new StringTokenizer(row1, " ");
			}
			if (pay == 0) {
				benzerlik = 0;
			} else {
				if (pay > payda)
					benzerlik = 100.0;
				else {
					benzerlik = (pay * 100.0) / payda;
				}
				pay = 0;
				payda = 0;

			}
			if (benzerlik >= benzerlikOrani) {

				// TODO SONUÇ EKRANI BURAYA

				Main.admin.oluþtur(row1, row2, benzerlik);

				System.out.println();
				System.out.println(threadName + " desin");
				System.out.println(row1 + " " + baslangýcIndex + "\n" + row2 + " " + i);// System.out.println(row1 +"\n"
																						// +row2 );
				System.out.println("benzerlik = " + benzerlik);
				// System.out.println(threadName +" desin" + row1 + " "+ baslangýcIndex +"\n"
				// +row2 +" " +i + " benzerlik = " + benzerlik);//System.out.println(row1 +"\n"
				// +row2);

				/*
				 * try { Thread.sleep(1000); } catch (InterruptedException e) { // TODO
				 * Auto-generated catch block e.printStackTrace(); }
				 */
			}

		}
		lastIndex = 0;
		beginIndex = 0;

	}

	@Override
	public void run() {

		try {
			startTime = System.currentTimeMillis() / 1000;
			function();
			endTime = System.currentTimeMillis() / 1000;
			estimatedTime = endTime - startTime;
			currentThreadName = Thread.currentThread().getName();
			Main.threadTime.oluþtur(currentThreadName, estimatedTime);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
