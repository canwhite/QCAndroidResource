package com.example.melonweather.db;

import org.litepal.crud.DataSupport;

public class Province extends DataSupport {

    private  int id;
    private  String provinceName;
    private  int provinceCode;


    public String getProvinceName() {
        return provinceName;
    }

    public int getId() {
        return id;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }


}
