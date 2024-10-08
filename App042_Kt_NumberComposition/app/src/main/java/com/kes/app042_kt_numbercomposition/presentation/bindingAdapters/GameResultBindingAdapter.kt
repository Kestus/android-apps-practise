package com.kes.app042_kt_numbercomposition.presentation.bindingAdapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.kes.app042_kt_numbercomposition.R

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(view: TextView, count: Int) {
    view.apply {
        text = String.format(
            context.getString(R.string.required_answers),
            count
        )
    }
}

@BindingAdapter("scoreAnswers")
fun bindScoreAnswers(view: TextView, count: Int) {
    view.apply {
        text = String.format(
            context.getString(R.string.score_answers),
            count
        )
    }
}

@BindingAdapter("requiredPercentage")
fun bindRequiredPercentage(view: TextView, count: Int) {
    view.apply {
        text = String.format(
            context.getString(R.string.required_percentage),
            count
        )
    }
}

@BindingAdapter("scorePercentage")
fun bindScorePercentage(view: TextView, count: Int) {
    view.apply {
        text = String.format(
            context.getString(R.string.score_percentage),
            count
        )
    }
}

@BindingAdapter("resultImage")
fun bindResultImage(view: ImageView, winner: Boolean) {
    view.apply {
        setImageResource(
            if (winner) R.drawable.ic_smile
            else R.drawable.ic_frown
        )
    }
}