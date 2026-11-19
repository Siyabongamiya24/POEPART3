package com.mycompany.quickchatpoe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        String longMsg = "This is a very long message that is definitely more than two hundred and fifty characters long. "
                                + "We are adding more words here just to make sure it goes over the limit. "
                                + "If this message is still not long enough then the test will fail, so we keep typing and typing until we are sure it is over 250 characters for sure. Adding even more words now.";
        Message msg = new Message(0, "+27718693002", longMsg);
        // Your Message.java says "exceeds 250 characters by X" - so we use contains, not equals
        assertTrue(msg.checkMessageLength().contains("Message exceeds 250 characters"));
    }

    @Test
    public void testRecipientSuccess() {
        Message msg = new Message(0, "+27718693002", "Hi");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testRecipientFailure() {
        Message msg = new Message(0, "0718693002", "Hi");
        assertTrue(msg.checkRecipientCell().contains("incorrectly formatted"));
    }

    @Test
    public void testCheckMessageID() {
        Message msg = new Message(0, "+27718693002", "Hi");
        assertTrue(msg.checkMessageID());
        assertEquals(10, msg.getMessageID().length());
    }

    @Test
    public void testCreateMessageHash() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        msg.setMessageIDForTest("0012345678");
        // Your hash = first 2 of ID + ":" + number + ":" + FIRST + LAST
        // = 00 : 0 : HI + TONIGHT
        assertEquals("00:0:HITONIGHT", msg.createMessageHash());
    }

    @Test
    public void testSentMessageSend() {
        Message msg = new Message(0, "+27718693002", "Hi");
        assertEquals("Message successfully sent.", msg.SentMessage(1));
    }

    @Test
    public void testSentMessageDisregard() {
        Message msg = new Message(0, "+27718693002", "Hi");
        // Your code has "the" in it
        assertEquals("Press 0 to delete the message.", msg.SentMessage(2));
    }

    @Test
    public void testSentMessageStored() {
        Message msg = new Message(0, "+27718693002", "Hi");
        assertEquals("Message successfully stored.", msg.SentMessage(3));
    }
}