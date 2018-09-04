package com.example.melonweather;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.melonweather.db.City;
import com.example.melonweather.db.County;
import com.example.melonweather.db.Province;
import com.example.melonweather.util.HttpUtil;
import com.example.melonweather.util.Utility;

import org.litepal.crud.DataSupport;
import org.litepal.util.BaseUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseAreaFragment extends Fragment {

    public  static  final int LEVEL_PROVINCE = 0;
    public  static  final int LEVEL_CITY = 1;
    public  static  final int LEVEL_CONTY = 2;

    private ProgressDialog progressDialog;


    private TextView titleText;
    private Button backButton;
    private ListView listView;


    private ArrayAdapter<String> adapter;



    private List<String> dataList = new ArrayList<>();


    /*
        省列表
     */

    private List<Province> provinceList;

    /*
        市列表
     */

    private List<City> cityList;


    /*
        县列表
     */
    private  List<County> countyList;


    /*
        选中的省份
     */

    private  Province selectedProvince;


    /*
        选中的城市
     */

    private City selectedCity;

    private static final String TAG = "ChooseAreaFragment";

    /*
        当前选中的级别

     */

    private  int currentLevel;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.choose_area,container,false);
        titleText = (TextView) view.findViewById(R.id.title_text);
        backButton  = (Button) view.findViewById(R.id.back_button);
        listView = (ListView)view.findViewById(R.id.list_view);

        //设置适配器，用官方封装的模型
        //
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        return  view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (currentLevel == LEVEL_PROVINCE){
                    //选中的省份
                    selectedProvince = provinceList.get(position);
                    //接下来查询城市
                    queryCities();

                }else if (currentLevel == LEVEL_CITY){

                    //选中的城市
                    selectedCity = cityList.get(position);
                    //接下来查询县
                    queryCounties();

                }else if (currentLevel == LEVEL_CONTY){

                    String weatherId = countyList.get(position).getWeatherId();
                    Intent intent = new Intent(getActivity(),WeatherActivity.class);
                    intent.putExtra("weather_id",weatherId);
                    startActivity(intent);
                    getActivity().finish();

                }






            }
        });
        //返回上一级

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentLevel == LEVEL_CONTY){

                    //查询城市
                    queryCities();

                }else if (currentLevel == LEVEL_CITY){

                    //如果还要返回查询省
                    queryProvinces();

                }




            }
        });
        //
        queryProvinces();

    }

    /*

        查询选中市内所有的县，优先从数据库中查询，如果没有查询到再去服务器中查询

     */


    private  void queryProvinces(){




        titleText.setText("中国");

        backButton.setVisibility(View.GONE);

        provinceList = DataSupport.findAll(Province.class);

        if (provinceList.size() > 0){

            dataList.clear();
            for (Province province: provinceList){
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            //将第position的item显示在最上边
            listView.setSelection(0);

            currentLevel = LEVEL_PROVINCE;


        }else{

            //如果从数据库查不出来数据，就去服务器请求吧
            String address = "http://guolin.tech/api/china";
            //queryFromServer(address,"province");
            queryFromServer(address,"province");

        }

    }

    /*
        查询选中省里边所有的市，优先从数据库查询，如果没有查询到，再到服务器查询
     */


    private void queryCities(){
        /*

        City里边的内容
            private int id;
            private String cityName;
            private int cityCode;
            private int provinceId;



        det_url = "http://guolin.tech/api/china/13"，

        比如我们点击了第13个，这时候因为我们还没有创建数据库，所以先去服务器请求数据，请求完数据的时候，顺带对数据进行了保存，model里所有的所有provinceId都被设置成了
        13，内容展示出来，如果下一次我们再次点击了这个省13传入，和provinceId做对比，发现一致了，就把所有的内容都给取出来

        省：[{'id': 1, 'name': '北京'}, {'id': 2, 'name': '上海'},...]
        市：[{'id': 67, 'name': '郑州'}, {'id': 68, 'name': '安阳'},...]
         */

        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        //int 转字符串，这里边的id是主键id
        cityList = DataSupport.where("provinceid = ?",String.valueOf(selectedProvince.getId()))
        .find(City.class);
        //
        if (cityList.size() > 0){

            dataList.clear();
            for (City city: cityList){
                dataList.add(city.getCityName());
            }

            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;


        }else{

            //学我者生，似我者死
            int provinceCode = selectedProvince.getProvinceCode();
            String address = "http://guolin.tech/api/china/" + provinceCode;
            //查询 query
            queryFromServer(address,"city");

        }
    }


    /**
     * 查询选中市内所有的县，优先从数据库查询，如果没有查询到再去服务器上查询。
     */
    private void queryCounties() {
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        //这一点是getId，id相当于主键编号，
        //[{'id': 67, 'name': '郑州'}, {'id': 68, 'name': '安阳'}, {'id': 69, 'name': '新乡'}, {'id': 70, 'name': '许昌'}
        //code 也就是这里边的id是不按顺序的
        countyList = DataSupport.where("cityid = ?", String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county : countyList) {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CONTY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String address = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
            queryFromServer(address, "county");
        }
    }





    /*
        根据传入的地址和类，从服务器查询省市县数据

     */

    private void queryFromServer(String address, final String type){

        //加载提示
        showProgressDialog();

        Log.d(TAG, "=======" );

        HttpUtil.sendOKHttpRequest(address, new Callback() {


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //根据类型对结构进行储存
                String responseText = response.body().string();

                boolean result = false;
                if ("province".equals(type)){
                    result = Utility.handleProvinceResponse(responseText);
                }else if("city".equals(type)){
                    //可以看到provinceId就是一个顺序编号
                    result = Utility.handleCityResponse(responseText,selectedProvince.getId());
                }else if ("county".equals(type)){
                    //cityId也只是一个顺序编号，先存再在下一级对比
                    result = Utility.handleCountyResponse(responseText,selectedCity.getId());
                }
                if (result){

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            closeProgressDialog();
                            if ("province".equals(type)){

                               queryProvinces();
                            }else if("city".equals(type)){
                                queryCities();
                            }else if ("county".equals(type)){
                                queryCounties();
                            }
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call call, IOException e) {

                //通过runOnUiThread()方法，返回主线程处理逻辑
                getActivity().runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {

                        Log.d(TAG, "run: " + "444444444");

                        closeProgressDialog();
                        //Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });



            }

        });









    }


    /*
       显示进度对话框

     */

//    dialog弹出后会点击屏幕，dialog不消失；点击物理返回键dialog消失

    private  void showProgressDialog(){

        if (progressDialog == null){

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("loading");
            //点击屏幕不消失，点击物理返回键消失
            progressDialog.setCanceledOnTouchOutside(false);

        }
        progressDialog.show();

    }

    /*

        关闭进度对话框

     */
    private  void closeProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();

        }



    }






























}
