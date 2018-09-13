package custom_observable;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class TheGoodObservable implements ObservableOnSubscribe<Integer> {

    public TheGoodObservable() {
    }

    public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {

        int j = 0;

        while (!observableEmitter.isDisposed()) {
            observableEmitter.onNext(++j);
        }
    }
}
