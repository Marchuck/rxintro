package subjects;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class SubjectsExample {

    void publishExample() {
        testSubject(PublishSubject.create());
    }

    void behaviorExample() {
        testSubject(BehaviorSubject.create());
    }

    void replayExample() {
        testSubject(ReplaySubject.create());
    }

    private void testSubject(Subject<String> subject) {

        System.out.println("Testing " + subject.getClass().getSimpleName());

        subject.onNext("A");

        Disposable subscription = subject.subscribe(System.out::println);

        subject.onNext("B");
        subject.onNext("C");

        subscription.dispose();

        subject.onNext("D");

        System.out.println("\nagain\n");
        subscription = subject.subscribe(System.out::println);
    }

    public static void main(String[] args) {

        new SubjectsExample().replayExample();


        while (true) {

        }
    }


    private void fooo() {


        Observable<String> obsvable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                int j = 0;
                for (int i = 0; i < 10; i++) {

                }

            }
        });


        Observable<Integer> integerObservable = Observable.just(1);
        Observable.fromIterable(Arrays.asList(1, 2, 3, 4));

        Observable<Integer> integerObservable1 = Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 2;
            }
        });

    }
}
