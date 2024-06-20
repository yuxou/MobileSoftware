package ddwu.com.mobile.finalreport.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import ddwu.com.mobile.finalreport.R

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
                    "$COL_TITLE TEXT, $COL_IMAGE INT, $COL_RATE REAL, " +
                    "$COL_ACTOR TEXT, $COL_DIRECTOR TEXT, " +
                    "$COL_RELEASE_DATE TEXT, $COL_WATCH_DATE TEXT)"
        Log.d("MovieDbHelper", CREATE_TABLE)
        db?.execSQL(CREATE_TABLE)

        // 샘플 데이터 삽입
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (01, '엘리멘탈', ${R.mipmap.movie4}, 4.5, '레아 루이스, 마무두 아티', '피터 손', '2023-06-14', '2023-06-15')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (02, '인사이드아웃2', ${R.mipmap.movie1}, 4.5, '에이미 포엘러, 마야 호크', '켈시 맨', '2024-06-12', '2024-06-14')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (03, '파묘', ${R.mipmap.movie2}, 5.0, '최민식, 김고은', '장재현', '2024-02-22', '2024-02-25')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (04, '웡카', ${R.mipmap.movie3}, 4.0, '티모시 샬라메', '폴 킹', '2024-01-31','2024-01-31')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (05, '혹성탈출4', ${R.mipmap.movie5}, 3.5, '오웬 티그, 프레이아 앨런', '웨스 볼', '2024-05-08', '2024-05-12')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
        val DROP_TABLE ="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }
}