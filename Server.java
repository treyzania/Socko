import java.io.*;
import java.net.*;
import java.util.Date;

public class Server extends Thread{

	private Socket sock;
	private static int clientNumber;


	public Server(Socket sock, int clientNumber){
		this.sock = sock;
		this.clientNumber = clientNumber;
		log("New Client Connection: #" + clientNumber + " has been made");
	}
	
	//runs a new thread
	public void run(){
		//creates a connection from the server to the client
		try{
			//gets the input from the socket
			BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			//sends the output via the socket connected to the client
			PrintWriter out = new PrintWriter(sock.getOutputStream(),true);
		
			//send a welcome message to the client
			out.println("Welcome Client " + clientNumber + " I am an angry echoing server!");
			out.println("Send me a message, and I will respond");

			//Get the message from the client
			while(true){
				//get input from the client
				String input = in.readLine();
				
				//if the user sends the messsage "exit", end the session between the clinet and server
				if(input == null || input.equals("exit")){
					break;
				}

				//otherwise, return the message they sent in all caps
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
	
	//print a message to the server console incase
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
