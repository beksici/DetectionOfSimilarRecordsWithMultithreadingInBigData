
//import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.ArrayList;
import java.util.StringTokenizer;
//import java.util.concurrent.Semaphore;

public class Task_2 implements Runnable{ 
private String name1;
private String name2;
private double benzerlikOrani;
private int threadSayisi;
private String threadName;
private int count;
private int kayitSayisi;
Statement myStat;
Statement myStat1;
Statement myStat2;
public static double startTime;
public static double endTime;
public static double estimatedTime;
public static String currentThreadName;

//ArrayList<String> row1 = new ArrayList<>();
	public Task_2(String threadName ,String name1, String name2, double benzerlikOrani, int threadSayisi,int count,int kayitSayisi,Statement myStat,Statement myStat1) {
	this.name1 = name1;
	this.name2 = name2;
	this.benzerlikOrani = benzerlikOrani;

	this.threadSayisi = threadSayisi;
	this.threadName=threadName;
	this.count=count;
	this.kayitSayisi=kayitSayisi;
	this.myStat=myStat;
	this.myStat1=myStat1;
	
	
	//Baglanti.yap();
	
	
}
	
	@SuppressWarnings("resource")
	synchronized void function() throws SQLException {
	//System.out.println("THREAD ÇALIÞIYOR .. " + threadName );
	// Connection myConn=Baglanti.baglantiKur();
	// Statement myStat= myConn.createStatement();
	// Statement myStat1= myConn.createStatement();
	 
		String row1="";
		String row2="";
		//int id;
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
		/*
		int artisMiktar;
		
		
		int lastIndex; 
		int beginIndex; 
		String sorgu="";
	 
		
	
	
		if(threadName.equalsIgnoreCase("t0")) {
			
			
				
			artisMiktar=kayitSayisi/threadSayisi;
			beginIndex=1;
			lastIndex=artisMiktar;
		
		}else { artisMiktar=kayitSayisi/threadSayisi;
			lastIndex=(count*artisMiktar);
		 beginIndex=(lastIndex - artisMiktar);
		
			
		}*/
		
		for (int  i= beginIndex; i < lastIndex ; i++) {
			

		String sorgu = " select "+ this.name2+ " from filtrelenmis where new_id="+i;
		//Baglanti.yap();
		ResultSet myres = myStat1.executeQuery(sorgu); //Baglanti.sorgula(sorgu);
		try {
			while(myres.next())
			{
				row1=myres.getString(name2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		StringTokenizer st1 = new StringTokenizer(row1, " ");
		for (int j = i; j <=kayitSayisi ; j++) {
			 sorgu = "select "+ this.name2+ " from filtrelenmis where new_id="+j;
			// Baglanti.yap();
			 ResultSet myres1=myStat1.executeQuery(sorgu); //Baglanti.sorgula(sorgu);
				try {
					while(myres1.next())
					{
						row2=myres1.getString(name2);
						
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(row1.equalsIgnoreCase(row2)) {
					benzerlik=100.0;
					if(benzerlik>=benzerlikOrani) {
						
						/*System.out.println(threadName +" desin");
						System.out.println(row1 +"  " +i +" \n" +row2 + "  " + j );//System.out.println(row1 +"\n" +row2 );
						System.out.println("benzerlik = " + benzerlik);*/
						sorgu="select id from filtrelenmis where new_id="+j;
						myres1=myStat1.executeQuery(sorgu);
						int idRow2=0;
						int idRow1=0;
						String row1request=" ";
						String row2request=" ";
						try {
							while(myres1.next())
							{
								idRow2=myres1.getInt("id");
								
								
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}    
						sorgu="select id from filtrelenmis where new_id="+i;
						myres1=myStat1.executeQuery(sorgu);
						try {
							while(myres1.next())
							{
								idRow1=myres1.getInt("id");
								
								
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}    
					sorgu="select "+ this.name1 + " from rows3 where id="+idRow1;
					myres1=myStat1.executeQuery(sorgu);
					try {
						while(myres1.next())
						{
							row1request =myres1.getString(this.name1);
							
							
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					sorgu="select "+ this.name1 + " from rows3 where id="+idRow2;
					myres1=myStat1.executeQuery(sorgu);
					try {
						while(myres1.next())
						{
							row2request =myres1.getString(this.name1);
							
							
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					Main.resultTask_2.oluþtur(row1, row2, benzerlik,row1request,row2request);
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
	        	
	        	//TODO SONUÇ EKRANI BURAYA
			/*System.out.println(threadName +" desin");
			System.out.println(row1 +"  " +i +" \n" +row2 + "  " + j );//System.out.println(row1 +"\n" +row2 );
			System.out.println("benzerlik = " + benzerlik);*/
			
			sorgu="select id from filtrelenmis where new_id="+j;
			myres1=myStat1.executeQuery(sorgu);
			int idRow2=0;
			int idRow1=0;
			String row1request=" ";
			String row2request=" ";
			try {
				while(myres1.next())
				{
					idRow2=myres1.getInt("id");
					
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}    
			sorgu="select id from filtrelenmis where new_id="+i;
			myres1=myStat1.executeQuery(sorgu);
			try {
				while(myres1.next())
				{
					idRow1=myres1.getInt("id");
					
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}    
		sorgu="select "+ this.name1 + " from rows3 where id="+idRow1;
		myres1=myStat1.executeQuery(sorgu);
		try {
			while(myres1.next())
			{
				row1request =myres1.getString(this.name1);
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sorgu="select "+ this.name1 + " from rows3 where id="+idRow2;
		myres1=myStat1.executeQuery(sorgu);
		try {
			while(myres1.next())
			{
				row2request =myres1.getString(this.name1);
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Main.resultTask_2.oluþtur(row1, row2, benzerlik,row1request,row2request);
	        }
			
		}
		
		}	
		lastIndex=0;
    	beginIndex=0;
	
	}
	
	

	@Override
	public void run() {
		
		try {
			startTime=System.currentTimeMillis()/1000;
			function();
			endTime=System.currentTimeMillis()/1000;
			estimatedTime=endTime-startTime;
			currentThreadName=Thread.currentThread().getName();
			Main.threadTime.oluþtur(currentThreadName, estimatedTime);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

}
