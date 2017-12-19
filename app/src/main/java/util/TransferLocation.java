package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Time 2017/12/19.
 * User renlei
 * Email renlei@xiaomi.com
 */

public class TransferLocation {
    public static void main(String []args){
        System.out.println("dsa");
        String result = getWriteString(sortData(getData())) ;
        writeToFile(result);
    }


    public static List<Provice> getData(){
        try {
            InputStream inputStream = new FileInputStream("D:\\project\\github\\MyApplication\\app\\src\\main\\java\\util\\cityWeather.json");
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String data = "";
            String tempString = null;
            while ((tempString = bufferedReader.readLine()) != null) {
                data += tempString;
            }


            bufferedReader.close();
            reader.close();
            inputStream.close();
            Gson gson = new Gson();
            JsonObject allJsonObj = gson.fromJson(data,JsonObject.class);

            List<Provice> provicesList = new ArrayList<>();
            Set<Map.Entry<String,JsonElement>> set = allJsonObj.entrySet();
            Iterator<Map.Entry<String,JsonElement>> setIt = set.iterator();

            while (setIt.hasNext()){
                Map.Entry<String,JsonElement> map = setIt.next();
                Provice provice = new Provice();
                provice.father = "";
                provice.name = map.getKey();


                JsonArray cityChildArray =  map .getValue().getAsJsonObject().getAsJsonArray("child");
                List<City>cityList = new ArrayList<>();
                for (int i = 0;i<cityChildArray.size();i++){
                    JsonObject cityJObj = cityChildArray.get(i).getAsJsonObject();
                    City city = new City();
                    city.name = cityJObj.get("name").getAsString();

                    List<QuXian>mQuXianList = new ArrayList<>();
                    JsonArray quxianChildArray = cityJObj.getAsJsonArray("child");
                    for (int j = 0;j<quxianChildArray.size();j++){
                        JsonObject quxianJObj = quxianChildArray.get(j).getAsJsonObject();
                        QuXian quXian = new QuXian();
                        quXian.name = quxianJObj.get("name").getAsString();
                        quXian.id = quxianJObj.get("id").getAsString();
                        mQuXianList.add(quXian);

                        if (city.id == null || city.id.equalsIgnoreCase("")){
                            city.id = quXian.id;
                        }
                        if (provice.id == null || provice.id.equalsIgnoreCase("")){
                            provice.id = quXian.id;
                        }
                    }
                    city.mQuxiansChild = new ArrayList<>();
                    city.mQuxiansChild.addAll(mQuXianList);
                    cityList.add(city);
                }
                provice.mCitysChild = new ArrayList<>();
                provice.mCitysChild.addAll(cityList);
                provicesList.add(provice);
            }
            return provicesList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null ;
    }

    public static List<Provice>sortData(List<Provice> proviceList){
        print(proviceList);
        Collections.sort(proviceList, new Comparator<Provice>() {
            @Override
            public int compare(Provice o1, Provice o2) {
                int o1Id = Integer.valueOf(o1.id);
                int o2Id = Integer.valueOf(o2.id);
                if (o1Id < o2Id){
                    return -1;
                }
                return 1;
            }
        });
        System.out.println("1111Collections.sort(provice.mCitysChild****************************************");
        for (int i =0;i< proviceList.size();i++){
            Provice provice = proviceList.get(i);
            Collections.sort(provice.mCitysChild, new Comparator<City>() {
                @Override
                public int compare(City o1, City o2) {
                    int o1Id = Integer.valueOf(o1.id);
                    int o2Id = Integer.valueOf(o2.id);
                    if (o1Id < o2Id){
                        return -1;
                    }
                    return 1;
                }
            });

            for (int j = 0;j<provice.mCitysChild.size();j++){
                City city = provice.mCitysChild.get(j);
                Collections.sort(city.mQuxiansChild, new Comparator<QuXian>() {
                    @Override
                    public int compare(QuXian o1, QuXian o2) {
                        int o1Id = Integer.valueOf(o1.id);
                        int o2Id = Integer.valueOf(o2.id);
                        if (o1Id < o2Id){
                            return -1;
                        }
                        return 1;
                    }
                });
            }

        }
        System.out.println("****************************************");
        print(proviceList);
        return proviceList;
    }



    private static String getWriteString(List<Provice> proviceList){
        JsonObject resultJobj = new JsonObject();
        for (int i = 0;i<proviceList.size();i++){
            Provice provice = proviceList.get(i);
            JsonObject proviceJObj = new JsonObject();
            proviceJObj.addProperty("father","");
            proviceJObj.addProperty("id",provice.id );
            proviceJObj.addProperty("name",provice.name);

            JsonArray cityArray = new JsonArray();
            for (int j = 0;j<provice.mCitysChild.size();j++){

                City city = provice.mCitysChild.get(j);
                JsonObject cityJObj = new JsonObject();
                cityJObj.addProperty("name",city.name);
                cityJObj.addProperty("id",city.id);


                JsonArray mQuxianArray = new JsonArray();
                for (int k =0; k< city.mQuxiansChild.size();k++){
                    QuXian quXian = city.mQuxiansChild.get(k);
                    JsonObject quxianJObj = new JsonObject();
                    quxianJObj.addProperty("name", quXian.name);
                    quxianJObj.addProperty("id",quXian.id);
                    quxianJObj.add("child",new JsonObject());
                    mQuxianArray.add(quxianJObj);
                }
                cityJObj.add("child",mQuxianArray);

                cityArray.add(cityJObj);
            }

            proviceJObj.add("child",cityArray);

            resultJobj.add(provice.name,proviceJObj);
        }
        Gson gson = new Gson();

        String result = gson.toJson(resultJobj);

        System.out.println( "getWriteString***********"+"\n"+result);
        return result;
    }
    private static void print(List<Provice> proviceList){
        for (int i = 0;i<proviceList.size();i++){
            System.out.println(proviceList.get(i).toString());
        }
    }

    private static void writeToFile(String data){
        try {
            String path = "D:\\project\\github\\MyApplication\\app\\src\\main\\java\\util\\cityWeather2.json";
            File file =new File(path);
            if (!file.exists()){
                file.createNewFile();
            }

            OutputStream ous =new FileOutputStream(file);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,false));
            bufferedWriter.write(data,0,data.length());
            ous.close();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    static class Provice{
        public String father;
        public String name;
        public String id;
//        JsonArray childCityArray;
        List<City> mCitysChild = new ArrayList<>();

        @Override
        public String toString() {
            return "Provice{" +
                    "father='" + father + '\'' +
                    ", name='" + name + '\'' +
                    ", id='" + id + '\'' +
                    ", mCitysChild=" + mCitysChild +
                    '}';
        }
    }

    static class City{
        public String name;
        public String id;
        List<QuXian> mQuxiansChild = new ArrayList<>();

        @Override
        public String toString() {
            return "City{" +
                    "name='" + name + '\'' +
                    ", id='" + id + '\'' +
                    ", mQuxiansChild=" + mQuxiansChild +
                    '}';
        }
    }
    static class QuXian{
        public String name;
        public String id;
        JsonObject chlid = new JsonObject();

        @Override
        public String toString() {
            return "QuXian{" +
                    "name='" + name + '\'' +
                    ", id='" + id + '\'' +
                    ", chlid=" + chlid +
                    '}';
        }
    }
}
