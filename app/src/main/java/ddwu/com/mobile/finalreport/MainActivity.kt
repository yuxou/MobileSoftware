package ddwu.com.mobile.finalreport

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.finalreport.data.MovieDAO
import ddwu.com.mobile.finalreport.data.MovieDTO
import ddwu.com.mobile.finalreport.databinding.ActivityMainBinding
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    val REQ_ADD = 100
    val REQ_UPDATE = 200

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var movies : ArrayList<MovieDTO>
    val movieDAO by lazy {
        MovieDAO(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        movies = movieDAO.getAllMovies()

        val adapter = MovieAdapter(movies)
        binding.recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, UpdateActivity::class.java)
                intent.putExtra("dto", movies[position])
                startActivityForResult(intent, REQ_UPDATE)
            }
        })

        adapter.setOnItemLongClickListener(object : MovieAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int): Boolean {
                showDeleteDialog(position)
                return true
            }
        })
    }

     private fun showDeleteDialog(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("${movies[position].title} 기록을 삭제하시겠습니까?")
            .setPositiveButton("삭제") { _, _ ->
                if (movieDAO.deleteMovie(movies[position]) > 0) {
                    movies.removeAt(position)
                    binding.recyclerView.adapter?.notifyDataSetChanged()
                    Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("취소") {dialog, _ -> dialog.cancel()}
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                startActivity(Intent(this, AddActivity::class.java))
                true
            }
            R.id.action_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            R.id.action_exit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}