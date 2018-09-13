package observables;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.operators.observable.ObservableError;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import observables.model.Poke;
import observables.model.PokeClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PokesExample {

    final PokeClient client = new PokeClient();

    List<Integer> pokeIds;

    private void run() {

        final List<Integer> _pokeIds = Arrays.asList(1, 3, 5, 6, 7, 9, 13, 25, 29, 55, 150, 102);

        pokeIds = Observable.range(1, 150)
                .toList()
                .map(this::randomize)
                .toObservable()
                .flatMapIterable(x -> x)
                .take(12)
                .toList()
                .blockingGet();


        SchedulerProvider provider = new SchedulerProvider() {
            @Override
            public Scheduler provideBgThread() {
                return Schedulers.trampoline();
            }
        };

        getPokesWhichHaveHeightGreaterThan(pokeIds, 7)
                .toObservable()
                .subscribeOn(provider.provideBgThread())
                .subscribe(this::handleTallPokes, this::handleError);
    }

    interface SchedulerProvider {
        Scheduler provideBgThread();
    }

    private void a() {

        Observable.fromIterable(Arrays.asList(1, 2, 2, 4, 5, 6))
                .flatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Exception {
                        if (integer != 2) return Observable.just(integer);
                        return Observable.empty();
                    }
                }).toList()
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        System.out.println("list: " + integers);
                    }
                });
    }

    private void handleError(Throwable throwable) {
        System.out.println("Error fetching pokes...");
        throwable.printStackTrace();
        System.exit(1);
    }

    private void handleTallPokes(List<Poke> tallPokes) {
        System.out.println("\n" + "there are " + tallPokes.size() + " of " + pokeIds.size() + " pokes which have height grater than " + 7 + "\n");
    }


    private List<Integer> randomPokesRxLongChain(int count) {
        return Observable.range(1, 150)
                .toList() // [1,2,3,...,150]
                .map(this::randomize) //random order, [21,37,9,..,11]
                .toObservable()
                .flatMapIterable(x -> x)
                .take(count)
                .toList()
                .blockingGet();
    }

    private List<Integer> randomPokesRx(int count) {
        return Observable.range(1, 150)
                .toList() // [1,2,3,...,150]
                .map(this::randomize) //random order, [21,37,9,..,11]
                .map(list -> list.subList(0, count))
                .blockingGet();
    }

    private List<Integer> randomPokesNormal(int count) {
        List<Integer> ids = new ArrayList<>();
        for (int i = 1; i <= 150; i++) {
            ids.add(i);
        }
        randomize(ids);

        return ids.subList(0, count);
    }

    private List<Integer> randomize(List<Integer> integers) {
        Collections.shuffle(integers);
        return integers;
    }

    private Single<List<Poke>> getPokesWhichHaveHeightGreaterThan(List<Integer> pokeIds, int height) {

        return Observable.fromIterable(pokeIds)
                .flatMap(id -> client.getPokemon(id).toObservable())
                .doOnNext(poke -> System.out.println(poke.name + " arrived!"))
                .filter(poke -> poke.height > height)
                .toList();
    }

    public static void main(String[] args) {

        System.out.println("PokesExample started");
        new PokesExample().a();

        while (true) {
            //no -op in order to make app alive
        }
    }


    void foo() {

    }


    BehaviorSubject<String> any = BehaviorSubject.createDefault("");
}
