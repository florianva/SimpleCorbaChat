package chat;


/**
* chat/ConnectedUsersHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from chat.idl
* mercredi 24 mai 2017 15 h 42 CEST
*/

public final class ConnectedUsersHolder implements org.omg.CORBA.portable.Streamable
{
  public String value[] = null;

  public ConnectedUsersHolder ()
  {
  }

  public ConnectedUsersHolder (String[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = chat.ConnectedUsersHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    chat.ConnectedUsersHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return chat.ConnectedUsersHelper.type ();
  }

}
