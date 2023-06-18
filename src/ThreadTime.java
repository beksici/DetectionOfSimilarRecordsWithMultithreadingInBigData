import java.awt.EventQueue;
import java.sql.ResultSetMetaData;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class ThreadTime extends JFrame {

	private JPanel contentPane;
	ResultSetMetaData rsmd;
	DefaultTableModel model;
	String[] colName=new String[2];
	int cols;
	private JTable table;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ThreadTime frame = new ThreadTime();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ThreadTime() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1212, 569);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 1176, 508);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBounds(694, 208, 299, 230);
		//contentPane.add(table);
		scrollPane.setViewportView(table);
		model=(DefaultTableModel) table.getModel();
		colName[0]="Thread Name";
		colName[1]="Lifetime";
		model.setColumnIdentifiers(colName);
		
	}
	
	public synchronized void oluþtur(String threadName, double lifetime) {
		String[] row= {threadName,String.valueOf(lifetime)};
		model.addRow(row);
		
	}


}
