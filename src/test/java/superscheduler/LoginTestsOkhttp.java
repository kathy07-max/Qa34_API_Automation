package superscheduler;

import com.google.gson.Gson;
import dtosuper.AuthRequestDto;
import dtosuper.AuthResponseDto;
import dtosuper.ErrorResponseDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTestsOkhttp {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json");
    OkHttpClient client = new OkHttpClient();
    //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im1hc2hhMDBAZ21haWwuY29tIn0.59abwypA8V_kepsNQ7HvKzHR8Gd6w58KUdi6xtuaqn8
    @Test
    public void loginSuccess() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("masha090@gmail.com").password("Mm020985$").build();
        RequestBody requestBody = RequestBody.create(gson.toJson(auth),JSON);
        Request request = new Request.Builder().url("http://super-scheduler-app.herokuapp.com/api/login")
               .post(requestBody).build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        AuthResponseDto responseDto = gson.fromJson(response.body().string(), AuthResponseDto.class);
        Assert.assertEquals(responseDto.getStatus(),"Login success");
        System.out.println(responseDto.getToken());
        Assert.assertFalse(responseDto.isRegistration());

    }
@Test
    public void loginSuccessWrongEmail() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("masha@gmail.com").password("Mm020985$").build();
        RequestBody requestBody = RequestBody.create(gson.toJson(auth),JSON);
        Request request = new Request.Builder().url("http://super-scheduler-app.herokuapp.com/api/login")
               .post(requestBody).build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        ErrorResponseDto responseDto = gson.fromJson(response.body().string(),ErrorResponseDto.class);
        Assert.assertEquals(responseDto.getMessage(),"Wrong email or password");
        Assert.assertEquals(responseDto.getCode(),401);

    }
}
