package nyc.c4q.unit5midretake;

import java.util.Map;

import nyc.c4q.unit5midretake.model.UserResults;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by AmyRivera on 2/20/18.
 */

public interface RetrofitService {
    @GET("/api/")
    Call<UserResults> getResults(@QueryMap Map<String, String> queries);

}