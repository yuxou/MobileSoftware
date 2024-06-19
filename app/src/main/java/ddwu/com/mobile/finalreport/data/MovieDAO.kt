package ddwu.com.mobile.finalreport.data

import android.annotation.SuppressLint
import android.content.Context
import android.provider.BaseColumns
import ddwu.com.mobile.finalreport.data.MovieDBHelper
import ddwu.com.mobile.finalreport.data.MovieDTO

class MovieDAO (val context: Context) {
    fun deleteMovie(dto: MovieDTO): Int {
        val helper = MovieDBHelper(context)
        val db = helper.writableDatabase

        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(dto.id.toString())

        return db.delete(MovieDBHelper.TABLE_NAME, whereClause, whereArgs)
    }

    @SuppressLint("Range")
    fun getAllMovies(): ArrayList<MovieDTO> {
        val helper = MovieDBHelper(context)
        val db = helper.readableDatabase
        val cursor = db.query(MovieDBHelper.TABLE_NAME, null, null, null, null, null, null)

        val movies = arrayListOf<MovieDTO>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndex(BaseColumns._ID))
                val title = getString(getColumnIndex(MovieDBHelper.COL_TITLE))
                val image = getString(getColumnIndex(MovieDBHelper.COL_IMAGE))
                val rate = getFloat(getColumnIndex(MovieDBHelper.COL_RATE))
                val actor = getString(getColumnIndex(MovieDBHelper.COL_ACTOR))
                val director = getString(getColumnIndex(MovieDBHelper.COL_DIRECTOR))
                val releaseDate = getString(getColumnIndex(MovieDBHelper.COL_RELEASE_DATE))
                val watchDate = getString(getColumnIndex(MovieDBHelper.COL_WATCH_DATE))
                val dto = MovieDTO(id, title, image, rate, actor, director, releaseDate, watchDate)
                movies.add(dto)
            }
        }
        cursor.close()
        return movies
    }
}