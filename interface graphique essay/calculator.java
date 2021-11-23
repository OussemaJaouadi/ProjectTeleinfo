import java.net.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
public class calculator extends JFrame implements ActionListener {
	// create a frame
	JFrame f;
	// create a textfield
	 JTextField l;
	client clientSocket =null;
	JLabel etatConnection;
	// store operator and operands
	String s0 , s1, s2;
	// main function
	 public calculator()
	{
		// create la frame principal
		f = new JFrame("calculator");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(100, 100, 220, 220);
		f.setTitle("Calculatrice");
		s0=s1=s2="";
		clientSocket = new client("localhost",8000);
		etatConnection = new JLabel("DECONNECTE");
		try {
			// set look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}

		// create a textfield
		l = new JTextField(16);
		// set the textfield to non editable
		l.setEditable(false);
		l.setBounds(5, 5, 210, 70);
		// create number buttons and some operators
		JButton b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, ba, bs, bd, bm, be, beq, beq1;

		// create number buttons
		b0 = new JButton("0");
		b1 = new JButton("1");
		b2 = new JButton("2");
		b3 = new JButton("3");
		b4 = new JButton("4");
		b5 = new JButton("5");
		b6 = new JButton("6");
		b7 = new JButton("7");
		b8 = new JButton("8");
		b9 = new JButton("9");

		// equals button
		beq1 = new JButton("=");
		// create operator buttons
		ba = new JButton("+");
		bs = new JButton("-");
		bd = new JButton("/");
		bm = new JButton("*");
		beq = new JButton("C");

		// create a panel
		JPanel p = new JPanel();
		// add action listeners
		bm.addActionListener(this);
		bd.addActionListener(this);
		bs.addActionListener(this);
		ba.addActionListener(this);
		b9.addActionListener(this);
		b8.addActionListener(this);
		b7.addActionListener(this);
		b6.addActionListener(this);
		b5.addActionListener(this);
		b4.addActionListener(this);
		b3.addActionListener(this);
		b2.addActionListener(this);
		b1.addActionListener(this);
		b0.addActionListener(this);
		beq.addActionListener(this);
		beq1.addActionListener(this);


		// add elements to panel
		p.add(l);
		p.add(ba);
		p.add(b1);
		p.add(b2);
		p.add(b3);
		p.add(bs);
		p.add(b4);
		p.add(b5);
		p.add(b6);
		p.add(bm);
		p.add(b7);
		p.add(b8);
		p.add(b9);
		p.add(bd);
		p.add(b0);
		p.add(beq);
		p.add(beq1);

		// set Background of panel
		p.setBackground(new Color(170,0,200));
		f.add(p);
		// CONCERNE LA PARTIE BAS DU FENETRE
    JPanel bottom = new JPanel();
    bottom.add(etatConnection);
    f.add(bottom, BorderLayout.SOUTH);
		etatConnection.setForeground(Color.RED);
		f.setSize(220, 220);
		f.setVisible(true);
		f.show();
		if (clientSocket.isConnected()) {
      etatConnection.setForeground(Color.GREEN);
      etatConnection.setText("Connected");
			f.show();
    }
		//fermer connectivite avec le serveur
		addWindowListener( new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent we) {
				if (clientSocket.isConnected()) {
          clientSocket.sendToServer("closing");
          clientSocket.close();
        }
      }
    } );
	}
	public void actionPerformed(ActionEvent e)
	{
		String s = e.getActionCommand();

		// if the value is a number
		if ((s.charAt(0) >= '0' && s.charAt(0) <= '9') ) {
			// if operand is present then add to second no
			if (!s1.equals(""))
				s2 = s2 + s;
			else
				s0 = s0 + s;

			// set the value of text
			l.setText(s0 + s1 + s2);
		}
		else if (s.charAt(0) == 'C') {
			// clear the one letter
			s0 = s1 = s2 = "";

			// set the value of text
			l.setText(s0 + s1 + s2);
		}
		else if (s.charAt(0) == '=') {

			clientSocket.sendToServer(s0+s1+s2);
			String te = clientSocket.getResult();
			// set the value of text
			l.setText(s0 + s1 + s2 + "=" + te);
			s0 = te;
			s1 = s2 = "";
		}
		else {
			// if there was no operand
			if (s1.equals("") || s2.equals(""))
				s1 = s;
			// else evaluate
			else {
				clientSocket.sendToServer(s0+s1+s2);
				String te = clientSocket.getResult();
				s0 = te;
				// place the operator
				s1 = s;
				// make the operand blank
				s2 = "";
			}
			// set the value of text
			l.setText(s0 + s1 + s2);
		}
	}
	public static void main(String[] args) {
		calculator c = new calculator();
	}

}
