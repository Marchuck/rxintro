package observables;

import observables.model.Poke;

import java.util.ArrayList;
import java.util.List;

public class PokesNonRxPseudoCode {

    public static void main(String[] args) {

        new PokesNonRxPseudoCode().imperativeFlow();

    }

    int count = 10;

    void imperativeFlow() {

        List<Poke> tallPokes = new ArrayList<>();

        int height = 7;

        for (int i = 1; i <= count; i++) {

            Poke poke;
            try {
                poke = performHttpRequest(i);
            } catch (SomeExpection expection) {
                handleError(expection);
                return;
            }

            System.out.println(poke.name + " arrived!");

            if (poke.height > height) {
                tallPokes.add(poke);
            }
        }
        handleTallPokes(tallPokes);

    }

    private void handleError(Throwable throwable) {
        System.out.println("Error fetching pokes...");
        throwable.printStackTrace();
        System.exit(1);
    }

    private void handleTallPokes(List<Poke> tallPokes) {
        System.out.println("\n" + "there are " + tallPokes.size() + " of " + count + " pokes which have height grater than " + 7 + "\n");
    }


    private Poke performHttpRequest(int i) throws SomeExpection {
        return new Poke();
    }

    static class SomeExpection extends Exception {

    }
}
