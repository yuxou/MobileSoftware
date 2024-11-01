package ddwu.com.mobile.finalreport

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ddwu.com.mobile.finalreport.data.MovieDBHelper
import ddwu.com.mobile.finalreport.data.MovieDTO
import ddwu.com.mobile.finalreport.databinding.ActivityAddMovieBinding

class AddActivity : AppCompatActivity() {

    val addMovieBinding by lazy {
        ActivityAddMovieBinding.inflate(layoutInflater)
    }

    var calendar = Calendar.getInstance()
    var year = calendar.get(Calendar.YEAR)
    var month = calendar.get(Calendar.MONTH)
    var day = calendar.get(Calendar.DAY_OF_MONTH)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(addMovieBinding.root)

        addMovieBinding.releaseCal.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                addMovieBinding.etAddReleaseDate.setText("$year-${month + 1}-$dayOfMonth")
            }, year, month, day)
            datePickerDialog.show()
        }

        addMovieBinding.watchCal.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                addMovieBinding.etAddWatchDate.setText("$year-${month + 1}-$dayOfMonth")
            }, year, month, day)
            datePickerDialog.show()
        }

        addMovieBinding.btnAddSave.setOnClickListener {
            val title = addMovieBinding.etAddTitle.text.toString()
            val rate = addMovieBinding.rbAddRate.rating
            val actor = addMovieBinding.etAddActor.text.toString()
            val director = addMovieBinding.etAddDirector.text.toString()
            val releaseDate = addMovieBinding.etAddReleaseDate.text.toString()
            val watchDate = addMovieBinding.etAddWatchDate.text.toString()

            if (title.isNotEmpty() && rate > 0 && watchDate.isNotEmpty()) {
                if (addMovie(MovieDTO(0, title, 0, rate, actor, director, releaseDate, watchDate)) > 0
                ) {
                    setResult(RESULT_OK)
                    Toast.makeText(this, "영화가 추가되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    setResult(RESULT_CANCELED)
                    Toast.makeText(this, "영화 추가에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
                finish()
            } else {
                Toast.makeText(this, "영화명, 평점, 관람 날짜는 필수 입력 항목입니다.", Toast.LENGTH_SHORT).show()
            }
        }
        addMovieBinding.btnAddCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            Toast.makeText(this, "추가가 취소되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun addMovie(newDTO: MovieDTO): Long {
        val helper = MovieDBHelper(this)
        val db = helper.writableDatabase

        val newValue = ContentValues().apply {
            put(MovieDBHelper.COL_TITLE, newDTO.title)
            //put(MovieDBHelper.COL_IMAGE, newDTO.image)
            put(MovieDBHelper.COL_IMAGE, R.mipmap.sample)
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