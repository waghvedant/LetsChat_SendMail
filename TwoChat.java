package ProPro;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;

import com.email.durgesh.Email;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import java.io.FileWriter;
import java.io.IOException;
public class TwoChat extends javax.swing.JFrame {
 
    /**

*/
private static final long serialVersionUID = 1L;
ServerSocket ss;
    // ServerSocket Class is used for providing system-independent
    // implementation of the server-side of a client/server Socket Connection.
    Socket s;
    //Socket class represents the socket that both
    //the client and the server use to communicate with each other
   
    // Given is list from tools in frame (their classes and objects)
    JTextField text=new JTextField();
    JButton send=new JButton();
    JButton upload = new JButton();
    JTextArea messages=new JTextArea();
    PrintWriter pout;
    BufferedReader br;
    ActionListener al,bl;
    String ipstring;
    boolean ready2send=false;
    TwoChat pt;
    boolean HorC;
    String cliOrServ;

    public TwoChat(boolean hostOrConnect,String ip)
    {
        ipstring=ip;   // Convert Ip from String to Long
       
        // given are the window event handling and frame structure class
        setLayout(null);
        setSize(600,550);
        setTitle("Client-Server Email System");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(text);
        text.setLocation(5,5);
        text.setSize(getWidth()-40-60,30);
        text.setEnabled(false);
        add(send);
        send.setLocation(10+text.getWidth(),text.getY());
        send.setText("Send");
        send.setSize(70,30);
        add(messages);
        messages.setEditable(false);
        messages.setBorder(new EtchedBorder());
        messages.setLocation(5,text.getHeight()+text.getY()+5);
        messages.setSize(getWidth()-70,getHeight()-text.getY()-text.getHeight()-100);
        add(upload);
        upload.setText("Upload");
       
        upload.setLocation(15,messages.getHeight()+messages.getY()+15);
        upload.setSize(100,40);
   

       
 al=new ActionListener()
 {
 public void actionPerformed(ActionEvent e)
  {
  ready2send=true;
  }
 };      
 send.addActionListener(al);
 
        pt=this;
        HorC=hostOrConnect;
        if(HorC)
            cliOrServ="\nClient: ";   // replacement of clinet and server name
        else
            cliOrServ="\nServer: ";
        Messenger.start();
       
   bl = new  ActionListener()
  {
        public void actionPerformed(ActionEvent e)
    {
    String s = e.getActionCommand();
    if (s.equals("Upload")) {
    String file = "InputFiles\\new.txt";
    FileWriter fileWriter;
    try {
    fileWriter = new FileWriter(file);
   
    messages.write(fileWriter);
         fileWriter.close();

    } catch (IOException e1) {
    // TODO Auto-generated catch block
    e1.printStackTrace();
    }
       
    // set the text of field to blank
 
    try{  
    	Class.forName("oracle.jdbc.driver.OracleDriver");
		// Loading driver
		
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","123");  
   
    String SQL="INSERT INTO storetextfile_table(file_name,file_extension,file_content)VALUES(?,?,?)";
    Path dir = Paths.get("C:\\Users\\Vedant Wagh\\eclipse-workspace\\OOPsProject\\InputFiles");
    try(Stream<Path> list = Files.list(dir);
   
    PreparedStatement ps = con.prepareStatement(SQL)) {

    List<Path> pathList = list.collect(Collectors.toList());
    System.out.println("Following files are saved in database..");
    for (Path path : pathList) {
    System.out.println(path.getFileName());
    File file1 = path.toFile();
   
    String fileName = file1.getName();
    long fileLength = file1.length();
    

   
    ps.setString(1, fileName);
    

    ps.setString(2, fileName.substring(fileName.lastIndexOf(".")+1));
    ps.setCharacterStream(3, new FileReader(file), fileLength);

    ps.addBatch();
    }
   
    System.out.println("----------------------------------------");
    int[] executeBatch = ps.executeBatch();
    for (int i : executeBatch) {
    System.out.println(i);
    }
    }
   
    } catch (IOException ex) {
    ex.printStackTrace();
    }
    catch (Exception ee)
    {
    ee.printStackTrace();
    }
    try {
    	Multipart emailContent = new MimeMultipart();
    	
    	Email email = new Email("lavanyadhole206@gmail.com","Vedant@123");
    	email.setFrom("lavanyadhole206@gmail.com", "Java Mail");
    	email.setSubject("Client-Server Chat");
    	email.setContent("<h1></h1>", "text/html");
        
    	
         //Attachment body part..
    	MimeBodyPart pdfAttachment = new MimeBodyPart();
    	pdfAttachment.attachFile("InputFiles\\\\new.txt");
        
    	emailContent.addBodyPart(pdfAttachment);
    	
    	email.setContent(emailContent, null);
    	email.addRecipient("waghvedant957@gmail.com");
    	email.send();
    	JOptionPane.showMessageDialog(null,"File Successfully Uploaded..","PopUp Dialog",JOptionPane.INFORMATION_MESSAGE);
    	
    } catch (Exception em)
    {
    	em.printStackTrace(); 
    }
    }
    }
    };
    
    upload.addActionListener(bl);    
    }
   
   
 
