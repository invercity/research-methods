package ua.invercity.research;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;

@SuppressWarnings("rawtypes")
public class MainView {

	private JFrame frmResearchMethods;
	private JList list;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuData;
	private MainView _this = this;
	private Object[][] data = {
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
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView window = new MainView();
					window.frmResearchMethods.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("unchecked")
	private void initialize() {
		frmResearchMethods = new JFrame();
		frmResearchMethods.setTitle("Методы научных исследований");
		frmResearchMethods.setBounds(100, 100, 750, 450);
		frmResearchMethods.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String[] buttons = {
				"Мультиколинеарность",
				"Гетероскедастичность"
		};
		list = new JList(buttons);
		list.setFont(new Font("Dialog", Font.BOLD, 14));
		list.addListSelectionListener(new ListSelectionListener() {
			boolean clicked = false;
			public void valueChanged(ListSelectionEvent e) {
				if (clicked == true) clicked = false;
				else {
					clicked = true;
					int index = list.getSelectedIndex();
					textArea.setText("");
					switch (index) {
					case 0:
						textArea.append("Мультиколинеарность");
						break;
					case 1: 
						textArea.append("Гетероскедастичность");
						break;
					default:
						break;
					}
				}
			}
		});
		scrollPane = new JScrollPane(list);
		frmResearchMethods.getContentPane().add(scrollPane, BorderLayout.WEST);
		textArea = new JTextArea();
		textArea.setFont(new Font("Dialog", Font.PLAIN, 15));
		textArea.setEditable(false);
		frmResearchMethods.getContentPane().add(textArea, BorderLayout.CENTER);
		
		menuBar = new JMenuBar();
		frmResearchMethods.setJMenuBar(menuBar);
		
		menu = new JMenu("Данные");
		menuBar.add(menu);
		
		menuData = new JMenuItem("Изменить");
		menuData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataInput d = new DataInput(_this, data);
				d.setVisible(true);
			}
		});
		menu.add(menuData);
	}
	
	public void updateData(Object[][] data) {
		this.data = data;
	};

}
