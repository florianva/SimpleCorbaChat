package chat;


/**
* chat/ChatClientOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from chat.idl
* mercredi 24 mai 2017 15 h 42 CEST
*/

public interface ChatClientOperations 
{
  String getUserName ();
  void addMSG (String msg);
  void updateConnectedUsers (String[] names);
} // interface ChatClientOperations
