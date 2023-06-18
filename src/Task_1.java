

//import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.ArrayList;
import java.util.StringTokenizer;
//import java.util.concurrent.Semaphore;

public class Task_1 implements Runnable{ 
private String name;
private double benzerlikOrani;
private int threadSayisi;
public int kayitSayisi=Main.totalSave;
private String threadName;
private int count;
public static double startTime;
public static double endTime;
public static double estimatedTime;
public static String currentThreadName;
//Connection myConn;

Statement myStat1;
//ArrayList<String> row1 = new ArrayList<>();
	public Task_1(String threadName ,String name, double benzerlikOrani, int threadSayisi,int count,Statement myStat1) {
	this.name = name;
	this.benzerlikOrani = benzerlikOrani;

	this.threadSayisi = threadSayisi;
	this.threadName=threadName;
	this.count=count;
	this.myStat1=myStat1;
	//Baglanti.yap();
	
	
}
	
	synchronized void function()  {
	//System.out.println("thread çalýþýyor.. " + threadName );
		
		String row1="";
		String row2="";
		int payda;
		String tut1;
		String tut2;
		int pay=0;
		double benzerlik=0;
		
		int artisMiktar= kayitSayisi/threadSayisi;
		
		int lastIndex= (count*artisMiktar);
		int beginIndex= (lastIndex - artisMiktar);
		if(threadName.equalsIgnoreCase("t0")) {
			beginIndex=1;
			lastIndex=artisMiktar;
		}
		
		if(threadName.equalsIgnoreCase("t"+(threadSayisi-1)))
		{lastIndex=kayitSayisi+1;
			}
		
		for (int  i= beginIndex; i < lastIndex ; i++) {
			

		String sorgu = "select "+ this.name+ " from rows3 where id="+i;
		//Baglanti.yap();
		ResultSet myres = null;
		try {
			myres = myStat1.executeQuery(sorgu);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  //Baglanti.sorgula(sorgu);
		try {
			while(myres.next())
			{
				row1=myres.getString(name);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringTokenizer st1 = new StringTokenizer(row1, " ");
		for (int j = i; j <kayitSayisi ; j++) {
			 sorgu = "select "+ this.name+ " from rows3 where id="+j;
			 //Baglanti.yap();
			 ResultSet myres1 = null;
			try {
				myres1 = myStat1.executeQuery(sorgu);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} //Baglanti.sorgula(sorgu);
				try {
					while(myres1.next())
					{
						row2=myres1.getString(name);
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println(sorgu);
					e.printStackTrace();
				}
				if(row1.equalsIgnoreCase(row2)) {
					benzerlik=100.0;
					if(benzerlik>=benzerlikOrani) {
						
						/*System.out.println(threadName +" desin");
						System.out.println(row1 +"  " +i +" \n" +row2 + "  " + j );
						System.out.println("benzerlik = " + benzerlik);*/
						
						Main.admin.oluþtur(row1, row2, benzerlik);
						
				        }
					continue;
				}
				StringTokenizer st2 = new StringTokenizer(row2," ");
				 
				if(st2.countTokens()>st1.countTokens()) { payda=st2.countTokens();}else { payda=st1.countTokens();}
			 
			while (st1.hasMoreTokens()) {  
	        tut1=st1.nextToken(); 
	        
	       while(st2.hasMoreTokens()) {  
		        tut2=st2.nextToken(); 
		        
		        if(tut1.equalsIgnoreCase(tut2))
		        {
		        	pay++;
		        }
		        
		        
		     }
	       if(st2.hasMoreTokens()==false)
	       { st2 = new StringTokenizer(row2," ");}	        
	     }
			if(st1.hasMoreTokens()==false) {
				st1 = new StringTokenizer(row1, " ");
			}
			if(pay==0) {
	        	benzerlik=0;
	        }else {
	        	if (pay>payda)
	        		benzerlik=100.0;
	        	else {
	        	benzerlik =(pay*100.0)/payda;}
	        	pay=0;
	        	payda=0;
	        	
	        	
	        }if(benzerlik>=benzerlikOrani) {
			/*System.out.println(threadName +" desin");
			System.out.println(row1 +"  " +i +" \n" +row2 + "  " + j );//System.out.println(row1 +"  " +row2 + "   " + j);
			System.out.println("benzerlik = " + benzerlik);*/
			
			
			Main.admin.oluþtur(row1, row2, benzerlik);
			
	        }
			
		}
		
		}	
		lastIndex=0;
    	beginIndex=0;
	
	}
	
	

	@Override
	public void run() {
		startTime=System.currentTimeMillis()/1000;
		function();
		endTime=System.currentTimeMillis()/1000;
		estimatedTime=endTime-startTime;
		currentThreadName=Thread.currentThread().getName();
		Main.threadTime.oluþtur(currentThreadName, estimatedTime);
	}

}
