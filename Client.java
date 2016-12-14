import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client{

	private static BufferedReader in;
	private static PrintWriter out;
	private static Socket sock;

	public static void main(String [] args) throws IOException{
		
		int portNumber = Integer.parseInt(args[0]);
		
		sock = new Socket("localhost", portNumber);
		in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		out = new PrintWriter(sock.getOutputStream(), true);
		
		//get over the welcome messages
		for(int i=0; i<2; i++)
			System.out.println(in.readLine());	
	
		Scanner scan = new Scanner(System.in);

		while(true){
			System.out.print("Input: ");
			String line = scan.nextLine();
			out.println(line);
			System.out.println("Response: "+in.readLine());
		}

	}
}
