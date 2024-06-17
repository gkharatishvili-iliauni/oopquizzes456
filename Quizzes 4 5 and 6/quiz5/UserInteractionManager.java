package quiz5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UserInteractionManager {
    private DummyCommunicationManager communicationManager;
    private List<ChatMessage> chatHistory;

    public UserInteractionManager() {
        this.communicationManager = new DummyCommunicationManager();
        this.chatHistory = new ArrayList<>();
    }

    public void startChat() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to the Chatbot. Type 'exit' to end the chat.");
        
        while (true) {
            try {
                System.out.print("You: ");
                String userMessage = reader.readLine();

                if ("exit".equalsIgnoreCase(userMessage)) {
                    System.out.println("Bot: Goodbye!");
                    break;
                }

                chatHistory.add(new ChatMessage("User", userMessage));
                String botResponse = communicationManager.getChatbotResponse(userMessage, chatHistory);
                chatHistory.add(new ChatMessage("Bot", botResponse));
                System.out.println("Bot: " + botResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        UserInteractionManager userManager = new UserInteractionManager();
        userManager.startChat();
    }
}

// CommunicationManager class which sends a request to the url

// public class CommunicationManager {
//     private static final String CHATBOT_API_URL = "http://chatbot-service/api/chat"; // Replace with chatbot API URL

//     public String getChatbotResponse(String userMessage, List<ChatMessage> chatHistory) throws Exception {
//         URL url = new URL(CHATBOT_API_URL);
//         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//         conn.setRequestMethod("POST");
//         conn.setRequestProperty("Content-Type", "application/json; utf-8");
//         conn.setRequestProperty("Accept", "application/json");
//         conn.setDoOutput(true);

//         JSONObject jsonInput = new JSONObject();
//         jsonInput.put("message", userMessage);
//         JSONArray historyArray = new JSONArray();
//         for (ChatMessage msg : chatHistory) {
//             JSONObject historyItem = new JSONObject();
//             historyItem.put("sender", msg.getSender());
//             historyItem.put("text", msg.getText());
//             historyArray.add(historyItem);
//         }
//         jsonInput.put("history", historyArray);

//         try (OutputStream os = conn.getOutputStream()) {
//             byte[] input = jsonInput.toString().getBytes("utf-8");
//             os.write(input, 0, input.length);
//         }

//         StringBuilder response = new StringBuilder();
//         try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
//             String responseLine;
//             while ((responseLine = br.readLine()) != null) {
//                 response.append(responseLine.trim());
//             }
//         }

//         JSONObject jsonResponse = (JSONObject) new org.json.simple.parser.JSONParser().parse(response.toString());
//         return (String) jsonResponse.get("response");
//     }
// }