package knight.chinacityselection;

import android.app.Application;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import knight.chinacityselection.db.CityDB;

public class ClientApplication extends Application {
    private static ClientApplication mApplication;
    private static CityDB mCityDB;

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
        mCityDB = openCityDB();// 这个必须最先复制完,所以我放在单线程中处理
    }

    private CityDB openCityDB() {
        String path = "/data"
                + Environment.getDataDirectory().getAbsolutePath()
                + File.separator + "knight.chinacityselection" + File.separator
                + CityDB.CITY_DB_NAME;
        File db = new File(path);
        if (!db.exists()) {
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

}
