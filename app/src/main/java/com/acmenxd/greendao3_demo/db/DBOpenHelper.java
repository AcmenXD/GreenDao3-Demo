package com.acmenxd.greendao3_demo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.acmenxd.greendao3_demo.db.dao.DaoMaster;
import com.acmenxd.greendao3_demo.db.migrator.BaseMigratorHelper;

import org.greenrobot.greendao.database.Database;

/**
 * @author 小东
 * @version v1.0
 * @date 2017/2/28 10:00
 * @detail 数据库升级
 */
public class DBOpenHelper extends DaoMaster.OpenHelper {
    private static final String TAG = DBOpenHelper.class.getSimpleName();

    public DBOpenHelper(Context context, String dbName, SQLiteDatabase.CursorFactory factory) {
        super(context, dbName, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        //DBManager.getInstance().onUpgrade(db);
        /**
         * * 数据库版本号不能降低,会导致App无法安装(newVersion < oldVersion)
         * 循环数据库版本,更新各版本数据结构差异
         */
        if (newVersion > oldVersion) {
            for (int i = oldVersion; i < newVersion; i++) {
                try {
                    BaseMigratorHelper migratorHelper = (BaseMigratorHelper) Class
                            .forName("com.acmenxd.greendao3_demo.db.migrator.MigratorHelper" + (i + 1)).newInstance();
                    if (migratorHelper != null) {
                        migratorHelper.onUpgrade(db);
                    }
                } catch (ClassNotFoundException | ClassCastException | IllegalAccessException | InstantiationException pE) {
                    Log.e("Dong", TAG + ":" + pE);
                }
            }
        }
    }
}
