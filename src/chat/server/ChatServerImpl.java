package chat.server;

import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import chat.ChatClient;
import chat.ChatServerPOA;

public class ChatServerImpl extends ChatServerPOA{

	ORB orb;
	
	private ChatClient client = null;
	HashMap<ChatClient, String> clients =  new HashMap<ChatClient, String>() ;

	
	
	public ChatServerImpl(String[] args) {
		// TODO Auto-generated constructor stub
		try{
			orb = ORB.init(args, null);
			Thread orbThread = new Thread(new Runnable() {
				@Override
				public void run(){
					orb.run();
				}
			});
			orbThread.start();
			
			POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootPOA.the_POAManager().activate();
			byte[] id = rootPOA.activate_object(this);
			
			org.omg.CORBA.Object ref = rootPOA.id_to_reference(id);
			String ior = orb.object_to_string(ref);
			System.out.println(ior);
			PrintWriter file = new PrintWriter("server_ior.txt");
			file.println(ior);
			file.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	

	private void updateConnectedUsers() throws RemoteException{

		Object objs[] = clients.values().toArray() ; 
		String[] names = new String[objs.length] ;
		for (int i=0 ; i<objs.length ; i++) names[i] = (String)objs[i] ;

		ChatClient tmp = null ; // pour retirer l'objet deconnecte en cas d'exception...
		for (ChatClient client : clients.keySet()){
			tmp = client ;
			tmp.updateConnectedUsers(names) ;
		}
	}
	
	
	
	
	@Override
	public void register(ChatClient c) {
		System.out.println("Server registering " + c.getUserName());
		clients.put(c, c.getUserName()) ;
		try {
			updateConnectedUsers() ;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void unregister(ChatClient c) {
		System.out.println("Server unregistering " + c.getUserName());
		clients.remove(c) ;
		try {
			updateConnectedUsers() ;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void sendToAll(String msg, ChatClient from) {
		String senderName = clients.get(from) ;
		System.out.println("Server sending " + msg + " from " + senderName);
		
		Vector<ChatClient> disconnected = new Vector<ChatClient>() ;
		for(ChatClient c : clients.keySet()){
			c.addMSG(senderName + " > " + msg + "\n") ;
		}
		
		// nettoyage de la liste
		if (!disconnected.isEmpty()) {
			for(ChatClient c : disconnected){
				System.out.println(c + " est deconnecte !");
				clients.remove(c) ;
			}
			try {
				updateConnectedUsers() ;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new ChatServerImpl(args) ;
	}
	
}
