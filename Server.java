import java.io.*;
import java.net.*;
import java.util.Date;

public class Server extends Thread{

	private Socket sock;
	private static int clientNumber;


	public Server(Socket sock, int clientNumber){
		this.sock = sock;
		this.clientNumber = clientNumber;
		log("New Client Connection: " + clientNumber + " has been made");
	}

	public void run(){
	try{
			BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			PrintWriter out = new PrintWriter(sock.getOutputStream(),true);
		
			//send a message to the clien
			out.println("Welcome Client " + clientNumber + " I am an angry echoing server!");
			out.println("Send me a message, and I will respond");

			//Get the message from the client
			while(true){
		
				String input = in.readLine();
				if(input == null || input.equals(".")){
					break;
				}
				out.println(input.toUpperCase());

			}
		}
		catch(Exception e){
			log("error handling client");
		}
		finally{
			try{sock.close();}
			catch(Exception e){log("couldn't close socket");}
			log("Connection with "+clientNumber+" closed");
		}
	}

	private void log(String str){
		System.out.println(str);
	}

	public static void main(String [] args) throws IOException{
	
		int portNumber = Integer.parseInt(args[0]);
		ServerSocket listener = new ServerSocket(portNumber);
		try{
			
			while(true){
				new Server(listener.accept(),clientNumber++).start();
			}
	
		}
		finally{
			listener.close();
		}
	}
}
