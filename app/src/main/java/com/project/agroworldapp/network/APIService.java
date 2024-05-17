package com.project.agroworldapp.network;

import com.project.agroworldapp.articles.model.CropsResponse;
import com.project.agroworldapp.articles.model.DiseasesResponse;
import com.project.agroworldapp.articles.model.FlowersResponse;
import com.project.agroworldapp.articles.model.FruitsResponse;
import com.project.agroworldapp.articles.model.HowToExpandResponse;
import com.project.agroworldapp.articles.model.InsectControlResponse;
import com.project.agroworldapp.weather.model.weather_data.WeatherResponse;
import com.project.agroworldapp.weather.model.weatherlist.WeatherDatesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    //https://api.openweathermap.org/data/2.5/weather?lat=19.075975&lon=72.87738&appid=92f4e9a9c233be99f0b33d1c58c72386
    //https://api.openweathermap.org/data/2.5/forecast?lat=44.34&lon=10.99&appid=92f4e9a9c233be99f0b33d1c58c72386
    //https://sheetdb.io/api/v1/4hm2n4jziczjy

    @GET("weather")
    Call<WeatherResponse> getWeatherData(
            @Query("lat") Double lat,
            @Query("lon") Double lon,
            @Query("appid") String apiKey);

    @GET("forecast")
    Call<WeatherDatesResponse> getWeatherForecastData(
            @Query("lat") Double lat,
            @Query("lon") Double lon,
            @Query("appid") String apiKey);

    @GET("b8553ao7vlgnq")
    Call<List<DiseasesResponse>> getDiseasesList();
    @GET("b8553ao7vlgnq")
    Call<List<DiseasesResponse>> getLocalizedDiseasesList();
    @GET("lf2mxa0pjhxby")
    Call<List<FruitsResponse>> getFruitsFromDB();
    @GET("lf2mxa0pjhxby")
    Call<List<FruitsResponse>> getLocalizedFruitsList();
    @GET("3msm2w9p63mkl")
    Call<List<FlowersResponse>> getFlowersList();
    @GET("3msm2w9p63mkl")
    Call<List<FlowersResponse>> getLocalizedFlowersList();
    @GET("94qvppd1vzgjj")
    Call<List<CropsResponse>> getListOfCrops();
    @GET("94qvppd1vzgjj")
    Call<List<CropsResponse>> getLocalizedCropsList();
    @GET("mzhhp7cm088y6")
    Call<List<HowToExpandResponse>> getListOfHowToExpandData();
    @GET("mzhhp7cm088y6")
    Call<List<HowToExpandResponse>> getLocalizedHowToExpandData();
    @GET("zbdbnbgg4ez04")
    Call<List<InsectControlResponse>> getInsectAndControlList();
    @GET("zbdbnbgg4ez04")
    Call<List<InsectControlResponse>> getLocalizedInsectAndControlList();
}
