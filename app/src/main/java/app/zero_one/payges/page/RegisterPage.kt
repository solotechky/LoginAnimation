package app.zero_one.payges.page

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import app.zero_one.payges.R
import kotlinx.android.synthetic.main.activity_register_page.*


class RegisterPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)
        anim()
        clickListener()
    }

    private fun anim() {

        val anim = ValueAnimator.ofFloat(0f, 1f)
        anim.addUpdateListener {
            linearLayout.alpha = it.animatedValue as Float
            linearLayout.scaleX = it.animatedValue as Float
            linearLayout.scaleY = it.animatedValue as Float
            imgDP.alpha = it.animatedValue as Float
            imgDP.scaleX = it.animatedValue as Float
            imgDP.scaleY = it.animatedValue as Float

        }
        anim.interpolator = OvershootInterpolator()
        anim.startDelay = 200L
        anim.duration = 500L
        anim.start()
    }

    private fun clickListener() {
        btnCreateAccount.setOnClickListener { submitForm() }
        btnGoBack.setOnClickListener { onBackPressed() }
    }

    private fun submitForm() {
        applySubmitAnim(true, 0f, 0.8f)
    }

    private fun applySubmitAnim(showLoading: Boolean, alpha: Float, scale: Float) {

        val duration = 400L

        val transition = ChangeBounds()
        transition.duration = duration
        transition.interpolator=OvershootInterpolator()

        TransitionManager.beginDelayedTransition(root, transition)

        val set = ConstraintSet()
        set.clone(root)

        if (showLoading) {
            set.connect(imgDP.id, ConstraintSet.BOTTOM, root.id, ConstraintSet.BOTTOM)
            set.connect(imgDP.id, ConstraintSet.TOP, root.id, ConstraintSet.TOP)
        } else {
            set.connect(imgDP.id, ConstraintSet.BOTTOM, linearLayout.id, ConstraintSet.TOP)
            set.clear(imgDP.id, ConstraintSet.TOP)
        }

        set.applyTo(root)

        linearLayout.animate().alpha(alpha).duration = duration
        linearLayout.animate().scaleX(scale).duration = duration
        linearLayout.animate().scaleY(scale).duration = duration

        btn.animate().alpha(alpha).duration = duration
        btn.animate().scaleX(scale).duration = duration
        btn.animate().scaleY(scale).duration = duration

        imgDP.animate().scaleX(scale).duration = duration
        imgDP.animate().scaleY(scale).duration = duration

        loadingAnim.animate().alpha(1 - alpha).duration = duration
        tvLoad.animate().alpha(1 - alpha).duration = duration

        linearLayout.visibility = if (showLoading) View.GONE else View.VISIBLE
        btn.visibility = if (showLoading) View.GONE else View.VISIBLE

        Handler().postDelayed({
            startActivity(Intent(this,DashboardPage::class.java))
            overridePendingTransition(0,0)
            finishAffinity()
        },2000)

    }



    override fun onBackPressed() {
        if (btn.visibility == View.VISIBLE) {
            overridePendingTransition(0, 0)
            finish()
        } else
            applySubmitAnim(false, 1f, 1f)
    }

}
