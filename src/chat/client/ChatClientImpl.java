package chat.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.rmi.RemoteException;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import chat.ChatClient;
import chat.ChatClientHelper;
import chat.ChatClientPOA;
import chat.ChatServer;
import chat.ChatServerHelper;

public class ChatClientImpl extends ChatClientPOA{

	private String name;
	private ORB orb;
	ChatClient cref;
	ChatServer server;
	private ChatClientFrame frm ;
	
	public ChatClientImpl(String name, String[] args, ChatClientFrame f) {
		// TODO Auto-generated constructor stub
		frm = f ;
		try{
			this.name = name;
			
			orb = ORB.init(args, null);
			Thread orbThread = new Thread(new Runnable(){
				@Override
				public void run(){
					orb.run();
				}
			});
			orbThread.start();
			
			POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootPOA.the_POAManager().activate();
			org.omg.CORBA.Object ref = rootPOA.servant_to_reference(this);
			cref = ChatClientHelper.narrow(ref);
			
			BufferedReader br = new BufferedReader(new FileReader("server_ior.txt"));
			String ior = br.readLine();
			br.close();
			
			org.omg.CORBA.Object o = orb.string_to_object(ior);
			server = ChatServerHelper.narrow(o);
			
			server.register(cref);
			frm.setTitle("Connected as :" + name);
			
			//buildUI();
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Override
	public String getUserName() {
		System.out.println(name + " sending user name !");
		return name ;
	}

	@Override
	public void addMSG(String msg) {
		System.out.println(name + " adding msg : " + msg);
		frm.appendMsg(msg) ;
	}

	@Override
	public void updateConnectedUsers(String[] names) {
		System.out.println(name + " updating connected user names !");
		frm.users.setListData(names); ;
	}
	
	public void sendToAll(String msg) {
		server.sendToAll(msg, cref) ;
	}
	
	public void unRegister() {
		server.unregister(cref) ;
	}
}
