package chat;


/**
* chat/ChatServerHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from chat.idl
* mercredi 24 mai 2017 15 h 42 CEST
*/

abstract public class ChatServerHelper
{
  private static String  _id = "IDL:chat/ChatServer:1.0";

  public static void insert (org.omg.CORBA.Any a, chat.ChatServer that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static chat.ChatServer extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (chat.ChatServerHelper.id (), "ChatServer");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static chat.ChatServer read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_ChatServerStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, chat.ChatServer value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static chat.ChatServer narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof chat.ChatServer)
      return (chat.ChatServer)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      chat._ChatServerStub stub = new chat._ChatServerStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static chat.ChatServer unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof chat.ChatServer)
      return (chat.ChatServer)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      chat._ChatServerStub stub = new chat._ChatServerStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
