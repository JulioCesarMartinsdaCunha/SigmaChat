package net.sigmachatguys.messagemanage;

import java.util.ArrayList;

public class SMessageManage
{
    final private ArrayList<SRawMessage> messages = new ArrayList<SRawMessage>();
    private boolean haveNewMessage = false;


    public void setNewMessage(String message)
    {
        SRawMessage newMessage = new SRawMessage(messages.size(), message);
        messages.add(newMessage);
        haveNewMessage = true;
    }

    public SRawMessage getMessage(int id)
    {
        SRawMessage newRawMessage = null;
        for (SRawMessage rawMessage : messages)
        {
            if (rawMessage.getId() == id)
            {
                newRawMessage = rawMessage;
            }
        }
        haveNewMessage = false;
        return newRawMessage;
    }

    public SRawMessage getLastMessage()
    {
        return messages.getLast();
    }

    public boolean isHaveNewMessage()
    {
        return haveNewMessage;
    }
}
