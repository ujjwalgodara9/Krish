package com.project.agroworldapp.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.agroworldapp.R;
import com.project.agroworldapp.articles.model.CropsResponse;
import com.project.agroworldapp.articles.model.DiseasesResponse;
import com.project.agroworldapp.articles.model.FlowersResponse;
import com.project.agroworldapp.articles.model.FruitsResponse;
import com.project.agroworldapp.articles.model.HowToExpandResponse;
import com.project.agroworldapp.articles.model.InsectControlResponse;
//import com.project.agroworldapp.payment.model.PaymentModel;
//import com.project.agroworldapp.shopping.model.ProductModel;
//import com.project.agroworldapp.transport.model.VehicleModel;
import com.project.agroworldapp.ui.repository.AgroWorldRepositoryImpl;
import com.project.agroworldapp.utils.Constants;
import com.project.agroworldapp.utils.Resource;
import com.project.agroworldapp.weather.model.weather_data.WeatherResponse;
import com.project.agroworldapp.weather.model.weatherlist.WeatherDatesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgroViewModel extends ViewModel {
    AgroWorldRepositoryImpl repository;
    private DatabaseReference databaseReference;
    private Context context;
    boolean selectedLanguage;

    public AgroViewModel(AgroWorldRepositoryImpl repository, Context context) {
        this.repository = repository;
        this.context = context;
        selectedLanguage = Constants.selectedLanguage(context);
    }

    /**
     *  Call the weather API & return the updated live data to views.
     *
     */
    private final MutableLiveData<Resource<WeatherResponse>> weatherResponseMutableLivedata = new MutableLiveData<>();
    public LiveData<Resource<WeatherResponse>> observeWeatherResponseLivedata = weatherResponseMutableLivedata;
    public void performWeatherRequest(double latitude, double longitude, String apiKey) {
        repository.getWeatherData(latitude, longitude, apiKey).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    weatherResponseMutableLivedata.setValue(Resource.success(response.body()));
                } else {
                    weatherResponseMutableLivedata.postValue(Resource.error(response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                weatherResponseMutableLivedata.postValue(Resource.error(t.getLocalizedMessage(), null));
            }
        });
    }

    /**
     *  For calling the weather forecast api & returning livedata to views.
     *
     */

    private final MutableLiveData<Resource<WeatherDatesResponse>> weatherDatesMutableLivedata = new MutableLiveData<>();
    public LiveData<Resource<WeatherDatesResponse>> observeWeatherDateResourceLiveData = weatherDatesMutableLivedata;

    public void performWeatherForecastRequest(double latitude, double longitude, String apiKey) {
        repository.getWeatherForecastData(latitude, longitude, apiKey).enqueue(new Callback<WeatherDatesResponse>() {
            @Override
            public void onResponse(Call<WeatherDatesResponse> call, Response<WeatherDatesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    weatherDatesMutableLivedata.setValue(Resource.success(response.body()));
                } else {
                    weatherDatesMutableLivedata.postValue(Resource.error(response.message(), null));
                }
            }
            @Override
            public void onFailure(Call<WeatherDatesResponse> call, Throwable t) {
                weatherDatesMutableLivedata.postValue(Resource.error(t.getLocalizedMessage(), null));
            }
        });
    }

    /**
     * For getting the data from sheetDB.
     * It will hit the diseases API & return livedata.
     */
    private final MutableLiveData<Resource<List<DiseasesResponse>>> diseasesMutableLiveData = new MutableLiveData<>();
    public LiveData<Resource<List<DiseasesResponse>>> observeDiseaseResponseLivedata = diseasesMutableLiveData;

    public void getDiseasesResponseLivedata() {
        Call<List<DiseasesResponse>> diseasesApiService;
        if (selectedLanguage) {
            diseasesApiService = repository.getLocalizedDiseasesList();
        } else {
            diseasesApiService = repository.getDiseasesList();
        }
        diseasesApiService.enqueue(new Callback<List<DiseasesResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<DiseasesResponse>> call, @NonNull Response<List<DiseasesResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    diseasesMutableLiveData.setValue(Resource.success(response.body()));
                } else {
                    diseasesMutableLiveData.setValue(Resource.error(context.getString(R.string.token_expired), null));
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<DiseasesResponse>> call, @NonNull Throwable t) {
                diseasesMutableLiveData.postValue(Resource.error(t.getLocalizedMessage(), null));
            }
        });
    }

    /**
     * For getting the data from sheetDB.
     * It will identify the language & as per key, It will hit the insect & control API & return livedata.
     */
    private final MutableLiveData<Resource<List<InsectControlResponse>>> insectControlMutableLiveData = new MutableLiveData<>();
    public LiveData<Resource<List<InsectControlResponse>>> observeInsectControlLiveData = insectControlMutableLiveData;

    public void getInsectAndControlLivedata() {
        Call<List<InsectControlResponse>> insectControlApiService;
        if (selectedLanguage) {
            insectControlApiService = repository.getLocalizedInsectAndControlList();
        } else {
            insectControlApiService = repository.getInsectAndControlList();
        }
        insectControlApiService.enqueue(new Callback<List<InsectControlResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<InsectControlResponse>> call, @NonNull Response<List<InsectControlResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    insectControlMutableLiveData.setValue(Resource.success(response.body()));
                } else {
                    insectControlMutableLiveData.setValue(Resource.error(context.getString(R.string.token_expired), null));
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<InsectControlResponse>> call, @NonNull Throwable t) {
                insectControlMutableLiveData.postValue(Resource.error(t.getLocalizedMessage(), null));
            }
        });
    }

    /**
     * For getting the data from sheetDB.
     * It will identify the language & as per key, It will hit the fruits API & return livedata.
     */
    private final MutableLiveData<Resource<List<FruitsResponse>>> fruitsMutableLiveData = new MutableLiveData<>();
    public LiveData<Resource<List<FruitsResponse>>> observeFruitsLiveData = fruitsMutableLiveData;

    public void getFruitsResponseLivedata() {
        Call<List<FruitsResponse>> fruitsApiService;
        if (selectedLanguage) {
            fruitsApiService = repository.getLocalizedFruitsList();
        } else {
            fruitsApiService = repository.getFruitsFromDB();
        }
        fruitsApiService.enqueue(new Callback<List<FruitsResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<FruitsResponse>> call, @NonNull Response<List<FruitsResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fruitsMutableLiveData.setValue(Resource.success(response.body()));
                } else {
                    fruitsMutableLiveData.setValue(Resource.error(context.getString(R.string.token_expired), null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<FruitsResponse>> call, @NonNull Throwable t) {
                fruitsMutableLiveData.postValue(Resource.error(t.getLocalizedMessage(), null));
            }
        });
    }

    /**
     * For getting the data from sheetDB.
     * It will identify the language & as per key, It will hit the How to expand API & return livedata.
     */
    private final MutableLiveData<Resource<List<HowToExpandResponse>>> howToExpandLivedata = new MutableLiveData<>();
    public LiveData<Resource<List<HowToExpandResponse>>> observeHowToExpandLivedata = howToExpandLivedata;

    public void getHowToExpandResponseLivedata() {
        Call<List<HowToExpandResponse>> howToExpandApiService;
        if (selectedLanguage) {
            howToExpandApiService = repository.getLocalizedHowToExpandData();
        } else {
            howToExpandApiService = repository.getListOfHowToExpandData();
        }
        howToExpandApiService.enqueue(new Callback<List<HowToExpandResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<HowToExpandResponse>> call, @NonNull Response<List<HowToExpandResponse>> response) {
                Constants.printLog(response.body() + " getHowToExpandResponse");
                if (response.isSuccessful() && response.body() != null) {
                    howToExpandLivedata.setValue(Resource.success(response.body()));
                } else {
                    howToExpandLivedata.setValue(Resource.error(context.getString(R.string.token_expired), null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<HowToExpandResponse>> call, @NonNull Throwable t) {
                Constants.printLog(t.getMessage() + " getHowToExpandResponse");
                howToExpandLivedata.postValue(Resource.error(t.getLocalizedMessage(), null));
            }
        });
    }

    /**
     * For getting the data from sheetDB.
     * It will identify the language & as per key, It will hit the crops API & return livedata.
     */
    private final MutableLiveData<Resource<List<CropsResponse>>> cropsMutableLiveData = new MutableLiveData<>();
    public LiveData<Resource<List<CropsResponse>>> observeCropsLiveData = cropsMutableLiveData;

    public void getCropsResponseLivedata() {
        Call<List<CropsResponse>> cropApiService;
        if (selectedLanguage) {
            cropApiService = repository.getLocalizedCropsList();
        } else {
            cropApiService = repository.getListOfCrops();
        }
        cropApiService.enqueue(new Callback<List<CropsResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<CropsResponse>> call, @NonNull Response<List<CropsResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cropsMutableLiveData.setValue(Resource.success(response.body()));
                } else {
                    cropsMutableLiveData.setValue(Resource.error(context.getString(R.string.token_expired), null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CropsResponse>> call, @NonNull Throwable t) {
                cropsMutableLiveData.postValue(Resource.error(t.getLocalizedMessage(), null));
            }
        });
    }

    /**
     * For getting the data from sheetDB.
     * It will identify the language & as per key, It will hit the flower API & update livedata.
     */
    private final MutableLiveData<Resource<List<FlowersResponse>>> flowersMutableLiveData = new MutableLiveData<>();
    public LiveData<Resource<List<FlowersResponse>>> observeFlowersLiveData = flowersMutableLiveData;

    public void getFlowersResponseLivedata() {
        Call<List<FlowersResponse>> flowerApi;
        if (selectedLanguage) {
            flowerApi = repository.getLocalizedFlowersList();
        } else {
            flowerApi = repository.getFlowersList();
        }
        flowerApi.enqueue(new Callback<List<FlowersResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<FlowersResponse>> call, @NonNull Response<List<FlowersResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    flowersMutableLiveData.setValue(Resource.success(response.body()));
                } else {
                    flowersMutableLiveData.postValue(Resource.error(context.getString(R.string.token_expired), null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<FlowersResponse>> call, @NonNull Throwable t) {
                flowersMutableLiveData.postValue(Resource.error(t.getLocalizedMessage(), null));
            }
        });
    }

}
