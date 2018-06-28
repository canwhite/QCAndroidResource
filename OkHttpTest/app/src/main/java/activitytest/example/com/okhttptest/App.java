package activitytest.example.com.okhttptest;

import java.util.List;

public class App{


    private String name;
    private String age;
    private List<AppItem> app;

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setApp(List<AppItem> app) {
        this.app = app;
    }

    public List<AppItem> getApp() {
        return app;
    }



    public class AppItem {

        private String id;
        private String name;
        private String version;


        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public String getVersion() {
            return version;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }








}



