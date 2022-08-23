package superscheduler;

import com.google.gson.Gson;
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
}
