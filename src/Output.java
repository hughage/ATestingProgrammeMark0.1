
import java.io.PrintWriter;

public class Output {
	
	Output(User user){
		try{
		    PrintWriter writer = new PrintWriter(user.name, "UTF-8");
		    writer.println(user.name);
		    writer.println(user.age);
		    writer.println(user.sex);
		    writer.close();
		} catch (Exception e) {
		   // do something
		}
	}

}
