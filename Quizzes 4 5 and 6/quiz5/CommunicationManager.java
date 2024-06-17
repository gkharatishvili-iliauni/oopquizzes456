package quiz5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class CommunicationManager {
    private static final String CHATBOT_API_URL = "http://your-chatbot-service/api/chat"; // Replace with your chatbot API URL

    public String getChatbotResponse(String userMessage, List<ChatMessage> chatHistory) throws Exception {
        URL url = new URL(CHATBOT_API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        JSONObject jsonInput = new JSONObject();
        jsonInput.put("message", userMessage);
        JSONArray historyArray = new JSONArray();
        for (ChatMessage msg : chatHistory) {
            JSONObject historyItem = new JSONObject();
            historyItem.put("sender", msg.getSender());
            historyItem.put("text", msg.getText());
            historyArray.add(historyItem);
        }
        jsonInput.put("history", historyArray);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInput.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }

        JSONObject jsonResponse = (JSONObject) new org.json.simple.parser.JSONParser().parse(response.toString());
        return (String) jsonResponse.get("response");
    }
}
