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
	private JScrollPane scrollPane_1;
	private JTextArea textArea;
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
		frmResearchMethods.setBounds(100, 100, 735, 450);
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
					Statistic s = new Statistic(10, 4, data);
					switch (index) {
					case 0:
						textArea.append("Мультиколинеарность\n");
						textArea.append("\n");
						double determ = s.getDetermMatrix();
						textArea.append("Определитель кореляционной матрицы - " + determ + ";\n");
						textArea.append("Поскольку определитель ~0, в массиве может \nсуществовать мультиколинеарность\n");
						textArea.append("\n");
						textArea.append("Проверка всего массива на наличие зависимоти по критерию X^2\n");
						double fi = s.getTestFi();
						textArea.append("Так как Фи рассчитанное= " + fi + "<7,81 X^2 табл.\n");
						textArea.append("(для уровней свободы 3 и уровня значимости 0,05)\n");
						textArea.append("Это значит, что в массиве переменных не сущ. мультиколинеарности\n");
						textArea.append("\n");
						textArea.append("Проверка на колинеарность отдельных переменных по Ф критерию\n");
						textArea.append("(для степеней свободы V1=7, V2=2 и уровня значимости 0,05)\n");
						double[] fArray = s.getTestFArray();
						textArea.append("Значение Ф1: " + fArray[0] + "<4.74 Ф табл.\n");
						textArea.append("Значение Ф2: " + fArray[1] + "<4.74 Ф табл.\n");
						textArea.append("Значение Ф3: " + fArray[2] + "<4.74 Ф табл.\n");
						textArea.append("Это значит, что каждая из переменных не колинеарна с другими\n");
						textArea.append("\n");
						textArea.append("Определение частичных коефициентов кореляции\n");
						double[] kArray = s.getTestKor();
						textArea.append("k1: " + kArray[0] + "\n");
						textArea.append("между х1 и х2 существует умеренная связь, если не учитывать влияние х3\n");
						textArea.append("k2: " + kArray[1] + "\n");
						textArea.append("между х1 и х3 существует слабая связь, если не учитывать влияние х2\n");
						textArea.append("k3: " + kArray[2] + "\n");
						textArea.append("между х2 и х3 существует умеренная связь, если не учитывать влияние х1\n");
						textArea.append("\n");
						textArea.append("Проверка на колинеарность с помощью t-критерия\n");
						textArea.append("(для степеней свободы V1=7, V2=2 и уровня значимости 0,05)\n");
						double tArray[] = s.getTestT();
						textArea.append("t1: " + tArray[0] + "< t табл (2.365)\n");
						textArea.append("Это значит, что х1 и х2 не колинеарны между собой\n");
						textArea.append("t2: " + tArray[1] + "< t табл (2.365)\n");
						textArea.append("Это значит, что х1 и х3 не колинеарны между собой\n");
						textArea.append("t3: " + tArray[2] + "< t табл (2.365)\n");
						textArea.append("Это значит, что х2 и х3 не колинеарны между собой\n");
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
		
		scrollPane_1 = new JScrollPane();
		frmResearchMethods.getContentPane().add(scrollPane_1, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		scrollPane_1.setRowHeaderView(textArea);
		
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
