package activitytest.example.com.databasetest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DatabaseProvider extends ContentProvider {

    public static final int BOOK_DIR = 0;
    public static final int BOOK_ITEM = 1;
    public static final int CATEGORY_DIR = 2;
    public static final int CATEGORY_ITEM = 3;
    public static final String AUTHORITY = "com.example.databasetest.provider";
    private static UriMatcher uriMatcher;
    private MyDatabaseHelper dbHelper;


    static {

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"book",BOOK_DIR);
        uriMatcher.addURI(AUTHORITY,"book/#",BOOK_ITEM);
        uriMatcher.addURI(AUTHORITY,"category",CATEGORY_DIR);
        uriMatcher.addURI(AUTHORITY,"category/#",CATEGORY_ITEM);

    }

    public DatabaseProvider() {

        //dbHelper = new MyDatabaseHelper(getContext(),"BookStore.db",null,2);
        //return

    }


    //创建数据库
    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        dbHelper = new MyDatabaseHelper(getContext(),"BookStore.db",null,2);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

         //查询数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){

            case BOOK_DIR:
                cursor = db.rawQuery("select * from Book",null);
                break;
            case BOOK_ITEM:
                //将AUTHORUTY之后的部分以分号相隔，0是路径，1是id
                String bookId = uri.getPathSegments().get(1);
                cursor = db.rawQuery("select * from Book where id = ?",new String[]{bookId});
                break;
            case CATEGORY_DIR:
                cursor = db.rawQuery("select * from Category",null);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                cursor = db.rawQuery("select * from Category where id = ?",new String[]{categoryId});
                break;
            default:
                break;


        }
        //查询返回值是游标
        return cursor;

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri  uriReturn = null;

        switch (uriMatcher.match(uri)){

            case BOOK_DIR:
            case BOOK_ITEM:
                //这个竟然是有返回值的，返回的竟然还是id
                long newBookId = db.insert("Book",null,values);
                //因为要返回一个URI对象，所以将内容URI转化为URI对象
                uriReturn = Uri.parse("content://" + AUTHORITY + "/book/"+newBookId);
                break;
            case CATEGORY_DIR:
            case CATEGORY_ITEM:
                long newCategoryId = db.insert("Category",null,values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/category/" +newCategoryId);
                break;
            default:
                break;
        }
        //增加的返回值是URI对象
        return  uriReturn;

    }
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        //更新的返回值是行
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedRows = 0;
        switch (uriMatcher.match(uri)){

            case BOOK_DIR:
                updatedRows = db.update("Book",values,selection,selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                updatedRows = db.update("Book",values,"id = ?",new String[]{bookId});

                break;
            case CATEGORY_DIR:
                updatedRows = db.update("Category",values,selection,selectionArgs);

                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                updatedRows = db.update("Category",values,"id = ?",new String[]{categoryId} );

                break;
            default:
                break;
        }

        return  updatedRows;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        //删除数据的返回值也是行
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows = 0 ;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                deletedRows = db.delete("Book",selection,selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                deletedRows = db.delete("Book","id = ?",new String[]{bookId});
                break;
            case CATEGORY_DIR:
                deletedRows = db.delete("Category",selection,selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                deletedRows = db.delete("Category","id = ?",new String[]{categoryId});

                break;
            default:
                break;

        }
        return  deletedRows;
    }

    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.databasetest.provider.book";

            case BOOK_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.databasetest.provider.book";
            case CATEGORY_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.databasetest.provider.category";
            case CATEGORY_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.databasetest.provider.category";

        }
        return  null;

    }


}
