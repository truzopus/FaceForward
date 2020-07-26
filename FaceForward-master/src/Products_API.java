import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Products_API {

    private String name;
    private String description;
    private String imgPath;
    private String price;
    private int sku;

    private static final String PRODUCT1_API_BASE = "https://api.bestbuy.com/v1/products((search=";

    private static final String PRODUCT2_API_BASE = ")&(categoryPath.id=";

    private static final String PRODUCT3_API_BASE = "))?apiKey=dTyAe3mCZe2UKGgG1wlYSFbQ&pageSize=1&format=json";

    public static String address;

    public Products_API() {
        this.name = null;
        this.description = null;
        this.imgPath = null;
        this.price = null;
    }

    public void search(String keyword, String category) {
        String realCat;
        if (category.equals("phones")) {
            realCat = "pcmcat209400050001";
        } else if (category.equals("desktop")) {
            realCat = "abcat0501000";
        } else if (category.equals("cameras")) {
            realCat = "abcat0401000";
        } else if (category.equals("health")) {
            realCat = "pcmcat242800050021";
        } else if (category.equals("headphones")) {
            realCat = "abcat0204000";
        } else if (category.equals("audio")) {
            realCat = "pcmcat241600050001";
        } else if (category.equals("security")) {
            realCat = "pcmcat254000050002";
        } else if (category.equals("laptops")) {
            realCat = "abcat0502000";
        } else if (category.equals("tablets")) {
            realCat = "pcmcat209000050006";
        } else if (category.equals("3ds")) {
            realCat = "pcmcat232900050000";
        } else if (category.equals("ps4")) {
            realCat = "pcmcat295700050012";
        } else if (category.equals("wireless audio")) {
            realCat = "pcmcat310200050004";
        } else if (category.equals("psvita")) {
            realCat = "pcmcat243400050029";
        } else if (category.equals("cooking")) {
            realCat = "abcat0904000";
        } else if (category.equals("fridges")) {
            realCat = "abcat0901000";
        } else if (category.equals("kitchen")) {
            realCat = "abcat0912000";
        } else if (category.equals("tvs")) {
            realCat = "abcat0101000";
        } else if (category.equals("washers and dryers")) {
            realCat = "abcat0910000";
        } else if (category.equals("wiiu")) {
            realCat = "pcmcat273800050036";
        } else if (category.equals("xbox1")) {
            realCat = "pcmcat300300050002";
        } else {
            realCat = null;
        }
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            String sb;
            String[] splited = keyword.split("\\s+");
            if (splited.length > 1) {
                for (int i = 1; i < splited.length; i++) {
                    splited[i] = "&search=" + splited[i];
                }
                String temp = "";
                for (int i = 1; i < splited.length; i++) {
                    temp = temp.concat(splited[i]);
                }
                sb = PRODUCT1_API_BASE + splited[0] + temp + PRODUCT2_API_BASE + realCat + PRODUCT3_API_BASE;
            } else {
                sb = PRODUCT1_API_BASE + keyword + PRODUCT2_API_BASE + realCat + PRODUCT3_API_BASE;
        }
            address = sb;
            URL url = new URL(sb);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        JSONObject jsonObj = new JSONObject(jsonResults.toString());
        JSONArray predsJsonArray = jsonObj.getJSONArray("products");

        this.description = predsJsonArray.getJSONObject(0).getString("shortDescription");
        this.name = predsJsonArray.getJSONObject(0).getString("name");
        this.sku = predsJsonArray.getJSONObject(0).getInt("sku");
        Integer temp = predsJsonArray.getJSONObject(0).getInt("salePrice");
        this.price = temp.toString();

        JSONArray imagesArray = predsJsonArray.getJSONObject(0).getJSONArray("images");
        String imgURL = imagesArray.getJSONObject(0).getString("href");

        try {
            URL url = new URL(imgURL);
            InputStream in = new BufferedInputStream(url.openStream());
            OutputStream out = new BufferedOutputStream(new FileOutputStream(this.name + ".jpg"));

            for ( int i; (i = in.read()) != -1; ) {
                out.write(i);
            }
        } catch (Exception e) {
        }
        this.imgPath = this.name + ".jpg";
    }

    public String getName() {
        return this.name;
    }

    public String getPrice() {
        return this.price;
    }

    public String getDescription() {
        return this.description;
    }

    public String getImagePath() {
        return this.imgPath;
    }

    public int getSku() {
        return this.sku;
    }
}
