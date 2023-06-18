import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class GetRequest extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	public static String selectedSameColumn,dataInColumn,dataThatWillBePrinted;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GetRequest frame = new GetRequest();
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GetRequest() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Data in Column :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(36, 85, 164, 26);
		contentPane.add(lblNewLabel);
		
		JLabel lblDataThatWill = new JLabel("Data that will be printed :");
		lblDataThatWill.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDataThatWill.setBounds(36, 156, 164, 26);
		contentPane.add(lblDataThatWill);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"", "PRODUCT", "ISSUE", "COMPANY", "STATE", "ZIP CODE", "COMPLAINT ID"}));
		comboBox_1.setBounds(229, 158, 118, 26);
		contentPane.add(comboBox_1);
		
		JButton btnNewButton = new JButton("ONAYLA");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBounds(229, 224, 118, 26);
		contentPane.add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(229, 88, 195, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblAccordingToWhat = new JLabel("According to what columns :");
		lblAccordingToWhat.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAccordingToWhat.setBounds(36, 32, 164, 26);
		contentPane.add(lblAccordingToWhat);
		
		JComboBox comboBox_1_1 = new JComboBox();
		comboBox_1_1.setModel(new DefaultComboBoxModel(new String[] {"", "PRODUCT", "ISSUE", "COMPANY", "STATE", "ZIP CODE"}));
		comboBox_1_1.setBounds(229, 34, 118, 26);
		contentPane.add(comboBox_1_1);
		

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox_1_1.getSelectedIndex()==1) {
					selectedSameColumn="product";
				}else if(comboBox_1_1.getSelectedIndex()==2) {
					selectedSameColumn="issue";
				}else if(comboBox_1_1.getSelectedIndex()==3) {
					selectedSameColumn="company";
				}else if(comboBox_1_1.getSelectedIndex()==4) {
					selectedSameColumn="state";
				}else if(comboBox_1_1.getSelectedIndex()==5) {
					selectedSameColumn="zip_code";
				}
				dataInColumn=textField.getText();
				if(comboBox_1.getSelectedIndex()==1) {
					dataThatWillBePrinted="product";
				}else if(comboBox_1.getSelectedIndex()==2) {
					dataThatWillBePrinted="issue";
				}else if(comboBox_1.getSelectedIndex()==3) {
					dataThatWillBePrinted="company";
				}else if(comboBox_1.getSelectedIndex()==4) {
					dataThatWillBePrinted="state";
				}else if(comboBox_1.getSelectedIndex()==5) {
					dataThatWillBePrinted="zip_code";
				}else if(comboBox_1.getSelectedIndex()==6) {
					dataThatWillBePrinted="complaint_id";
				}
				Scenario_2.main(null);
				setVisible(false);
			}
		});
	}
}
