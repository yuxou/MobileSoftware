package ddwu.com.mobile.finalreport.data

import java.io.Serializable

data class MovieDTO(
    var id: Int,
    var title: String,
    var image: Int,
    var rate: Float,
    var actor: String?,
    var director: String?,
    var releaseDate: String?,
    var watchDate: String
) : Serializable {
    override fun toString() = "$id - $title (Rating: $rate, Watch Date: $watchDate)"
}