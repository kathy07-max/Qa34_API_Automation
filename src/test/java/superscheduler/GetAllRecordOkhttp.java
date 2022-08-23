package superscheduler;

import com.google.gson.Gson;
import dtosuper.ErrorResponseDto;
import dtosuper.GetAllRecRequestDto;
import dtosuper.GetAllRecResponseDto;
import dtosuper.RecordeDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetAllRecordOkhttp {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json");
    OkHttpClient client = new OkHttpClient();
   String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im1hc2hhMDBAZ21haWwuY29tIn0.59abwypA8V_kepsNQ7HvKzHR8Gd6w58KUdi6xtuaqn8";


    @Test
    public void getAllRecordeSuccess() throws IOException {
        GetAllRecRequestDto getAll = GetAllRecRequestDto.builder()
               .monthFrom(1).monthTo(12).yearFrom(2021).yearTo(2022).build();
        RequestBody body = RequestBody.create(gson.toJson(getAll),JSON);
        Request request = new Request.Builder().url("http://super-scheduler-app.herokuapp.com/api/records")
               .post(body).addHeader("Authorization",token).build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);
        GetAllRecResponseDto allRecords = gson.fromJson(response.body().string(),GetAllRecResponseDto.class);
        List<RecordeDto> records = allRecords.getRecords();
        for (RecordeDto rec: records) {
            System.out.println(rec.toString());
            System.out.println("*****************************");
        }

    }
    @Test
    public void getAllRecordeWrongMonth() throws IOException {
        GetAllRecRequestDto getAll = GetAllRecRequestDto.builder()
               .monthFrom(1).monthTo(13).yearFrom(2021).yearTo(2022).build();
        RequestBody body = RequestBody.create(gson.toJson(getAll),JSON);
        Request request = new Request.Builder().url("http://super-scheduler-app.herokuapp.com/api/records")
               .post(body).addHeader("Authorization",token).build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),400);
        ErrorResponseDto error = gson.fromJson(response.body().string(),ErrorResponseDto.class);
        Assert.assertEquals(error.getMessage(),"Wrong month period! Month from and to need be in range 1-12");
        System.out.println("Details: "+error.getDetails());
    }
    @Test
    public void getAllRecordeWrongYear() throws IOException {
        GetAllRecRequestDto getAll = GetAllRecRequestDto.builder()
               .monthFrom(1).monthTo(12).yearFrom(2017).yearTo(2022).build();
        RequestBody body = RequestBody.create(gson.toJson(getAll), JSON);
        Request request = new Request.Builder().url("http://super-scheduler-app.herokuapp.com/api/records")
               .post(body).addHeader("Authorization", token).build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 400);
        ErrorResponseDto error = gson.fromJson(response.body().string(), ErrorResponseDto.class);
        Assert.assertEquals(error.getMessage(), "Wrong year period!Year from and to needs be in range currentYear - 2 years and currentYear + 2 years");
    }
    @Test
    public void getAllRecordeWrongAuth() throws IOException {
        GetAllRecRequestDto getAll = GetAllRecRequestDto.builder()
               .monthFrom(1).monthTo(12).yearFrom(2020).yearTo(2022).build();
        RequestBody body = RequestBody.create(gson.toJson(getAll), JSON);
        Request request = new Request.Builder().url("http://super-scheduler-app.herokuapp.com/api/records")
               .post(body).addHeader("Authorization", "fgjdwhdv67").build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 401);
        ErrorResponseDto error = gson.fromJson(response.body().string(), ErrorResponseDto.class);
        Assert.assertEquals(error.getMessage(), "Wrong authorization format");//message is not correct
    }
}
