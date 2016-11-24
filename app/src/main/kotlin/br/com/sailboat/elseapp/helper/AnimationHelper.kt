package br.com.sailboat.elseapp.helper

import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.View

object AnimationHelper {

    fun performScaleUpAnimation(view: View) {
        view.animate().scaleX(1f).scaleY(1f).interpolator = FastOutSlowInInterpolator()
    }

    fun performScaleDownAnimation(view: View) {
        view.animate().scaleX(0f).scaleY(0f).interpolator = FastOutSlowInInterpolator()
    }

}
