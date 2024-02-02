package dev.proptit.messenger

import android.app.Application
import android.content.Context
import dev.proptit.messenger.data.AppDatabase

class MyApp: Application() {
    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }

    companion object {
        @Volatile // giúp các thread luôn đọc giá trị mới nhất của INSTANCE
        private lateinit var INSTANCE: MyApp

        @Synchronized // tại mỗi thời điểm, chỉ có một luồng thực thi có thể nhập khối mã này => đảm bảo cơ sở dữ liệu chỉ được khởi tạo một lần.
        fun getInstance(): MyApp {
            if (!this::INSTANCE.isInitialized) {
                INSTANCE = MyApp()
                return INSTANCE
            }
            return INSTANCE
        }

        fun context(): Context {
            return getInstance().applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

}