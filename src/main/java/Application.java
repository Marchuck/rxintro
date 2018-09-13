import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class Application {


    public static void main(String[] args) {


        final Disposable disposable = Observable.create(new ObservableOnSubscribe<Integer>() {

            boolean disposed = false;

            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                int j = 0;

                emitter.setCancellable(new Cancellable() {
                    public void cancel() throws Exception {
                        System.out.println("cancel called");
                        disposed = true;
                    }
                });
                emitter.setDisposable(new Disposable() {

                    public void dispose() {
                        System.out.println("dispose() called");
                        disposed = true;
                    }

                    public boolean isDisposed() {
                        System.out.println("isDisposed called");
                        return disposed;
                    }
                });

                while (true) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(++j);

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ignored) {
                        }
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    public void accept(Integer integer) {
                        System.out.println("onNext(" + integer + ")");
                    }
                }, new Consumer<Throwable>() {
                    public void accept(Throwable throwable) {
                        System.out.println("on error");
                    }
                });

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        disposable.dispose();

        while (true) {
        }
    }
}