    public static void main(String args[]) {

        // dialog box to allow act as a client or server
        int inp=JOptionPane.showConfirmDialog(null,"Do you want to host the chat?\nYes - Act as server\nNo - Act as client","Want to host a chat?",JOptionPane.YES_NO_OPTION);
        if(inp==0)
            {
            new TwoChat(true,null).setVisible(true);
            }
        else
            {
            String ipstring=JOptionPane.showInputDialog("Please Enter the IP Address :) ");
            try{
                InetAddress.getByName(ipstring);  // getting their IP adress
                new TwoChat(false,ipstring).setVisible(true);
            }catch(Exception e){JOptionPane.showMessageDialog(null,"Invalid or Unreachable IP");}
            }
       
    }

Thread Messenger=new Thread()
{
 public void run()
 {
  try{
  if(HorC)
        {
        messages.setText("Waiting for an incoming connection.\nEnter my ip at client side.\nMy ip: "+InetAddress.getLocalHost().getHostAddress());
        ss=new ServerSocket(9999);  // creating object and sending request to client to get connection
        s= ss.accept();
        s.setKeepAlive(true);
        }
  else
        {
        messages.setText("Connecting to:"+ipstring+":9999");
        s=new Socket(InetAddress.getByName(ipstring),9999);
        // Inetaddress  methods provides methods to get the IP address of any hostname.
        }
  text.setEnabled(true);
  pout = new PrintWriter(s.getOutputStream(),true);
  br=new BufferedReader(new InputStreamReader(s.getInputStream()));
  // InputStreamReader reads bytes and decodes them into characters using a specified charset.
  // getInputStream get the string
 
  messages.setText(messages.getText()+"\nConnected to:"+s.getInetAddress().getHostAddress()+":"+s.getPort());
  // getInetAddress class represents an Internet Protocol (IP) address.
  // getHostAddress method of InetAddress class returns the IP address string in textual presentation
 
  while(true)
  {
   if(ready2send==true)
        {
        pout.println(text.getText());
        messages.setText(messages.getText()+"\nMe: "+text.getText());
        text.setText("");
        ready2send=false;
        }
    if(br.ready())
        {
        messages.setText(messages.getText()+cliOrServ+br.readLine());
        }
    Thread.sleep(80);  // waiting for connection from client till some time
  }
 }catch(Exception ex)
    {
    JOptionPane.showMessageDialog(pt,ex.getMessage());
    messages.setText("Cannot connect!");
      try {
          wait(3000);  //waiting for connection from client till some time
      } catch (InterruptedException ex1) {}
     
      //  This Exception is used when a thread is waiting or sleeping
      // and another thread interrupts it using the interrupt method in class Thread
    System.exit(0);
    }
 }
};

}




