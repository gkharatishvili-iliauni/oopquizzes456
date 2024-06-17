package quiz5;

import java.util.List;

public class DummyCommunicationManager {
    public String getChatbotResponse(String userMessage, List<ChatMessage> chatHistory) {
        String simulatedResponse = simulateResponse(userMessage);
        return simulatedResponse;
    }

    private String simulateResponse(String userMessage) {
        switch (userMessage.toLowerCase()) {
            case "hello":
                return "Good day";
            case "what time is it?":
                return "9:00 AM";
            case "i should go!":
                return "Wait for me!";
            default:
                return "I'm sorry, I don't understand.";
        }
    }
}
