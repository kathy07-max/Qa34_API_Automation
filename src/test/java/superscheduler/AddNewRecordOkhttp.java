package superscheduler;

import com.google.gson.Gson;
import dtosuper.DateDto;
import dtosuper.ErrorResponseDto;
import dtosuper.NewRecResponseDto;
import dtosuper.RecordeDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddNewRecordOkhttp {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json");
    OkHttpClient client = new OkHttpClient();
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im1hc2hhMDBAZ21haWwuY29tIn0.59abwypA8V_kepsNQ7HvKzHR8Gd6w58KUdi6xtuaqn8";

    @Test
    public void addNewRecordeSuccess() throws IOException {
        RecordeDto recorde = RecordeDto.builder().breaks(2).currency("43")
               .date(DateDto.builder().dayOfMonth(26)
                      .dayOfWeek("std")
                      .month(7)
                      .year(2022).build())
               .hours(3)
               .id(12).timeFrom("08:00").timeTo("12:00")
               .title("my title")
               .totalSalary(37)
               .type("type").wage(54).build();
        RequestBody body = RequestBody.create(gson.toJson(recorde),JSON);
        Request request = new Request.Builder().url("http://super-scheduler-app.herokuapp.com/api/record")
               .post(body).addHeader("Authorization",token).build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);
        NewRecResponseDto newRecorde = gson.fromJson(response.body().string(),NewRecResponseDto.class);
        System.out.println("New recorde was added success with ID: "+newRecorde.getId());

    }

    @Test
    public void addNewRecordeWrongToken() throws IOException {
        RecordeDto recorde = RecordeDto.builder().breaks(2).currency("43")
               .date(DateDto.builder().dayOfMonth(12)
                      .dayOfWeek("std")
                      .month(3)
                      .year(2022).build())
               .hours(3)
               .id(12).timeFrom("08:00").timeTo("12:00")
               .title("my title")
               .totalSalary(37)
               .type("type").wage(54).build();
        RequestBody body = RequestBody.create(gson.toJson(recorde), JSON);
        Request request = new Request.Builder().url("http://super-scheduler-app.herokuapp.com/api/record")
               .post(body).addHeader("Authorization", "dghjfd").build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 401);
        ErrorResponseDto error = gson.fromJson(response.body().string(),ErrorResponseDto.class);
        Assert.assertEquals(error.getMessage(),"Wrong authorization format");
    }

}
