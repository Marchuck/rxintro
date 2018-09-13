package cryptoExample;

import io.reactivex.Observable;
import io.reactivex.disposables.SerialDisposable;
import io.reactivex.functions.Predicate;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CryptoComputationExample {

    CryptoComputationExample(String publicKey) {
        this.publicKey = publicKey;
    }

    final String accountNumber = "xrb_1q3hqecaw15cjt7thbtxu3pbzr1eihtzzpzxguoc37bj1wc5ffoh7w74gi6p";
    final String publicKey;

    final String privateKey = "mocked private key";


    private SerialDisposable serialDisposable = new SerialDisposable();

    public void startObservingHashes() {

        serialDisposable.set(
                Observable.interval(1, 15, TimeUnit.SECONDS)
                        .flatMap(nextSecondElapsed -> getPendingHashes(accountNumber))
                        .filter(new Predicate<PendingBlocksResponse>() {
                            @Override
                            public boolean test(PendingBlocksResponse pendingBlocksResponse) throws Exception {
                                return !pendingBlocksResponse.isEmpty();
                            }
                        })
//                        .flatMap(getPendingHashesResponse -> {
//                            if (getPendingHashesResponse.isEmpty()) {
//                                return Observable.empty();
//                            } else {
//                                return Observable.just(getPendingHashesResponse);
//                            }
//                        })
                        .flatMapIterable(getPendingHashesResponse -> getPendingHashesResponse.hashes)
                        .flatMap(this::processBlockBasedOnPendingHash)
                        .subscribe()
        );

    }

    private Observable<ProcessResponse> processBlockBasedOnPendingHash(String hash) {
        return getBlockInfo(hash)
                .flatMap(blockResponse -> {

                    String balance = blockResponse.balance;
                    String amount = blockResponse.amount;

                    return getAccountInfo(accountNumber)
                            .flatMap(accountInfoResponse -> {

                                String _frontier = accountInfoResponse.frontier;

                                if (accountInfoResponse.isFreshAccount()) {
                                    _frontier = publicKey;
                                }

                                final String frontier = _frontier;

                                return generateWork(frontier)
                                        .flatMap(workResponse -> {

                                            String dataToSign = computeStateHash(publicKey, frontier, hash, balance, amount);
                                            String signature = computeSignature(privateKey, dataToSign);
                                            return processBlock(dataToSign, signature)
                                                    .onErrorReturnItem(new ProcessResponse());

                                        });
                            });

                });
    }


    class ProcessResponse {
    }

    private Observable<ProcessResponse> processBlock(String dataToSign, String signature) {
        return Observable.just(new ProcessResponse());
    }

    private String computeSignature(String privateKey, String dataToSign) {
        return null;
    }

    private String computeStateHash(String publicKey, String frontier, String hash, String balance, String amount) {
        return null;
    }

    private Observable<AccountResponse> getAccountInfo(String accountNumber) {
        return null;
    }

    private Observable<?> generateWork(String frontier) {
        return null;
    }

    private Observable<BlockInfo> getBlockInfo(String hash) {
        return Observable.just(new BlockInfo());
    }


    private Observable<PendingBlocksResponse> getPendingHashes(String accountNumber) {
        //todo: replace with real request
        return Observable.just(new PendingBlocksResponse(accountNumber))
                .onErrorResumeNext(throwable -> {
                    return Observable.just(PendingBlocksResponse.EMPTY);
                });
    }

    static class AccountResponse {
        String frontier;

        public boolean isFreshAccount() {
            return false;
        }
    }

    static class BlockInfo {

        String balance, amount;
    }

    static class PendingBlocksResponse {

        String account;

        List<String> hashes = new ArrayList<>();

        boolean isEmpty() {
            return hashes.isEmpty();
        }

        static PendingBlocksResponse EMPTY = new PendingBlocksResponse();

        public PendingBlocksResponse() {
        }

        public PendingBlocksResponse(String account) {
            this.account = account;
        }
    }
}
