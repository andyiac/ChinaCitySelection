package knight.chinacityselection;

import android.app.Application;
import android.os.Environment;
import android.os.Handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import knight.chinacityselection.db.City;
import knight.chinacityselection.db.CityDB;

public class ClientApplication extends Application {
    public static ArrayList<EventHandler> mListeners = new ArrayList<EventHandler>();
    private static ClientApplication mApplication;
    private static final String FORMAT = "^[a-z,A1-Z].*$";
    private static final int CITY_LIST_SCUESS = 0;
    private List<City> mCityList;
    private static CityDB mCityDB;
    private boolean isCityListComplite;
    private List<String> mSections;
    private Map<String, List<City>> mMap;

    // 首字母位置集
    private List<Integer> mPositions;

    // 首字母对应的位置
    private Map<String, Integer> mIndexer;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case CITY_LIST_SCUESS:
                    isCityListComplite = true;
                    if (mListeners.size() > 0)// 通知接口完成加载
                        for (EventHandler handler : mListeners) {
                            handler.onCityComplite();
                        }
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        initCityList();
    }

    public static ClientApplication getInstance() {
        return mApplication;
    }

    public synchronized CityDB getCityDB() {
        if (mCityDB == null)
            mCityDB = openCityDB();
        return mCityDB;
    }

    private void initCityList() {
        mCityList = new ArrayList<City>();
        mSections = new ArrayList<String>();
        mMap = new HashMap<String, List<City>>();
        mPositions = new ArrayList<Integer>();
        mIndexer = new HashMap<String, Integer>();
        mCityDB = openCityDB();// 这个必须最先复制完,所以我放在单线程中处理
        new Thread(new Runnable() {

            @Override
            public void run() {
                isCityListComplite = false;
                prepareCityList();
                mHandler.sendEmptyMessage(CITY_LIST_SCUESS);
            }
        }).start();
    }

    private CityDB openCityDB() {
        String path = "/data"
                + Environment.getDataDirectory().getAbsolutePath()
                + File.separator + "knight.chinacityselection" + File.separator
                + CityDB.CITY_DB_NAME;
        File db = new File(path);
        if (!db.exists()) {
            // L.i("db is not exists");
            try {
                InputStream is = getAssets().open("city.db");
                FileOutputStream fos = new FileOutputStream(db);
                int len = -1;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    fos.flush();
                }
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        return new CityDB(this, path);
    }

    private boolean prepareCityList() {
        mCityList = mCityDB.getAllCity();// 获取数据库中所有城市
        for (City city : mCityList) {
            String firstName = city.getFirstPY();// 第一个字拼音的第一个字母
            if (firstName.matches(FORMAT)) {
                if (mSections.contains(firstName)) {
                    mMap.get(firstName).add(city);
                } else {
                    mSections.add(firstName);
                    List<City> list = new ArrayList<City>();
                    list.add(city);
                    mMap.put(firstName, list);
                }
            } else {
                if (mSections.contains("#")) {
                    mMap.get("#").add(city);
                } else {
                    mSections.add("#");
                    List<City> list = new ArrayList<City>();
                    list.add(city);
                    mMap.put("#", list);
                }
            }
        }
        Collections.sort(mSections);// 按照字母重新排序
        int position = 0;
        for (int i = 0; i < mSections.size(); i++) {
            mIndexer.put(mSections.get(i), position);// 存入map中，key为首字母字符串，value为首字母在listview中位置
            mPositions.add(position);// 首字母在listview中位置，存入list中
            position += mMap.get(mSections.get(i)).size();// 计算下一个首字母在listview的位置
        }
        return true;
    }


    public static abstract interface EventHandler {
        public abstract void onCityComplite();

        public abstract void onNetChange();
    }
}
