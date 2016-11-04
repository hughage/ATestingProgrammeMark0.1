
public class ATestingMain{ 
	
///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static User user; //user info: name, age, sex etc
	static Output output; //thing that creates txt file output

	//static LeapData leap; //get leap data
	static MyWindow myWindows;
	

	public static void main(String[] args) {
		
		
		myWindows = new MyWindow(); 
		user = new User();
		output = new Output(user);
		
	}

}
