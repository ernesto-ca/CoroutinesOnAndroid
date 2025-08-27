package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase14


import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersions

class FakeDatabase: AndroidVersionDao {
    var insertedIntoDb = false
    override  fun getAndroidVersions(): List<AndroidVersionEntity> {
        return mockAndroidVersions.mapToEntityList()
    }

    override  fun insert(androidVersionEntity: AndroidVersionEntity) {
        insertedIntoDb = true
    }

    override  fun clear() {
        insertedIntoDb = false
    }

}
