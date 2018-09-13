package mapExample;

import io.reactivex.Observable;
import io.reactivex.Single;

import java.util.List;

public class MapExample {

    static class MarkerData {
    }

    static class GoogleMap {
    }

    interface API {

        Single<List<Integer>> getPlaces();

        Single<MarkerData> getMarkerDetail(int id);

    }

    API api;

    public void fillMApWithMarkers() {

        Observable.zip(getMapAsync(), getMarkersData(), MapAndMarkerData::new)
                .subscribe(mapAndMarkerData -> System.out.println("map and marker data : " + mapAndMarkerData),
                throwable -> System.err.println("error: " + throwable)
        );
    }

    static class MapAndMarkerData {
        final GoogleMap map;
        final List<MarkerData> data;

        public MapAndMarkerData(GoogleMap map, List<MarkerData> data) {
            this.map = map;
            this.data = data;
        }
    }

    private Observable<List<MarkerData>> getMarkersData() {

        return api.getPlaces().toObservable()
                .flatMap(ids -> Observable.fromIterable(ids)) // can be replaced with .flatMapIterable(x->x)
                .flatMap(uniqueId -> api.getMarkerDetail(uniqueId).toObservable()).onErrorResumeNext(Observable.empty())
                .toList()
                .toObservable()
                //additional constraint
//                .filter(p -> !p.isEmpty())
//                .timeout(10, TimeUnit.SECONDS)

                ;

    }

    private Observable<GoogleMap> getMapAsync() {
        return Observable.fromCallable(() -> new GoogleMap());
    }
}
