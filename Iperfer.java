import java.net.*;
import java.io.*;
class Iperfer{
    public static void main(String[] args){
	int counter = 0;
	byte[] b = new byte[1000]; 
	OutputStream out = null;
	String flag = args[0];
	if(flag== "-c"){
	    String hostName = args[1];
	    int port = Integer.parseInt(args[2]);
	    int time = Integer.parseInt(args[3]);
	    //converting time in seconds to nanoseconds
	    double nanTime = time * .000000001;
	    //create new socket object for the client
	    try{
		Socket clien = new Socket(hostName, port);
		out = clien.getOutputStream();
	    }catch (IOException e){
		System.err.println("Caught IOException: " + e.getMessage());
	    }
	    
	    long startTime = System.nanoTime();
	    //while still time remaining send 1000 byte array and increment
	    //counter
	    while (System.nanoTime() - startTime < nanTime){
		try{
		    out.write(b);
		}catch (IOException e){
		    System.err.println("Caught IOException: " + e.getMessage());
		}
		counter++;
	    }
	    //number of kilobytes sent will be i
	    double rate = counter/time;
	    System.out.println("sent=" + counter +" KB rate =" + rate/1000 + " Mbps");
	}
	if(flag == "-s"){
	    int sPort = Integer.parseInt(args[1]);
	    ServerSocket serv = new ServerSocket();
	    serv.bind(sPort);
	    InputStream stdin;
	    try{
		Socket server = serv.accept();
		stdin = server.getInputStream();
	    }catch (IOException e){
                System.err.println("Caught IOException: " + e.getMessage());
            }
            long startTime = System.nanoTime();
	    while (serv.getInputStream().available() > 0) {
         	int ready = serv.getInputStream().available();
         	byte[] bytes = new byte[1000];
         	serv.getInputStream().read(bytes);
         	counter++;
	    }
	    long elapsed = System.nanoTime() - startTime;
	    double rate = counter/elapsed;
	    System.out.println("recieved=" + counter +" KB rate =" + rate/1000 + " Mbps");
	}
    }
}
