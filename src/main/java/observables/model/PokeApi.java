package observables.model;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokeApi {

    String endpoint = "http://pokeapi.co/api/v2/";

    @GET("pokemon/{id}")
    Single<Poke> getPokemon(@Path("id") int id);
}
