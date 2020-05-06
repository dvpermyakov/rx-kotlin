Rx-kotlin for educational purposes
------------------------------------------------------------
[![CircleCI](https://circleci.com/gh/dvpermyakov/rx-kotlin/tree/master.svg?style=shield)](https://circleci.com/gh/dvpermyakov/rx-kotlin/tree/master)
[![Bintray](https://api.bintray.com/packages/dvpermyakov/rx-kotlin/core/images/download.svg)](https://bintray.com/dvpermyakov/rx-kotlin/core/_latestVersion)

1. Creating
    * Create
    * Just
    * Empty
    * FromCallable
    * FromList
    * Range
2. Transforming
    * Buffer
    * Map
    * FlatMap
    * ConcatMap
    * SwitchMap
3. Filtering
    * Filter
    * Distinct
    * TakeLast
4. Combining
    * Merge
    * Zip
5. Utility
    * DoOnNext
    * DoOnSubscribe
    * Subscribe
    * SubscrubeOn
6. Subjects
    * AsyncSubject
    * BehaviorSubject
    * PublishSubject
    * ReplaySubject

```Kotlin
class TransactionInteractor {
    private val cardRepository = CardRepository()
    private val transactionRepository = TransactionRepository()

    fun getAllTransactions(): Observable<Transaction> {
        return Observable.concatList(
            listOf(
                cardRepository.getMyCards().subscribeOn(ThreadScheduler()),
                cardRepository.getOtherCards().subscribeOn(ThreadScheduler())
            )
        )
            .doOnNext { Log.e(TAG, "card = $it") }
            .subscribeOn(ThreadScheduler())
            .switchMap { card ->
                transactionRepository.getTransactions(card)
                    .subscribeOn(ThreadScheduler())
                    .doOnNext { Log.e(TAG, "transaction = $it for card = ${card.id}") }
            }
    }

    companion object {
        private const val TAG = "TransactionInteractor"
    }
}
```
