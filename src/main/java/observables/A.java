package observables;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import java.util.Arrays;

public class A {
    public static void main(String[] args) {


        Observable.fromIterable(Arrays.asList(1, 2, 3, 4, 5, 6))

                .flatMap((Function<Integer, ObservableSource<String>>) integer -> {

                    if (integer == 3) {
                        try{

                        }catch (NullPointerException x){
                            return Observable.just("");
                        }
                    }
                    return Observable.just(integer + "::");
                })


//                .onErrorReturn(new Function<Throwable, String>() {
//                    @Override
//                    public String apply(Throwable throwable) throws Exception {
//                        return "-1";
//                    }
//                })
                .subscribe(s -> System.out.println("onNext " + s),
                        throwable -> System.err.println("error: " + throwable));
    }

    static class WoskoException extends Exception {

    }
}
