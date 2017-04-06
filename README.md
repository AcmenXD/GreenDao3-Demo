# GreenDao3-Demo
基于<a href="https://github.com/greenrobot/greenDAO">greenDAO3.x</a>版本,写的使用Demo,并拓展了一部分数据库升级功能.


如要了解功能实现,请运行app程序查看控制台日志和源代码!
* 源代码 : <a href="https://github.com/AcmenXD/GreenDao3-Demo">AcmenXD/GreenDao3-Demo</a>
* apk下载路径 : <a href="https://github.com/AcmenXD/Resource/blob/master/apks/GreenDao3-Demo.apk">GreenDao3-Demo.apk</a>
### 依赖
---
- AndroidStudio
```
    // 以下配置在app下的build.gradle配置即可
	apply plugin: 'org.greenrobot.greendao'
    buildscript {
        repositories {
            mavenCentral()
        }
        dependencies {
            classpath 'org.greenrobot:greendao-gradle-plugin:3.2.1'
        }
    }
    greendao {
        /**
         * 数据库版本号不能降低,会导致App无法安装
         * 数据库字段发生变更或新增表时,更新这个版本号,运行Make Project
         * 版本号历史:
         * 1:2016年11月21日13:32:33 - > 创建初始库和表
         */
        schemaVersion 1
        daoPackage 'com.acmenxd.greendao3_demo.db.dao'//生成的dao文件存放路径
        targetGenDir 'src/main/java'//自定义生成数据库文件的目录，可以将生成的文件放到我们的java目录中，而不是build中，这样就不用额外的设置资源目录了
    }
```
```
	compile 'org.greenrobot:greendao:3.2.0'
```
### 功能
---
- 支持greenDAO3.x所有功能,未改写其代码
- 支持新增表
- 支持表字段增加和删除
- 支持增加表字段时,原有数据填充默认值
### 使用 -> 以下代码 注释很详细、很重要很重要很重要!!!
---
**第一步**
```java
    // Application中初始化数据库
    DBManager.getInstance().init(this);
```
---
**第二步**
```java
    /**
     * 创建表对应的实体类(注解为greenDAO提供,可参考https://github.com/greenrobot/greenDAO)
     * 每个实体类对应一个数据库表,表名默认为全部大写的类名
     */
    @Entity //将我们的java普通类变为一个能够被greenDAO识别的数据库类型的实体类
    public class Student {
        /**
         * 注释解释:
         *
         * @NotNull : 表示该字段不可以为空
         * @Unique : 表示该字段唯一
         * @Index(unique = true) : 使普通属性改变成唯一索引属性
         */
        @Id//通过这个注解标记的字段必须是Long类型的，这个字段在数据库中表示它就是主键，并且它默认就是自增的
        private Long id;

        @Property(nameInDb = "NAME")
        private String name;
        private int age;
        private double score;
        private Date date;
        @Transient //表明这个字段不会被写入数据库，只是作为一个普通的java类字段，用来临时存储数据的，不会被持久化
        private Date date2;
    }
```
---
**第三步**
```java
    配置好build.gradle,运行Make Project,会自动生成部分代码和java文件(如运行失败,修复问题后,需再次运行)
```
---
**第四步**
```java
    创建StudentDB,为Student表添加 增删改查 函数
```
---
**第五步**
```java
    运行程序,会自动创建数据库表和字段
```
### 新增表 或 增删表字段
---
**第一步**
```java
    创建新的实体类 或 修改实体类(Student)
```
---
**第二步**
```java
    修改build.gradle中数据库的版本号,运行Make Project,会自动更改相关代码(如运行失败,修复问题后,需再次运行)
```
---
**第三步**
```java
    创建 MigratorHelper+数据库版本号 的类文件(此文件为数据库升级时,表结构的修改类),并在DBOpenHelper中确认MigratorHelper类的包名是否正确(因为这里用的反射)
```
---
**第四步**
```java
    /**
     * 在MigratorHelper.onUpgrade函数中,更新数据库表结构
     */
    public void onUpgrade(Database db) {
        /**
         * migrate()参数解释
         * 参数一:数据库db实例
         * 参数二:新增字段在更新数据库时回调, 设置其默认值, 如为null,字段会自动设置默认值(参考DefaultCallback类)
         * 参数三:需要更新或新建表的Dao.class类(有增删字段或新增的表必须在这里配置)
         */
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
        }, StudentDao.class, Student2Dao.class, Student3Dao.class);
    }
```
---
**第五步**
```java
    运行程序,会自动更新数据库表和字段
```
有问题请于作者联系AcmenXD@163.com ^_^!
---
### 打个小广告^_^
**gitHub** : https://github.com/AcmenXD   如对您有帮助,欢迎点Star支持,谢谢~

**技术博客** : http://blog.csdn.net/wxd_beijing
# END