package ua.invercity.research;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTable;

import java.awt.Font;

public class DataInput extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
	private JTable table;
	private Object[][] defaultData;
	private String[] defaultColumnNames = {"1", "2", "3", "4"};
	private MainView main = null;

	/**
	 * Create the dialog.
	 */
	public DataInput() {
		setTitle("Ввод данных");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		{
			model = new DefaultTableModel(10, 4);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// update data in matrix
						for (int i=0;i<10;i++) {
							for (int j=0;j<4;j++) {
								defaultData[i][j] = model.getValueAt(i, j);
							}
						}
						// update main matrix
						main.updateData(defaultData);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			table = new JTable();
			model = new DefaultTableModel();
			table.setModel(model);
			table.setFont(new Font("Dialog", Font.PLAIN, 14));
			getContentPane().add(table, BorderLayout.NORTH);
		}
	}
	
	public DataInput(MainView parent, Object[][] data) {
		this();
		main = parent;
		defaultData = data;
		model.setDataVector(defaultData, defaultColumnNames);
	}

}
