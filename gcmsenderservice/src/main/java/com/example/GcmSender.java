//package com.example;

package com.example;

        import org.apache.commons.io.IOUtils;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;
        import java.lang.Object;
        import java.net.HttpURLConnection;
        import java.net.InetSocketAddress;
        import java.net.Proxy;
        import java.net.URL;

/**
 * Created by y424 on 23/09/2016.
 */

public class GcmSender {
    public static final String API_KEY = "AIzaSyCmjfLbwq8jT4askR9yIKv-To5ZmUDd50g";

    public static void main(String [] args){
        try {
            // Prepare JSON containing the GCM message content. What to send and where to send.
            JSONObject jGcmData = new JSONObject();
            JSONObject jData = new JSONObject();
            jData.put("message", args[0].trim());
            // Where to send GCM message.
            if (args.length > 1 && args[1] != null) {
                jGcmData.put("to", args[1].trim());
            } else {
                jGcmData.put("to", "/topics/global");
            }
            // What to send in GCM message.
            jGcmData.put("data", jData);

            // Create connection to send GCM Message request.
            URL url = new URL("https://android.googleapis.com/gcm/send");
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("ilproxy1.europa.internal", 8080));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
            conn.setRequestProperty("Authorization", "key=" + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Send GCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jGcmData.toString().getBytes());

            // Read GCM response.
            InputStream inputStream = conn.getInputStream();
            String resp = IOUtils.toString(inputStream);
            System.out.println(resp);
            System.out.println("Check your device/emulator for notification or logcat for " +
                    "confirmation of the receipt of the GCM message.");
        } catch (IOException e) {
            System.out.println("Unable to send GCM message.");
            System.out.println("Please ensure that API_KEY has been replaced by the server " +
                    "API key, and that the device's registration token is correct (if specified).");
            e.printStackTrace();
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }
}
