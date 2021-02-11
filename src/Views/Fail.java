package Views;

public class Fail implements View {

	@Override
	public void open() {
		System.out.println("Login failed too many times.\nAccount is gone and so are you.");
	}

}
