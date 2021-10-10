package com.jetbrains.handson.mpp.ehsan

import android.graphics.Typeface
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jetbrains.handson.mpp.ehsan.data.model.News
import com.jetbrains.handson.mpp.ehsan.ui.homePage.ApiStatus
import com.jetbrains.handson.mpp.ehsan.ui.homePage.NewsListAdapter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

@BindingAdapter("imgeUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl?.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("min_temp")
fun minTempToSpotsString(textView: TextView, minTemp: Double) {
    var spannableString = SpannableString(textView.resources.getString(R.string.minTemp,
        String.format("%.1f", minTemp)))
    val styleSpan = StyleSpan(Typeface.BOLD)
    spannableString.setSpan(styleSpan, 0, 4, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    textView.text = spannableString
}

@BindingAdapter("max_temp")
fun maxTempSpotsString(textView: TextView, maxTemp: Double) {
    var spannableString = SpannableString(textView.resources.getString(R.string.maxTemp,
        String.format("%.1f", maxTemp)))
    val styleSpan = StyleSpan(Typeface.BOLD)
    spannableString.setSpan(styleSpan, 0, 4, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    textView.text = spannableString
}

@BindingAdapter("feels_like_temp")
fun feelsLikeTempSpotsString(textView: TextView, feelsLikeTemp: Double) {
    var spannableString = SpannableString(textView.resources.getString(
        R.string.feelsLikeTemp,
        String.format("%.1f", feelsLikeTemp)))
    val styleSpan = StyleSpan(Typeface.BOLD)
    spannableString.setSpan(styleSpan, 0, 4, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    textView.text = spannableString
}

@BindingAdapter("weather")
fun setAvailableSpotsString(textView: TextView, weather: String?) {
    var spannableString = SpannableString(textView.resources.getString(R.string.weather, weather))
    val styleSpan = StyleSpan(Typeface.BOLD)
    spannableString.setSpan(styleSpan, 0, 8, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    textView.text = spannableString
}

@BindingAdapter("ApiStatus")
fun bindStatus(statusImageView: ImageView, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("FormatDate")
fun formatDate(textView: TextView, rawDate: String) {


    val dateRawFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

      try{
        textView.text = LocalDate.parse(rawDate, dateRawFormat)
            .format(DateTimeFormatter.ofPattern("dd MMM uuuu"))
      }catch (e:Exception){}
}

val datePattern = Pattern.compile(
    "[0-9]{4,4}" + "-" + "[0-9]{2,2}" + "-" + "[0-9]{2,2}" +
            "T" +
            "[0-9]{2,2}" + ":" + "[0-9]{2,2}" + ":" + "[0-9]{2,2}" +
            "Z"
)

fun isValidDate(str: String): Boolean {
    return datePattern.matcher(str).matches()
}
