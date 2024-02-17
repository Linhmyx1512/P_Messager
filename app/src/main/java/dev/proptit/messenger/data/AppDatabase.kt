package dev.proptit.messenger.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.proptit.messenger.data.contact.Contact
import dev.proptit.messenger.data.contact.ContactDao
import dev.proptit.messenger.data.message.Message
import dev.proptit.messenger.data.message.MessageDao


@Database(entities = [Contact::class, Message::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile // giúp các thread luôn đọc giá trị mới nhất của INSTANCE
        private var INSTANCE: AppDatabase? = null

        @Synchronized // tại mỗi thời điểm, chỉ có một luồng thực thi có thể nhập khối mã này => đảm bảo cơ sở dữ liệu chỉ được khởi tạo một lần.
        fun getDatabase(context: Context) : AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "message_database"
                ).fallbackToDestructiveMigration() // xóa cơ sở dữ liệu cũ và tạo cơ sở dữ liệu mới
                    .build() // tạo cơ sở dữ liệu
                INSTANCE = instance
                return instance
            }
        }
    }

}