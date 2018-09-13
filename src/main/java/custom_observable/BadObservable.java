package custom_observable;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;

public class BadObservable implements ObservableOnSubscribe<Integer> {

    public BadObservable() {

    }

    public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {

        observableEmitter.setCancellable(new Cancellable() {
            public void cancel() throws Exception {
                System.out.println("cancel called");
            }
        });
//
        observableEmitter.setDisposable(new Disposable() {

            boolean isDisposed = false;

            public void dispose() {
                isDisposed = true;
                System.out.println("dispose called");
            }

            public boolean isDisposed() {
                System.out.println("isDisposed() called");
                return isDisposed;
            }
        });

        int j = 0;
        for (; ; ) {
            observableEmitter.onNext(++j);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException x) {
            }
        }
    }
}
