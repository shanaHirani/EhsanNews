package com.jetbrains.handson.mpp.ehsan

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jetbrains.handson.mpp.ehsan.shared.NetworkResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.netFormat():String{
    return this.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
}

fun LocalDate.uiFormat():String{
    return this.format(DateTimeFormatter.ofPattern("dd MMM uuuu"))
}

fun View.snackBar( fragment:Fragment, massage:String, action:(()->Unit)?=null){
   
    val snackBar:Snackbar
    if(action == null){
         snackBar = Snackbar.make(this,massage, Snackbar.LENGTH_LONG)
        snackBar.show()
    }else {
        snackBar = Snackbar.make(this,massage, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction("Retry"){
            if(!fragment.isDetached){
            action()}
            snackBar.dismiss()
        }
        snackBar.show()
    }
}

fun Fragment.handleApiError(
    failure: NetworkResponse.Failure,
    retry:(()->Unit)?=null
) {
    when {
        failure.isNetWorkError -> requireView().snackBar(
            this,
            "please check your internet connection",
            retry
        )
        else -> {
            val error = failure.errorResponseBody?.string().toString()
            requireView().snackBar(this,error)
        }
    }

}