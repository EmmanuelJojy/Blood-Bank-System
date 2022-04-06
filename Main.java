public class Main {
	static String uid = "";
	static boolean printflag = true;

	public static void main(String[] args) {

		System.out.print("\n= Attempting DB connection...  ");
		DB.connect();

		System.out.print("= Triggering GUI... ");
		new GUI();		
		System.out.println("Complete\n");
				
	}
}