
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSetMetaData;

@SuppressWarnings("serial")
public class admin extends JFrame {

	private JPanel contentPane;
	
	ResultSetMetaData rsmd;
	DefaultTableModel model;
	String[] colName=new String[3];
	int cols;
	private JTable table;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					admin frame = new admin();
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
	public admin() {
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
		colName[0]="Data_1";
		colName[1]="Data_2";
		colName[2]="Similarity";
		model.setColumnIdentifiers(colName);
		
	}
	
	public synchronized void oluþtur(String row1, String row2, double similarity) {
		String[] row= {row1,row2,String.valueOf(similarity)};
		model.addRow(row);
	}
}

