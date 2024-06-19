package ddwu.com.mobile.finalreport

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ddwu.com.mobile.finalreport.data.MovieDBHelper
import ddwu.com.mobile.finalreport.data.MovieDTO
import ddwu.com.mobile.finalreport.data.MovieDAO
import ddwu.com.mobile.finalreport.databinding.ActivityAddMovieBinding

class AddActivity : AppCompatActivity() {

    val addMovieBinding by lazy {
        ActivityAddMovieBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(addMovieBinding.root)

        addMovieBinding.btnAddSave.setOnClickListener {
            val title = addMovieBinding.etAddTitle.text.toString()
            val image = addMovieBinding.etAddImage.text.toString()
            val rate = addMovieBinding.rbAddRate.rating
            val actor = addMovieBinding.etAddActor.text.toString()
            val director = addMovieBinding.etAddDirector.text.toString()
            val releaseDate = addMovieBinding.etAddReleaseDate.text.toString()
            val watchDate = addMovieBinding.etAddWatchDate.text.toString()

            if (title.isNotEmpty() && rate > 0 && watchDate.isNotEmpty()) {
                if (addMovie(MovieDTO(0, title, image, rate, actor, director, releaseDate, watchDate)) > 0
                ) {
                    setResult(RESULT_OK)
                } else {
                    setResult(RESULT_CANCELED)
                }
                finish()
            } else {
                Toast.makeText(this, "영화명, 평점, 관람 날짜는 필수 입력 항목입니다.", Toast.LENGTH_SHORT).show()
            }
        }
        addMovieBinding.btnAddCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    fun addMovie(newDTO: MovieDTO): Long {
        val helper = MovieDBHelper(this)
        val db = helper.writableDatabase

        val newValue = ContentValues().apply {
            put(MovieDBHelper.COL_TITLE, newDTO.title)
            put(MovieDBHelper.COL_IMAGE, newDTO.image)
            put(MovieDBHelper.COL_RATE, newDTO.rate)
            put(MovieDBHelper.COL_ACTOR, newDTO.actor)
            put(MovieDBHelper.COL_DIRECTOR, newDTO.director)
            put(MovieDBHelper.COL_RELEASE_DATE, newDTO.releaseDate)
            put(MovieDBHelper.COL_WATCH_DATE, newDTO.watchDate)
        }

        val newCount = db.insert(MovieDBHelper.TABLE_NAME, null, newValue)

        helper.close()

        return newCount
    }
}