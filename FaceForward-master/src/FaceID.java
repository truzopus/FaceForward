import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;

public class FaceID {
            // Replace <Subscription Key> with your valid subscription key.
            private static final String subscriptionKey = "f99a86bd80cd440c8b75d84743b90df2";

            private static final String uriBase =
                    "https://westcentralus.api.cognitive.microsoft.com/face/v1.0/detect";

            private static final String imageWithFaces =
                    "{\"url\":\"https://upload.wikimedia.org/wikipedia/commons/c/c3/RH_Louise_Lillian_Gish.jpg\"}";

            private static final String faceAttributes =
                    "age,gender,headPose,smile,facialHair,glasses,emotion,hair,makeup,occlusion,accessories,blur,exposure,noise";


    public static String FaceRecognize(String photoString, Boolean local){
        HttpClient httpclient = HttpClientBuilder.create().build();
        try {
            URIBuilder builder = new URIBuilder(uriBase);
            // Request parameters. All of them are optional.
            builder.setParameter("returnFaceId", "true");
            builder.setParameter("returnFaceLandmarks", "false");
            builder.setParameter("returnFaceAttributes", faceAttributes);

            // Prepare the URI for the REST API call.
            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);

            request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

            // Request headers.
            if (local) {
                request.setHeader("Content-Type", "application/octet-stream");
                File file = new File(photoString);
                FileEntity reqEntity = new FileEntity(file, ContentType.APPLICATION_OCTET_STREAM);
                request.setEntity(reqEntity);
            } else {
                request.setHeader("Content-Type", "application/json");
                StringEntity reqEntity = new StringEntity(photoString);
                request.setEntity(reqEntity);
            }
            // Request body.

            // Execute the REST API call and get the response entity.
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null)
            {
                // Format and display the JSON response.
                System.out.println("REST Response:\n");

                String jsonString = EntityUtils.toString(entity).trim();
                if (jsonString.charAt(0) == '[') {
                    JSONArray jsonArray = new JSONArray(jsonString);
                    System.out.println(jsonArray.toString(2));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject.has("faceId")) {
                            return jsonObject.getString("faceId");
                        }
                    }
                }
                else if (jsonString.charAt(0) == '{') {
                    JSONObject jsonObject1 = new JSONObject(jsonString);
                    System.out.println(jsonObject1.toString(2));
                    String code = jsonObject1.getString("faceId");
                    return code;
                } else {
                    System.out.println(jsonString);
                    return null;
                }
            }
        }
        catch (Exception e)
        {
            // Display error message.
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static boolean FaceCompare(String faceid1, String faceid2){
        String inputJson =  "{\"faceId1\": \"" + faceid1 + "\"," +
                "\"faceId2\": \"" + faceid2 + "\"}";

        HttpClient httpclient = HttpClients.createDefault();
        try {

            URIBuilder builder = new URIBuilder("https://westcentralus.api.cognitive.microsoft.com/face/v1.0/verify");
            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", "f99a86bd80cd440c8b75d84743b90df2");

            // Request body
            StringEntity reqEntity = new StringEntity(inputJson);
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null)
            {
                String answer = EntityUtils.toString(entity).trim();
                System.out.println(answer);
                JSONObject jsonObject = new JSONObject(answer);
                return jsonObject.getBoolean("isIdentical");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}


/////////////////////////////




