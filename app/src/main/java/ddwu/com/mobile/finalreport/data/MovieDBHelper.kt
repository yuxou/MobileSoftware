package ddwu.com.mobile.finalreport.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

class MovieDBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    val TAG = "MovieDBHelper"

    companion object {
        const val DB_NAME = "movie_db"
        const val TABLE_NAME = "movie_table"
        const val COL_TITLE = "title"
        const val COL_IMAGE = "image"
        const val COL_RATE = "rate"
        const val COL_ACTOR = "actor"
        const val COL_DIRECTOR = "director"
        const val COL_RELEASE_DATE = "release_date"
        const val COL_WATCH_DATE = "watch_date"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_TITLE TEXT, $COL_IMAGE TEXT, $COL_RATE REAL, " +
                    "$COL_ACTOR TEXT, $COL_DIRECTOR TEXT, " +
                    "$COL_RELEASE_DATE TEXT, $COL_WATCH_DATE TEXT)"
        Log.d("MovieDbHelper", CREATE_TABLE)
        db?.execSQL(CREATE_TABLE)

        // 샘플 데이터 삽입
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (01, 'Inception', 'path/to/image', 4.5, 'Leonardo DiCaprio', 'Christopher Nolan', '2010-07-16', '2020-01-01')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (02, 'Interstellar', 'path/to/image', 5.0, 'Matthew McConaughey', 'Christopher Nolan', '2014-11-07', '2020-02-01')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (03, 'Parasite', 'path/to/image', 4.8, 'Song Kang-ho', 'Bong Joon-ho', '2019-05-30', '2020-03-01')")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
        val DROP_TABLE ="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }
}