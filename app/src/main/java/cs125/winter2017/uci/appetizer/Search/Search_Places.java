package cs125.winter2017.uci.appetizer.Search;


import java.io.IOException;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class Search_Places{

    private String Query;
    private double Lat;
    private double Lon;
    private int Radius;
    private int Price;
    private String Type;
    private String Mealtype;
    private boolean Opennow;
    private String GoogleAPIkey = "AIzaSyARCNUYBmwBkXLBCWkmddG2Kr-Bcb0xe-Y";
    private String result;


    public Search_Places(String query, double lat, double lon, int radius, int price, String mealtype){
        Query = query.replaceAll(" ", "+");
        Lat = lat;
        Lon = lon;
        Radius = radius;
        Price = price;
        Type = "restaurant";
        Mealtype = mealtype;
        //Opennow = opennow;
        result = new String();

    }

    private String get_url(){
        String base_search_url = "https://maps.googleapis.com/maps/api/place/textsearch/json?";
        String search_url = base_search_url + "&query=" + Query;
        search_url = search_url + "&type=" + Type;
        search_url = search_url + "&location=" + Double.toString(Lat) + "," + Double.toString(Lon);
        search_url = search_url + "&radius=" + Radius;
        if (Price > -1){
            search_url = search_url + "&price=" + Price;
        }
        //search_url = search_url + "&opennow=" + Boolean.toString(Opennow);
        search_url = search_url + "&key=" + GoogleAPIkey;

        return search_url;

    }

    private String get_places(String url){

        String charset = "UTF-8";




        try {
//            URLConnection connection = new URL(url).openConnection();
//            connection.setRequestProperty("Accept-Charset", charset);
//            InputStream in = connection.getInputStream();
//            BufferedReader bReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"), 8);
//            StringBuilder sBuilder = new StringBuilder();
            URL con = new URL(url);
            HttpURLConnection connect = (HttpURLConnection) con.openConnection();
            InputStreamReader in = new InputStreamReader(connect.getInputStream());
            //            BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            char[] buff = new char[1024];
            int read;
            while ((read = in.read(buff)) != -1) {
                sb.append(buff,0,read);
            }

            String result = sb.toString();

            return(result);
//            return new JSONObject(responseStrBuilder.toString());


        } catch (IOException e) {
            e.printStackTrace();
//        } catch (JSONException e){
//            e.printStackTrace();
        }
        return "";


    }


    public String search(){
        String url = get_url();

        String results = get_places(url);
        return results;
    }

    public void Search(DownloadPlacesTask task) {
        String url = get_url();
        task.execute(url);

    }



}
