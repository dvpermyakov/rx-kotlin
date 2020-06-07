rx-kotlin operators
------------------------------------------------------------
[![CircleCI](https://circleci.com/gh/dvpermyakov/rx-kotlin/tree/master.svg?style=shield)](https://circleci.com/gh/dvpermyakov/rx-kotlin/tree/master)
[![Bintray](https://api.bintray.com/packages/dvpermyakov/maven/com.dvpermyakov.rx-kotlin/images/download.svg)](https://bintray.com/dvpermyakov/maven/com.dvpermyakov.rx-kotlin/_latestVersion)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

1. Creating
    * Create
    * Empty
    * FromCallable
    * FromList
    * Just
    * Never
    * Range
2. Transforming
    * Buffer
    * ConcatMap
    * FlatMap
    * Map
    * SwitchMap
3. Filtering
    * Distinct
    * Filter
    * TakeLast
4. Combining
    * CombineLatest
    * Concat
    * Merge
    * Zip
5. Utility
    * DoOnNext
    * DoOnSubscribe
    * ObserveOn
    * SubscrubeOn
6. Subjects
    * AsyncSubject
    * BehaviorSubject
    * PublishSubject
    * ReplaySubject
    
Gradle: 
```Groovy
repositories {
   jcenter()
}
dependencies {
   implementation 'com.dvpermyakov:rx-kotlin:0.1.2'
}
```

Example:
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
