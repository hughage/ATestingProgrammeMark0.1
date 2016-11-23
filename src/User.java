import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class User {
	
	Scanner user_input = new Scanner( System.in );
	
	String name;
	int age;
	String sex;
	int[] pMax = new int[4];
	int avPMax; 
	String hand;
	
	boolean userDone = false;

	
	
	User(){
		getUserInfo();
	}
	
	private void getUserInfo(){
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		   Date date = new Date();
		   System.out.println(dateFormat.format(date));
		   
		name = dateFormat.format(date);
		System.out.println("Age:");
		this.age = user_input.nextInt( );
		System.out.println("Male?:");
		this.sex= user_input.next( );
		this.hand = user_input.next( );
		userDone = true; 
		
	}
	
	public String getName(){
		if (userDone){
			return name; 
		} else {
			String t = "User Input Not finished";
			return t;
		}
	}
	
	public int getAge(){
		if (userDone){
			return age; 
		} else {
			return 0;
		}
	}
	
	public String getSex(){
		if (userDone){
			return sex; 
		} else {
			String t = "User Input Not finished";
			return t;
		}
	}

}
