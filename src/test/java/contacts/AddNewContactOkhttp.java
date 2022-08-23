package contacts;

import com.google.gson.Gson;
import dto.ContactDto;
import dto.ErrorResponseDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddNewContactOkhttp {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();
String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InNoZXZjaGVua29AbWFpbC5ydSJ9.FiANW0UYRAvq_mgCfGlLJCB3lUy_RTI656-PHoxCEPg";

@Test
    public  void  addNewContactSuccess() throws IOException {
int i = (int)(System.currentTimeMillis()/1000)%3600;
    ContactDto contactDto = ContactDto.builder().name("Masha")
           .lastName("Treesha")
           .email("masha"+i+"@gmail.com")
           .address("Beer-Sheva")
           .description("universita friend")
           .phone("123654"+ i)
           .build();
    RequestBody body = RequestBody.create(gson.toJson(contactDto),JSON);
    Request request = new Request.Builder()
           .url("https://contacts-telran.herokuapp.com/api/contact")
           .post(body)
           .addHeader("Authorization",token).build();
    Response response = client.newCall(request).execute();
   Assert.assertTrue(response.isSuccessful());
    Assert.assertEquals(response.code(),200);


    ContactDto contact = gson.fromJson(response.body().string(), ContactDto.class);
    System.out.println(contact.getId());

}
    @Test
    public  void  addNewContactWithoutName() throws IOException {
        int i = (int)(System.currentTimeMillis()/1000)%3600;
        ContactDto contactDto = ContactDto.builder()
               .lastName("Treesha")
               .email("masha"+i+"@gmail.com")
               .address("Beer-Sheva")
               .description("universita friend")
               .phone("123654"+ i)
               .build();
        RequestBody body = RequestBody.create(gson.toJson(contactDto),JSON);
        Request request = new Request.Builder()
               .url("https://contacts-telran.herokuapp.com/api/contact")
               .post(body)
               .addHeader("Authorization",token).build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),400);

        ErrorResponseDto error = gson.fromJson(response.body().string(),ErrorResponseDto.class);
        Assert.assertEquals(error.getMessage(),"Wrong contact format! Name can't be empty!");

    }
    @Test
    public  void  addNewContactWrongAuth() throws IOException {
        int i = (int)(System.currentTimeMillis()/1000)%3600;
        ContactDto contactDto = ContactDto.builder()
               .name("Masha")
               .lastName("Treesha")
               .email("masha"+i+"@gmail.com")
               .address("Beer-Sheva")
               .description("universita friend")
               .phone("123654"+ i)
               .build();
        RequestBody body = RequestBody.create(gson.toJson(contactDto),JSON);
        Request request = new Request.Builder()
               .url("https://contacts-telran.herokuapp.com/api/contact")
               .post(body)
               .addHeader("Authorization","hdgfjshdgs").build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());


        ErrorResponseDto error = gson.fromJson(response.body().string(),ErrorResponseDto.class);
        Assert.assertEquals(error.getMessage(),"Wrong token format!");
        Assert.assertEquals(response.code(),401);
    }
}
