package activitytest.example.com.contactstest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MyProvider extends ContentProvider {

    //4 个整型常量
    public static final int TABLE1_DIR = 0;
    public static final int TABLE1_ITEM = 1;
    public static final int TABLE2_DIR = 2;
    public static final int TABLE2_ITEM = 3;
    //这个是用作拼接
    private static UriMatcher uriMatcher;


    //静态代码块
    static {

        //上边的4个整形常量是为了后边做判断的时候使用，关联一下
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //第一张表
        uriMatcher.addURI("activitytest.example.com.contactstest","table1",TABLE1_DIR);
        //第一张表的表下id
        uriMatcher.addURI("activitytest.example.com.contactstest","table1/#",TABLE1_ITEM);
        //第二张表
        uriMatcher.addURI("activitytest.example.com.contactstest","table2",TABLE2_DIR);
        //第二张表的表下id
        uriMatcher.addURI("activitytest.example.com.contactstest","table2/#",TABLE2_ITEM);


    }



    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (uriMatcher.match(uri)){

            case TABLE1_DIR:
                //查询表1中的所有数据
                return  null;

            case TABLE1_ITEM:
                //查询表1中的单条数据
                return  null;

            case TABLE2_DIR:
                //查询表2中的所有数据
                return  null;

            case TABLE2_ITEM:
                //查询表2中的单条数据
                return  null;

            default:
                break;


        }
        return  null;

    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }


    /*

         根据URI返回对应的MIME类型
         (1)必须以vnd开头
         (2)如果URI以路径结尾，则（1）后接android.cursor.dir/   如果id结尾，则android.cursor.item/
         (3)后边接上 vnd.<authority>.<path>
         eg:返回的一个完整地址
         vnd.android.cursor.dir/vnd.activitytest.example.com.contactstest.provider.table1


     */

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (uriMatcher.match(uri)){

            case TABLE1_DIR:
                return "vnd.android.cursor.dir/vnd.activitytest.example.com.contactstest.provider.table1";
            case TABLE1_ITEM:
                return "vnd.android.cursor.item/vnd.activitytest.example.com.contactstest.provider.table1";
            case TABLE2_DIR:
                return  "vnd.android.cursor.dir/vnd.activitytest.example.com.contactstest.provider.table2";
            case TABLE2_ITEM:
                return  "vnd.android.cursor.item/vnd.activitytest.example.com.contactstest.provider.table2";
            default:
                break;
        }
        return null;
    }
}
