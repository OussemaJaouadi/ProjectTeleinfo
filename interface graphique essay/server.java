import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class server{
	static ServerSocket ss;
	static Socket s;
	static DataInputStream inputText ;
	static DataOutputStream outputText;

	public static void main(String args[]) throws IOException{
		try{
		//Establish the socket connection.
		ss = new ServerSocket(8000);
		System.out.println("server fonctionne");
		}catch(Exception e){
		System.out.println(e);
		}
		while (true)
		{
			s = ss.accept();
			System.out.println("Client connected");
			//Processing the request.
			inputText = new DataInputStream(s.getInputStream());
			outputText = new DataOutputStream(s.getOutputStream());
			// wait for input
			System.out.println("wait and input");
			String input = inputText.readUTF();

			if(input.equals("closing"))
				break;

			System.out.println("Equation received:-" + input);
			int result;

			// Use StringTokenizer to break the equation into operand and
			// operation
			StringTokenizer st = new StringTokenizer(input);

			int oprnd1 = Integer.parseInt(st.nextToken());
			String operation = st.nextToken();
			int oprnd2 = Integer.parseInt(st.nextToken());

			// perform the required operation.
			if (operation.equals("+"))
			{
				result = oprnd1 + oprnd2;
			}

			else if (operation.equals("-"))
			{
				result = oprnd1 - oprnd2;
			}
			else if (operation.equals("*"))
			{
				result = oprnd1 * oprnd2;
			}
			else
			{
				result = oprnd1 / oprnd2;
			}
			System.out.println("Sending the result...");

			// send the result back to the client.
			outputText.writeUTF(Integer.toString(result));
		}
	}
}
