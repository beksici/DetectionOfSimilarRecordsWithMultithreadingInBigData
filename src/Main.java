import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showMessageDialog;

@SuppressWarnings("serial")
public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField txtDvf;
	private JTextField textField;
	
	public static IdInput idInput;
	long a,b;
	public static double rateOfSimilarity;
	public static int numberOfThread,totalSave,id;
	public static String selectedSourceColumn;
	String numberOfThreadInput,rateOfSimilarityInput,query;
	String[] columns= {"","PRODUCT","ISSUE","COMPANY","STATE","ZIP CODE"};
	public static admin admin=new admin();
	public static ResultTask_2 resultTask_2=new ResultTask_2();
	public static ThreadTime threadTime=new ThreadTime();
	
	ResultSet myRes=DbConnection.yap();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main main = new Main();
					main.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Main() {
		String sorgu="DROP TABLE IF EXISTS filtrelenmis";
		DbConnection.update(sorgu);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 740, 406);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("THE NUMBERS OF THREAD :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 60, 174, 31);
		contentPane.add(lblNewLabel);
		
		txtDvf = new JTextField();
		txtDvf.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtDvf.setBounds(194, 61, 126, 31);
		contentPane.add(txtDvf);
		txtDvf.setColumns(10);
		
		JLabel lblTheRateOf = new JLabel("THE RATE OF SIMILARITY :");
		lblTheRateOf.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTheRateOf.setBounds(10, 135, 174, 31);
		contentPane.add(lblTheRateOf);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.BOLD, 11));
		textField.setColumns(10);
		textField.setBounds(194, 136, 126, 31);
		contentPane.add(textField);
		
		JLabel lblColumnToTest = new JLabel("COLUMN THAT WILL BE TESTED :");
		lblColumnToTest.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblColumnToTest.setBounds(10, 213, 207, 31);
		contentPane.add(lblColumnToTest);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(columns));
		comboBox.setSelectedIndex(0);
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 13));
		comboBox.setBounds(227, 218, 92, 26);
		contentPane.add(comboBox);
		
		JButton btnNewButton = new JButton("ONLY WITH RATE AND COLUMN");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(471, 61, 207, 31);
		contentPane.add(btnNewButton);
		
		JButton btnCustom = new JButton("CUSTOM SAVE");
		btnCustom.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCustom.setBounds(471, 136, 207, 31);
		contentPane.add(btnCustom);
		
		JButton btnRestrctedSearch = new JButton("RESTRICTED SEARCH");
		btnRestrctedSearch.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnRestrctedSearch.setBounds(471, 214, 207, 31);
		contentPane.add(btnRestrctedSearch);
		
		query="select count(*) as result from rows3";
		myRes=DbConnection.sorgula(query);
		try {
			while(myRes.next()) {
				totalSave=myRes.getInt("result");
				//totalSave=2000;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				try {
					numberOfThreadInput=txtDvf.getText();
					rateOfSimilarityInput=textField.getText();
					if(Integer.parseInt(numberOfThreadInput)<1  || Double.parseDouble(rateOfSimilarityInput)<0.0  || Double.parseDouble(rateOfSimilarityInput)>100.0 || comboBox.getSelectedIndex()==0) {
						showMessageDialog(null, "Please Enter Valid Values !!!");
						Main main=new Main();
						main.setVisible(true);
						setVisible(false);
						return;
					}else{
						numberOfThread=Integer.parseInt(numberOfThreadInput);
						rateOfSimilarity=Double.parseDouble(rateOfSimilarityInput);
						if(comboBox.getSelectedIndex()==1) {
							selectedSourceColumn="product";
						}else if(comboBox.getSelectedIndex()==2) {
							selectedSourceColumn="issue";
						}else if(comboBox.getSelectedIndex()==3) {
							selectedSourceColumn="company";
						}else if(comboBox.getSelectedIndex()==4) {
							selectedSourceColumn="state";
						}else if(comboBox.getSelectedIndex()==5) {
							selectedSourceColumn="zip_code";
						}
						Scenario_1.main(null);
						setVisible(false);
						return;
					}
				}catch(NumberFormatException e2) {
					showMessageDialog(null, "Please Enter Valid Values !!!");
					Main main=new Main();
					main.setVisible(true);
					setVisible(false);
					return;
				}
			}
		});
		

		btnCustom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					numberOfThreadInput=txtDvf.getText();
					rateOfSimilarityInput=textField.getText();
					if(Integer.parseInt(numberOfThreadInput)<1  || Double.parseDouble(rateOfSimilarityInput)<0.0  || Double.parseDouble(rateOfSimilarityInput)>100.0 || comboBox.getSelectedIndex()==0) {
						showMessageDialog(null, "Please Enter Valid Values !!!");
						Main main=new Main();
						main.setVisible(true);
						setVisible(false);
						return;
					}else{
						numberOfThread=Integer.parseInt(numberOfThreadInput);
						rateOfSimilarity=Double.parseDouble(rateOfSimilarityInput);
						if(comboBox.getSelectedIndex()==1) {
							selectedSourceColumn="product";
						}else if(comboBox.getSelectedIndex()==2) {
							selectedSourceColumn="issue";
						}else if(comboBox.getSelectedIndex()==3) {
							selectedSourceColumn="company";
						}else if(comboBox.getSelectedIndex()==4) {
							selectedSourceColumn="state";
						}else if(comboBox.getSelectedIndex()==5) {
							selectedSourceColumn="zip_code";
						}
						GetRequest getRequest=new GetRequest();
						getRequest.setVisible(true);
						setVisible(false);
						return;
					}
				}catch(NumberFormatException e2) {
					showMessageDialog(null, "Please Enter Valid Values !!!");
					Main main=new Main();
					main.setVisible(true);
					setVisible(false);
					return;
				}
			}
		});

		btnRestrctedSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					numberOfThreadInput=txtDvf.getText();
					rateOfSimilarityInput=textField.getText();
					if(Integer.parseInt(numberOfThreadInput)<1  || Double.parseDouble(rateOfSimilarityInput)<0.0  || Double.parseDouble(rateOfSimilarityInput)>100.0 || comboBox.getSelectedIndex()==0) {
						showMessageDialog(null, "Please Enter Valid Values !!!");
						Main main=new Main();
						main.setVisible(true);
						setVisible(false);
						return;
					}else{
						numberOfThread=Integer.parseInt(numberOfThreadInput);
						rateOfSimilarity=Double.parseDouble(rateOfSimilarityInput);
						if(comboBox.getSelectedIndex()==1) {
							selectedSourceColumn="product";
						}else if(comboBox.getSelectedIndex()==2) {
							selectedSourceColumn="issue";
						}else if(comboBox.getSelectedIndex()==3) {
							selectedSourceColumn="company";
						}else if(comboBox.getSelectedIndex()==4) {
							selectedSourceColumn="state";
						}else if(comboBox.getSelectedIndex()==5) {
							selectedSourceColumn="zip_code";
						}
						idInput=new IdInput();
						idInput.setVisible(true);
						setVisible(false);
						return;
					}
				}catch(NumberFormatException e2) {
					showMessageDialog(null, "Please Enter Valid Values !!!");
					Main main=new Main();
					main.setVisible(true);
					setVisible(false);
					return;
				}
			}
		});
		
		
	}//Main constructor bitiþi
}
