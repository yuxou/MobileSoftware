package ddwu.com.mobile.finalreport

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobile.finalreport.data.MovieDTO
import ddwu.com.mobile.finalreport.databinding.ActivityAddMovieBinding
import ddwu.com.mobile.finalreport.databinding.ItemMovieBinding

class MovieAdapter (private val movies : ArrayList<MovieDTO>)
    : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private val TAG = "MovieAdapter"

    inner class MovieViewHolder(val itemBinding: ItemMovieBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movie: MovieDTO) {
            itemBinding.title.text = movie.title
            itemBinding.rating.text = movie.rate.toString()
            itemBinding.watchDate.text = movie.watchDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])

        holder.itemBinding.root.setOnClickListener {
            listener?.onItemClick(it, position)
        }

        holder.itemBinding.root.setOnLongClickListener {
            longClickListener?.onItemLongClick(it, position) ?: false
        }
    }

    override fun getItemCount(): Int = movies.size

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int) : Boolean
    }

    var longClickListener: OnItemLongClickListener? = null

    fun setOnItemLongClickListener (listener: OnItemLongClickListener?) {
        this.longClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(view : View, position : Int)
    }

    /*사용자 정의 외부 click 이벤트 리스너 설정 */
    var listener : OnItemClickListener? = null  // listener 를 사용하지 않을 때도 있으므로 null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

}