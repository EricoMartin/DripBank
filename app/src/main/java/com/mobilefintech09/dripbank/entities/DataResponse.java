package com.mobilefintech09.dripbank.entities;

import com.squareup.moshi.Json;

public class DataResponse {

    @Json(name = "data")
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


}
