package repository;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import java.util.concurrent.Callable;

public class RepositoryPatternExample {

    class ApiClient {
        Observable<Data> getFreshData() {
            return Observable.just(new Data());
        }
    }

    class Data {
    }

    interface DataRepository {
        Observable<Data> provideData();
    }


    public static class DefaultDataRepository implements DataRepository {

        private Data cachedData;

        ApiClient apiClient;

        public DefaultDataRepository(ApiClient apiClient) {
            this.apiClient = apiClient;
        }

        public Observable<Data> provideData() {

            final Data cachedData = getCachedData();

            if (cachedData != null) {

                return Observable.fromCallable(() -> cachedData)
                        .concatWith(
                        getFreshDataAndCache().onErrorResumeNext(throwable -> {
                            return Observable.empty();
                        }) //emits onNext(cache),onNext(fresh) | onNext(cache)
                );

            } else {
                return getFreshDataAndCache(); //emits onNext(fresh) | onError
            }
        }

        private Observable<Data> getFreshDataAndCache() {
            return apiClient.getFreshData().doOnNext(new Consumer<Data>() {
                public void accept(Data data) throws Exception {
                    cachedData = data;
                }
            });
        }


        private Data getCachedData() {
            return cachedData;
        }
    }
}
