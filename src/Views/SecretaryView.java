package Views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import CRUD.Read;

public class SecretaryView implements View {
	private JFrame frame;
	private JPanel mainPanel;

	@Override
	public void open() {
		frame = new JFrame("Chop Shop");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		createMainPanel();
		frame.add(mainPanel);
		frame.setVisible(true);
	}

	private void createMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("Orders", getClientPanel());
		tabbedPane.add("Clients", getOrderPanel());
		tabbedPane.add("Employees", getEmployeePanel());
		mainPanel.add(tabbedPane, BorderLayout.CENTER);
		mainPanel.repaint();
	}

	private void updateMainPanel() {
		
	}

	private JComponent getClientPanel() {
		// TODO Auto-generated method stub
		return new JPanel();
	}

	private JComponent getOrderPanel() {
		// TODO Auto-generated method stub
		return new JPanel();
	}

	private JComponent getEmployeePanel() {
		JPanel EmployeePanel = new JPanel();
		ArrayList<ArrayList<String>> employees = Read.getEmployees();
		ArrayList<String> header = employees.get(0);
		EmployeePanel.setLayout(new GridLayout(employees.size(), header.size() + 1));
		boolean first = true;
		for (ArrayList<String> column : employees) {
			for (String s : column) {
				EmployeePanel.add(new JLabel(s));
			}
			if (first) {
				EmployeePanel.add(new JLabel());
				first = false;
			} else {
				JButton edit = new JButton("Edit");
				edit.addActionListener(new EditEmployeeListener(header, column));
				EmployeePanel.add(edit);
			}
		}

		return new JScrollPane(EmployeePanel);
	}

	private class EditEmployeeListener implements ActionListener {
		ArrayList<String> data, header;
		ArrayList<JTextField> textboxes;

		public EditEmployeeListener(ArrayList<String> header, ArrayList<String> column) {
			this.header = header;
			data = column;
			textboxes = new ArrayList<JTextField>();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame editFrame = new JFrame();
			editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			editFrame.setSize(600, 400);
			editFrame.setVisible(true);

			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.add(new JLabel("Edit: "), BorderLayout.NORTH);
			JPanel center = new JPanel();
			center.setLayout(new GridLayout(header.size(), 2));
			for (int i = 0; i < header.size(); i++) {
				center.add(new JLabel(header.get(i)));
				JTextField field = new JTextField(data.get(i));
				textboxes.add(field);
				center.add(field);
			}
			
			panel.add(center, BorderLayout.CENTER);
			JButton saveButton = new JButton("save");
			saveButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					//update employee data
					
					String alias = textboxes.get(0).getText();
					String position = textboxes.get(1).getText();
					String location = textboxes.get(2).getText();
					int range = Integer.parseInt(textboxes.get(3).getText());
					String currency = textboxes.get(4).getText();
					String isAvailable = textboxes.get(5).getText();
					String terminate = textboxes.get(6).getText();
					
					int avail = 0;
					int term = 0;
					if(isAvailable.equalsIgnoreCase("yes")){
						avail = 1;
					}
					if(terminate.equalsIgnoreCase("yes")){
						term = 1;
					}
					
					Read.updateEmployee(alias, position, location, range, currency, avail, term);
					editFrame.dispose();
					createMainPanel();
				}
			});
			panel.add(saveButton, BorderLayout.SOUTH);
			editFrame.add(panel);
		}

	}

}
