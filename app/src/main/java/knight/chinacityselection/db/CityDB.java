package knight.chinacityselection.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class CityDB {
    public static final String CITY_DB_NAME = "kuaihuo.db";
    private static final String CITY_TABLE_NAME = "city";
    private SQLiteDatabase db;

    public CityDB(Context context, String path) {
        db = context.openOrCreateDatabase(path, Context.MODE_PRIVATE, null);
    }

    public List<City> getAllCity() {
        List<City> list = new ArrayList<City>();
        Cursor c = db.rawQuery("SELECT * from " + CITY_TABLE_NAME, null);
        while (c.moveToNext()) {
            String province = c.getString(c.getColumnIndex("province"));
            String city = c.getString(c.getColumnIndex("city"));
//            String number = c.getString(c.getColumnIndex("number"));
//            String allPY = c.getString(c.getColumnIndex("allpy"));
//            String allFirstPY = c.getString(c.getColumnIndex("allfirstpy"));
//            String firstPY = c.getString(c.getColumnIndex("firstpy"));
            Double latitude = c.getDouble(c.getColumnIndex("latitude"));
            Double longitude = c.getDouble(c.getColumnIndex("longitude"));
            City item = new City(province, city, latitude, longitude);
            list.add(item);
        }
        return list;
    }


    public List<String> getAllProvince() {

        List<String> list = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT distinct province from " + CITY_TABLE_NAME, null);
        while (c.moveToNext()) {
            String province = c.getString(c.getColumnIndex("province"));
            list.add(province);
        }
        return list;
    }


    /**
     * 拿到省的所有城市
     *
     * @return
     */
    public List<String> getProvinceAllCity(String province) {

        List<String> list = new ArrayList<>();

//        Cursor c = db.rawQuery("SELECT city from " + CITY_TABLE_NAME + " where province = ?", new String[]{province});
        Cursor c = db.rawQuery("SELECT city from " + CITY_TABLE_NAME + " where province = ? ", new String[]{province});
        while (c.moveToNext()) {
            String city = c.getString(c.getColumnIndex("city"));
            list.add(city);
        }
        return list;
    }


    public City getCity(String city) {
        if (TextUtils.isEmpty(city))
            return null;
        City item = getCityInfo(parseName(city));
        if (item == null) {
            item = getCityInfo(city);
        }
        return item;
    }

    /**
     * 去掉市或县搜索
     *
     * @param city
     * @return
     */
    private String parseName(String city) {
        if (city.contains("市")) {// 如果为空就去掉市字再试试
            String subStr[] = city.split("市");
            city = subStr[0];
        } else if (city.contains("县")) {// 或者去掉县字再试试
            String subStr[] = city.split("县");
            city = subStr[0];
        }
        return city;
    }

    private City getCityInfo(String city) {
        Cursor c = db.rawQuery("SELECT * from " + CITY_TABLE_NAME
                + " where city=?", new String[]{city});
        if (c.moveToFirst()) {
            String province = c.getString(c.getColumnIndex("province"));
            String name = c.getString(c.getColumnIndex("city"));
//            String number = c.getString(c.getColumnIndex("number"));
//            String allPY = c.getString(c.getColumnIndex("allpy"));
//            String allFirstPY = c.getString(c.getColumnIndex("allfirstpy"));
//            String firstPY = c.getString(c.getColumnIndex("firstpy"));
            Double latitude = c.getDouble(c.getColumnIndex("latitude"));
            Double longitude = c.getDouble(c.getColumnIndex("longitude"));

            City item = new City(province, name, latitude, longitude);
            return item;
        }
        return null;
    }


    /**
     * 查询附近的城市
     * 画正方形
     */
    public List<String> getNearbyCityList(String sCity) {
        City city = getCity(sCity);
        List<String> nearbyCitysList = new ArrayList<String>();
        //根据常跑地信息画正方形地域
        double lat = city.getLatitude() + 0.9;
        double lon = city.getLongitude() + 0.9;
        double lat1 = city.getLatitude() - 0.9;
        double lon1 = city.getLongitude() - 0.9;

        Cursor c = db.rawQuery("SELECT * from " + CITY_TABLE_NAME + " WHERE LATITUDE < " + lat + " AND LATITUDE > " + lat1 +
                " AND LONGITUDE <" + lon + " AND LONGITUDE > " + lon1, null);
        while (c.moveToNext()) {
            String nearbyCity = c.getString(c.getColumnIndex("city"));
            nearbyCitysList.add(nearbyCity);
        }
        return nearbyCitysList;
    }

}
