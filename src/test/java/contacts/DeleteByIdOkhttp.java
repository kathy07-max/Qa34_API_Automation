package contacts;

import com.google.gson.Gson;
import dto.ContactDto;
import dto.ResponseDeleteByIdDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class DeleteByIdOkhttp {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InNoZXZjaGVua29AbWFpbC5ydSJ9.FiANW0UYRAvq_mgCfGlLJCB3lUy_RTI656-PHoxCEPg";
    int id;
    @BeforeMethod
    public void preconditions() throws IOException {
        ContactDto contactDto = ContactDto.builder().name("Masha")
               .lastName("Treesha")
               .email("ma$ha00@gmail.com")
               .address("Beer-Sheva")
               .description("universita friend")
               .phone("1236546574")
               .build();
        RequestBody body = RequestBody.create(gson.toJson(contactDto),JSON);
        Request request = new Request.Builder()
               .url("https://contacts-telran.herokuapp.com/api/contact")
               .post(body)
               .addHeader("Authorization",token).build();
        Response response = client.newCall(request).execute();
        ContactDto contact = gson.fromJson(response.body().string(), ContactDto.class);
        id = contact.getId();
    }
    @Test
    public void deleteByIdSuccess() throws IOException {
        Request request = new Request.Builder().url("https://contacts-telran.herokuapp.com/api/contact/"+id)
               .delete()
               .addHeader("Authorization", token)
               .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);

        ResponseDeleteByIdDto status = gson.fromJson(response.body().string(),ResponseDeleteByIdDto.class);
        Assert.assertEquals(status.getStatus(),"Contact was deleted!");
    }
}
