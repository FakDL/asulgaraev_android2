package com.example.mobilecourse2.ui.main.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilecourse2.DownloadImageHelper
import com.example.mobilecourse2.R
import com.example.mobilecourse2.ui.main.recyclerview.FilmHolder.Companion.characterDiff
import com.example.mobilecourse2.model.Film
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_film.*


class FilmAdapter(
    private val clickLambda: (String) -> Unit,
    private val downloadImageHelper: DownloadImageHelper
): PagingDataAdapter<Film, FilmHolder>(characterDiff){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmHolder =
        FilmHolder.create(parent, clickLambda, downloadImageHelper)


    override fun onBindViewHolder(holder: FilmHolder, position: Int) {
        val film = getItem(position)
        if (film != null) {
            holder.bind(film)
        }
    }
    }

class FilmHolder(
    override val containerView: View,
    private val clickLambda: (String) -> Unit,
    private val downloadImageHelper: DownloadImageHelper
): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(film: Film) {
        downloadImageHelper.setImage(iv_poster, film.img_url)
        tv_title.text = film.title
        itemView.setOnClickListener {
            Log.d("Test", film.id.substring(7,16) )
            clickLambda(film.id.substring(7,16))
        }
    }

    companion object {
        fun create(parent: ViewGroup, clickLambda: (String) -> Unit, downloadImageHelper: DownloadImageHelper): FilmHolder = FilmHolder(
            LayoutInflater.from(parent.context).
            inflate(R.layout.item_film, parent, false), clickLambda, downloadImageHelper)
        val characterDiff = object: DiffUtil.ItemCallback<Film>() {
            override fun areItemsTheSame(old: Film, new: Film): Boolean {
                return old.id == new.id

            }

            override fun areContentsTheSame(old: Film, new: Film): Boolean {
                return old == new
            }

        }
    }
}
