package ddwu.com.mobile.finalreport

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import ddwu.com.mobile.finalreport.data.MovieDBHelper
import ddwu.com.mobile.finalreport.data.MovieDTO
import ddwu.com.mobile.finalreport.data.MovieDAO
import ddwu.com.mobile.finalreport.databinding.ActivityUpdateMovieBinding

class UpdateActivity : AppCompatActivity() {

    val updateBinding by lazy {
        ActivityUpdateMovieBinding.inflate(layoutInflater)
    }

    var calendar = Calendar.getInstance()
    var year = calendar.get(Calendar.YEAR)
    var month = calendar.get(Calendar.MONTH)
    var day = calendar.get(Calendar.DAY_OF_MONTH)

    @SuppressLint("SetTextI18n")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(updateBinding.root)

        updateBinding.releaseCal.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                updateBinding.etUpdateReleaseDate.setText("$year-${month + 1}-$dayOfMonth")
            }, year, month, day)
            datePickerDialog.show()
        }

        updateBinding.watchCal.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                updateBinding.etUpdateWatchDate.setText("$year-${month + 1}-$dayOfMonth")
            }, year, month, day)
            datePickerDialog.show()
        }

        val dto = intent.getSerializableExtra("dto") as MovieDTO

        updateBinding.etUpdateTitle.setText(dto.title)
        updateBinding.rbUpdateRate.rating = dto.rate
        updateBinding.etUpdateActor.setText(dto.actor)
        updateBinding.etUpdateDirector.setText(dto.director)
        updateBinding.etUpdateReleaseDate.setText(dto.releaseDate)
        updateBinding.etUpdateWatchDate.setText(dto.watchDate)

        updateBinding.btnUpdateSave.setOnClickListener {
            dto.title = updateBinding.etUpdateTitle.text.toString()
            dto.rate = updateBinding.rbUpdateRate.rating
            dto.actor = updateBinding.etUpdateActor.text.toString()
            dto.director = updateBinding.etUpdateDirector.text.toString()
            dto.releaseDate = updateBinding.etUpdateReleaseDate.text.toString()
            dto.watchDate = updateBinding.etUpdateWatchDate.text.toString()

            if (updateMovie(dto) > 0) {
                setResult(RESULT_OK)
            } else {
                setResult(RESULT_CANCELED)
            }

            finish()
        }

        updateBinding.btnUpdateCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    fun updateMovie(dto: MovieDTO): Int {
        val helper = MovieDBHelper(this)
        val db = helper.writableDatabase

        val updateValue = ContentValues().apply {
            put(MovieDBHelper.COL_TITLE, dto.title)
            put(MovieDBHelper.COL_IMAGE, dto.image)
            put(MovieDBHelper.COL_RATE, dto.rate)
            put(MovieDBHelper.COL_ACTOR, dto.actor)
            put(MovieDBHelper.COL_DIRECTOR, dto.director)
            put(MovieDBHelper.COL_RELEASE_DATE, dto.releaseDate)
            put(MovieDBHelper.COL_WATCH_DATE, dto.watchDate)
        }

        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(dto.id.toString())

        val resultCount = db.update(MovieDBHelper.TABLE_NAME, updateValue, whereClause, whereArgs)

        helper.close()
        return resultCount
    }
}