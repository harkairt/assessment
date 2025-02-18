package com.chain.githubissues.presentation.common

import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chain.githubissues.R
import com.chain.githubissues.domain.entity.IssueState
import com.chain.githubissues.domain.entity.Label
import com.chain.githubissues.util.dp
import com.chain.githubissues.util.getResourceColor
import com.chain.githubissues.util.setRightMargin
import com.chain.githubissues.util.wrappingParams
import com.squareup.picasso.Picasso
import io.noties.markwon.Markwon


@BindingAdapter("data")
fun <T> setData(recyclerView: RecyclerView, data: T?) {
    if (data == null) {
        Log.e("____", "recyclerView data binding: data is null!")
        return
    }

    if (recyclerView.adapter is BindableAdapter<*>) {
        (recyclerView.adapter as BindableAdapter<T>).bindData(data)
    }
}

@BindingAdapter("imagePath")
fun loadImage(imageView: ImageView, nullableImagePath: String?) {
    nullableImagePath?.let { imagePath ->
        Picasso.get()
            .load(imagePath)
            .fit().centerInside()
            .into(imageView)
    }
}

@BindingAdapter("issueState")
fun setIssueStateIcon(imageView: ImageView, issueState: IssueState?) {
    issueState?.let {
        imageView.setImageResource(
            when (issueState) {
                IssueState.open -> com.chain.githubissues.R.drawable.open_issue
                IssueState.closed -> com.chain.githubissues.R.drawable.closed_issue
            }
        )
    }
}

@BindingAdapter("inProgress")
fun setInProgress(progressBar: ProgressBar, inProgress: Boolean) {
    progressBar.visibility = if (inProgress) View.VISIBLE else View.INVISIBLE

}


@BindingAdapter("markdown")
fun setMarkdownText(textView: TextView, markdownString: String?) {
    markdownString?.let {
        Markwon.create(textView.context).setMarkdown(textView, markdownString)
    }
}


@BindingAdapter("hideWhenNull")
fun <T> hideWhenNull(view: View, data: T?) {
    view.visibility = if (data == null) View.INVISIBLE else {
        val startAnimation =
        AnimationUtils.loadAnimation(view.context, R.anim.fade_in)
        view.startAnimation(startAnimation)
        View.VISIBLE
    }
}


@BindingAdapter("labels")
fun addLabels(viewGroup: ViewGroup, labelList: List<Label>?) {
    viewGroup.removeAllViews()

    if (labelList == null)
        return

    if (labelList.isEmpty()) {
        viewGroup.visibility = View.GONE
        return
    } else
        viewGroup.visibility = View.VISIBLE

    labelList.forEach {
        viewGroup.addView(TextView(viewGroup.context, null, 0, com.chain.githubissues.R.style.labelTextStyle).apply {
            layoutParams = wrappingParams().apply {
                setRightMargin(4.dp)
            }
            text = it.name
            setBackgroundColor(
                when {
                    it.color != null -> Color.parseColor("#${it.color.toUpperCase()}")
                    else -> getResourceColor(com.chain.githubissues.R.color.primaryDarkText)
                }
            )
        })
    }


}
