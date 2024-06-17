package quiz6;

import java.util.ArrayList;
import java.util.List;

public class SpecialCommunicationManager {
    private String commonServiceUrl;
    private String specialServiceUrl;
    private List<String> conversationHistory;

    public SpecialCommunicationManager(String commonServiceUrl, String specialServiceUrl) {
        this.commonServiceUrl = commonServiceUrl;
        this.specialServiceUrl = specialServiceUrl;
        this.conversationHistory = new ArrayList<>();
    }

    public String sendMessage(String userMessage) {
        conversationHistory.add(userMessage);

        boolean containsHelp = userMessage.toLowerCase().contains("help");
        if (!containsHelp) {
            for (String message : conversationHistory) {
                if (message.toLowerCase().contains("help")) {
                    containsHelp = true;
                    break;
                }
            }
        }

        if (containsHelp) {
            return specialServiceUrl;
        } else {
            return commonServiceUrl;
        }
    }

    public static void main(String[] args) {
        SpecialCommunicationManager manager = new SpecialCommunicationManager(
                "http://common.service.url", "http://special.service.url");
        System.out.println(manager.sendMessage("Hello")); // should use commonServiceUrl
        System.out.println(manager.sendMessage("I need help with this"));  // should use specialServiceUrl
        System.out.println(manager.sendMessage("What time is it?")); // should still use specialServiceUrl because "help" is in history
    }
}
