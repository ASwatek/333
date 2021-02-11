package Application;

import Views.View;

public class AppMain {

	public static void main(String[] args) throws Exception{
		Login login = new Login();
		View employeeView = login.getEmployeeView();
		while(employeeView == null){
			employeeView = login.getEmployeeView();
			Thread.sleep(10); //needed for it to exit the while loop
		}
		employeeView.open();
	}
}
