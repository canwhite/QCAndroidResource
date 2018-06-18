package activitytest.example.com.litepaltest;

import org.litepal.crud.DataSupport;

public class Category extends DataSupport {

    private  int id;
    private  String categoryName;
    private  int categoryCode;


    public void setId(int id) {
        this.id = id;
    }
    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
