package com.puzzle_agency.androidknowledge.knowledge.local_storage.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Database(
    entities = [SampleEntity::class],
    version = 1
)
abstract class SampleDatabase : RoomDatabase() {
    abstract val sampleDao: SampleDao
}

@Module
@InstallIn(SingletonComponent::class)
object SampleRoomModule {

    @Provides
    @Singleton
    fun provideSampleDatabase(@ApplicationContext context: Context): SampleDatabase =
        Room.databaseBuilder(
            context,
            SampleDatabase::class.java,
            "sample.db"
        ).build()

}