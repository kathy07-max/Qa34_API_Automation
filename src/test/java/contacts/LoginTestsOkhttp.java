package contacts;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import dto.ErrorResponseDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTestsOkhttp {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();
//eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InNoZXZjaGVua29AbWFpbC5ydSJ9.FiANW0UYRAvq_mgCfGlLJCB3lUy_RTI656-PHoxCEPg
    @Test
    public void loginSuccess() throws IOException {
        AuthRequestDto auth = AuthRequestDto
               .builder().email("shevchenko@mail.ru").password("020985$Max").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
               .url("https://contacts-telran.herokuapp.com/api/login").post(requestBody).build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);
        AuthResponseDto responseDto = gson.fromJson(response.body().string(),AuthResponseDto.class);
        System.out.println(responseDto.getToken());
    }

    @Test
    public void loginWrongPasswordFormat() throws IOException {
        AuthRequestDto auth = AuthRequestDto
               .builder().email("shevchenko@mail.ru").password("024585max").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
               .url("https://contacts-telran.herokuapp.com/api/login").post(requestBody).build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 400);
        ErrorResponseDto errorResponseDto = gson.fromJson(response.body().string(), ErrorResponseDto.class);
        Assert.assertEquals(errorResponseDto.getMessage(),"Password must contain at least one uppercase letter!");
    }
    @Test
    public void loginWrongEmaildFormat() throws IOException {
        AuthRequestDto auth = AuthRequestDto
               .builder().email("shevchenkomail.ru").password("024585$Max").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
               .url("https://contacts-telran.herokuapp.com/api/login").post(requestBody).build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 400);
        ErrorResponseDto errorResponseDto = gson.fromJson(response.body().string(), ErrorResponseDto.class);
        Assert.assertEquals(errorResponseDto.getMessage(),"Wrong email format! Example: name@mail.com");
    }
    @Test
    public void loginWrongPassword() throws IOException {
        AuthRequestDto auth = AuthRequestDto
               .builder().email("shevchenko@mail.ru").password("024585$Max").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
               .url("https://contacts-telran.herokuapp.com/api/login").post(requestBody).build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        //Assert.assertEquals(response.code(), 401);
        ErrorResponseDto errorResponseDto = gson.fromJson(response.body().string(), ErrorResponseDto.class);
        Assert.assertEquals(errorResponseDto.getMessage(),"Wrong email or password!");
    }
}
