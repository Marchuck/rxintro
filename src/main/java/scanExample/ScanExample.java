package scanExample;

import io.reactivex.Observable;

import java.util.List;

public class ScanExample {


    public static void main(String[] args) {

        List<Integer> list = Observable.range(1, 10)

                .scan(1, (factorial, nextInt) -> factorial * nextInt)
                .toList()
                .blockingGet();

        System.out.println("factorial results: " + list);
    }
}
