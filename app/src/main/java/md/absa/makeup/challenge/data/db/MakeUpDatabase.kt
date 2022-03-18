package md.absa.makeup.challenge.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import md.absa.makeup.challenge.data.db.converters.ProductColorConverter
import md.absa.makeup.challenge.data.db.converters.TagListConverter
import md.absa.makeup.challenge.data.db.dao.MakeUpItemDao
import md.absa.makeup.challenge.model.MakeUpItem

@Database(
    entities =
    [
        MakeUpItem::class
    ],
    exportSchema = false,
    version = 1
)
@TypeConverters(
    ProductColorConverter::class,
    TagListConverter::class
)
abstract class MakeUpDatabase : RoomDatabase() {

    abstract fun makeUpItemDao(): MakeUpItemDao

    companion object {

        private var INSTANCE: MakeUpDatabase? = null

        @Synchronized
        fun getInstance(
            context: Context
        ): MakeUpDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MakeUpDatabase::class.java,
                    "makeup_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }
    }
}
