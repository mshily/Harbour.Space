import com.vina_esima.classexercises.data.SearchItem
import com.vina_esima.classexercises.data.UnsplashItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val ACCESS_KEY = "Hy6iXm_4xiOVbqyHH_DOEiXF84j97eQJ4MsH8HuNmx8"
private const val AUTH_HEADER = "Authorization: Client-ID $ACCESS_KEY"
interface UnsplashApi {
    @Headers(AUTH_HEADER)
    @GET("photos")
    fun fetchPhotos() : Call<List<UnsplashItem>>

    @Headers(AUTH_HEADER)
    @GET("/search/photos")
    fun SearchPhotos(@Query ("query") query: String) : Call<SearchItem>
}