package com.excalibur.followproject.activity;

import android.database.Cursor;
import android.text.TextUtils;

//import com.excalibur.followproject.bean.DaoMaster;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 我们基本思路为：
 * 创建临时表-->删除原表-->创建新表-->复制临时表数据到新表并删除临时表
 * 并且我们新增加的和修改的字段做好为String类型，避免字段不能为null的情况发生
 *
 * 更新的时候只需要
 * 1.再要更新的实体类中加上字段
 * 2.在gradle文件里面将greenDao的schemaVersion加一
 */
public class MigrationHelper {

    private static final String CONVERSION_CLASS_NOT_FOUND_EXCEPTION = "MIGRATION HELPER - CLASS DOESN'T MATCH WITH THE CURRENT PARAMETERS";
    private static MigrationHelper migrationHelper;
    public static MigrationHelper getInstance(){
        if(null == migrationHelper){
            synchronized (MigrationHelper.class){
                if(null == migrationHelper)
                    migrationHelper = new MigrationHelper();
            }
        }
        return migrationHelper;
    }

    public void migrate(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        generateTempTables(db, daoClasses);
//        DaoMaster.dropAllTables(db, true);
//        DaoMaster.createAllTables(db, false);
        restoreData(db, daoClasses);
    }

    private void generateTempTables(Database database, Class<? extends AbstractDao<?,?>>... daoClasses){
        for (int i = 0; i < daoClasses.length; i++) {
            DaoConfig config = new DaoConfig(database,daoClasses[i]);

            String divider = "";
            String tableName = config.tablename;
            String tempTableName = tableName.concat("_TEMP");
            List<String> properties = new ArrayList<>();

            StringBuilder createTableString = new StringBuilder();

            createTableString.append("CREATE TABLE ").append(tempTableName).append(" (");

            for (int j = 0; j < config.properties.length; j++) {
                String columnName = config.properties[j].columnName;

                if(getColumns(database,tableName).contains(columnName)){
                    properties.add(columnName);

                    String type = null;

                    try {
                        type = getTypeByClass(config.properties[i].type);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    createTableString.append(divider).append(columnName).append(" ").append(type);

                    if(config.properties[i].primaryKey){
                        createTableString.append("PRIMARY KEY");
                    }
                    divider = ",";
                }
            }
            createTableString.append(");");

            database.execSQL(createTableString.toString());

            StringBuilder insertTableString = new StringBuilder();

            insertTableString.append("INSERT INTO ").append(tempTableName).append(" (");
            insertTableString.append(TextUtils.join(",",properties));
            insertTableString.append(") SELECT ");
            insertTableString.append(TextUtils.join(",",properties));
            insertTableString.append(" FROM ").append(tableName).append(";");

            database.execSQL(insertTableString.toString());
        }

    }

    private void restoreData(Database db,Class<? extends AbstractDao<?,?>>... daoClasses){
        for (int i = 0; i < daoClasses.length; i++) {
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
            String tableName = daoConfig.tablename;
            String tempTableName = daoConfig.tablename.concat("_TEMP");
            List<String> properties = new ArrayList();

            for (int j = 0; j < daoConfig.properties.length; j++) {
                String columnName = daoConfig.properties[j].columnName;

                if (getColumns(db, tempTableName).contains(columnName)) {
                    properties.add(columnName);
                }
            }

            StringBuilder insertTableStringBuilder = new StringBuilder();

            insertTableStringBuilder.append("INSERT INTO ").append(tableName).append(" (");
            insertTableStringBuilder.append(TextUtils.join(",", properties));
            insertTableStringBuilder.append(") SELECT ");
            insertTableStringBuilder.append(TextUtils.join(",", properties));
            insertTableStringBuilder.append(" FROM ").append(tempTableName).append(";");

            StringBuilder dropTableStringBuilder = new StringBuilder();
            dropTableStringBuilder.append("DROP TABLE ").append(tempTableName);
            db.execSQL(insertTableStringBuilder.toString());
            db.execSQL(dropTableStringBuilder.toString());
        }
    }

    private String getTypeByClass(Class<?> type) throws Exception {
        if(type.equals(String.class))
            return "TEXT";
        if(type.equals(Long.class) || type.equals(Integer.class) || type.equals(long.class))
            return "INTEGER";
        if(type.equals(Boolean.class))
            return "BOOLEAN";

        Exception exception = new Exception(CONVERSION_CLASS_NOT_FOUND_EXCEPTION.concat(" - Class: ").concat(type.toString()));
        exception.printStackTrace();
        throw exception;
    }

    private List<String> getColumns(Database db,String tableName){
        List<String> columns = new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 1",null);
            if(null != cursor){
                columns = new ArrayList<>(Arrays.asList(cursor.getColumnNames()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null != cursor)
                cursor.close();
        }
        return columns;
    }
}
