package net.sigmachatguys.messagemanage;

import java.util.ArrayList;

public class MessageManage
{
    final private ArrayList<RawMessage> messages = new ArrayList<RawMessage>();
    private boolean haveNewMessage = false;


    public void setNewMessage(String message)
    {
        RawMessage newMessage = new RawMessage(messages.size(), message);
        messages.add(newMessage);
        haveNewMessage = true;
    }

    public RawMessage getMessage(int id)
    {
        RawMessage newRawMessage = null;
        for (RawMessage rawMessage : messages)
        {
            if (rawMessage.getId() == id)
            {
                newRawMessage = rawMessage;
            }
        }

        haveNewMessage = false;
        return newRawMessage;
    }

    public RawMessage getLastMessage()
    {
        haveNewMessage = false;
        return messages.getLast();
    }

    public boolean isHaveNewMessage()
    {
        return haveNewMessage;
    }
}
