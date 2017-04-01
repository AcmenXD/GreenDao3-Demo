package com.acmenxd.greendao3_demo.db.migrator;

import org.greenrobot.greendao.database.Database;

/**
 * @author 小东
 * @version v1.0
 * @date 2017/2/28 10:00
 * @detail 更新数据库版本基类
 */
public abstract class BaseMigratorHelper {
    public abstract void onUpgrade(Database db);
}
