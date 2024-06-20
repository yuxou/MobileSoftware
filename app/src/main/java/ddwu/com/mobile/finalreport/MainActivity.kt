package ddwu.com.mobile.finalreport

/*
과제명: 영화 리뷰 앱
분반: 01분반
학번: 20220777
성명: 신유주
제출일: 2024년 6월
 */

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_UPDATE, REQ_ADD -> {
                if (resultCode == RESULT_OK) {
                    updateList()
                } else {
                    Toast.makeText(this@MainActivity, "취소되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateList() {
        movies.clear()
        movies.addAll(movieDAO.getAllMovies())
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank()) {
                    val filter = movieDAO.searchMovies(newText)
                    (binding.recyclerView.adapter as MovieAdapter).apply {
                        movies.clear()
                        movies.addAll(filter)
                        notifyDataSetChanged()
                    }
                } else {
                    updateList()
                }
                return true
            }
        })
        return super .onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_add -> {
                startActivityForResult(Intent(this, AddActivity::class.java), REQ_ADD)
                return true
            }
            R.id.action_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                return true
            }
            R.id.action_exit -> {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("앱 종료")
                    .setMessage("앱을 종료하시겠습니까?")
                    .setPositiveButton("종료") { dialog, _ ->
                        finish()
                    }
                    .setNegativeButton("취소") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}