package com.acmenxd.greendao3_demo.db.migrator;

import com.acmenxd.greendao3_demo.db.dao.StudentDao;

import org.greenrobot.greendao.database.Database;

/**
 * @author 小东
 * @version v1.0
 * @date 2017/2/28 10:00
 * @detail 更新数据库版本 -> DB版本号 1
 */
public class MigratorHelper1 extends BaseMigratorHelper {
    private static final String TAG = MigratorHelper1.class.getSimpleName();

    @Override
    public void onUpgrade(Database db) {
        //更新数据库表结构
        MigrationHelperUtil.getInstance().migrate(db, new DefaultCallback() {
            @Override
            public String onText(String tablename, String columnName) {
                return null;
            }
            public Long onInteger(String tablename, String columnName) {
                return null;
            }
            @Override
            public Double onReal(String tablename, String columnName) {
                return null;
            }
            @Override
            public Boolean onBoolean(String tablename, String columnName) {
                return null;
            }
        }, StudentDao.class);
    }
}
