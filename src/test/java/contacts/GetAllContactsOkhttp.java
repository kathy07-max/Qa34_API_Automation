package contacts;

import com.google.gson.Gson;
import dto.ContactDto;
import dto.ErrorResponseDto;
import dto.GetAllContactsDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetAllContactsOkhttp {
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InNoZXZjaGVua29AbWFpbC5ydSJ9.FiANW0UYRAvq_mgCfGlLJCB3lUy_RTI656-PHoxCEPg";

    @Test
    public void getAllContactsSuccess() throws IOException {
        Request request = new Request.Builder()
               .url("https://contacts-telran.herokuapp.com/api/contact")
               .get()
               .addHeader("Authorization", token)
               .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 200);
        GetAllContactsDto contacts = gson.fromJson(response.body().string(), GetAllContactsDto.class);
        List<ContactDto> all = contacts.getContacts();
        for (ContactDto dto : all) {
            System.out.println(dto.toString());
        }
    }

    @Test
    public void getAllContactWrongToken() throws IOException {
        Request request = new Request.Builder()
               .url("https://contacts-telran.herokuapp.com/api/contact")
               .get()
               .addHeader("Authorization", "hgfjgd")
               .build();
        Response response = client.newCall(request).execute();
        ErrorResponseDto error = gson.fromJson(response.body().string(), ErrorResponseDto.class);
        Assert.assertEquals(error.getMessage(),"Wrong token format!");
        Assert.assertEquals(error.getCode(),401);
    }
}

