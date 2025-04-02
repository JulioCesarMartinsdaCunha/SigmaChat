package net.sigmachatguys.messagemanage;

public class SRawMessage
{
    private int id = 0;
    private String message = "";

    public SRawMessage(int id, String message)
    {
        this.message = message;
        this.id = id;
    }

    public void excludeMessage()
    {
        message = null;
    }

    //GETTER&SETTER
    public String getMessage()
    {
        return message;
    }
    public int getId()
    {
        return id;
    }
}
