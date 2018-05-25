package activitytest.example.com.listviewtest;

public class Fruit {

    private String name;
    private int imageId;

    //构造函数，完成set的功能
    public Fruit(String name,int imageId){

        this.name = name;
        this.imageId = imageId;

    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
