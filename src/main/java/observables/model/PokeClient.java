package observables.model;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokeClient {

    final PokeApi api;

    public PokeClient() {

        api = new Retrofit.Builder()
                .baseUrl(PokeApi.endpoint)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PokeApi.class);
    }

    public Single<Poke> getPokemon(int id) {
        return api.getPokemon(id);
    }
}
