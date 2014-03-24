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
	private Object[][] defaultData = {
		{(double) 18.6, (double) 3.4, (double) 9.8, (double) 4.3}, 
		{(double) 17.0, (double) 5.8, (double) 6.7, (double) 4.0}, 
		{(double) 19.6, (double) 4.5, (double) 8.5, (double) 5.2}, 
		{(double) 19.9, (double) 5.2, (double) 8.1, (double) 6.0}, 
		{(double) 16.1, (double) 4.3, (double) 6.1, (double) 5.5}, 
		{(double) 17.5, (double) 3.2, (double) 7.0, (double) 4.5}, 
		{(double) 15.2, (double) 3.7, (double) 9.1, (double) 4.6}, 
		{(double) 16.4, (double) 5.7, (double) 6.5, (double) 4.0}, 
		{(double) 17.6, (double) 3.9, (double) 6.7, (double) 4.7}, 
		{(double) 17.6, (double) 3.4, (double) 9.4, (double) 4.2}
	}; 
	private String[] defaultColumnNames = {"1", "2", "3", "4"};

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
			model.setDataVector(defaultData, defaultColumnNames);
			table.setModel(model);
			table.setFont(new Font("Dialog", Font.PLAIN, 14));
			getContentPane().add(table, BorderLayout.NORTH);
		}
	}

}
