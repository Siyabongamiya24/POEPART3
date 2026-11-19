package com.mycompany.quickchatpoe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageManagerTest {
    MessageManager manager = new MessageManager();

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        manager.populateTestData();
        String expected = "Did you get the cake?\nIt is dinner time!";
        StringBuilder actual = new StringBuilder();
        for (int i = 0; i < manager.sentMessages.size(); i++) {
            actual.append(manager.sentMessages.get(i).getMessage());
            if (i < manager.sentMessages.size() - 1) actual.append("\n");
        }
        assertEquals(expected, actual.toString());
    }

    @Test
    public void testDisplayLongestMessage() {
        manager.populateTestData();
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                manager.displayLongestStoredMessage());
    }

    @Test
    public void testSearchForMessageID() {
        manager.populateTestData();
        String result = manager.searchByMessageID("0838884567");
        assertTrue(result.contains("It is dinner time!"));
        assertTrue(result.contains("0838884567"));
    }

    @Test
    public void testSearchMessagesForRecipient() {
        manager.populateTestData();
        String result = manager.searchByRecipient("+27838884567");
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testDeleteMessageByHash() {
        manager.populateTestData();
        String hash = manager.storedMessages.get(0).getMessageHash();
        String result = manager.deleteByHash(hash);
        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.", result);
    }

    @Test
    public void testDisplayReport() {
        manager.populateTestData();
        String report = manager.displayReport();
        assertTrue(report.contains("Message Hash"));
        assertTrue(report.contains("Recipient"));
        assertTrue(report.contains("Message"));
        assertTrue(report.contains("+27838884567"));
    }
}