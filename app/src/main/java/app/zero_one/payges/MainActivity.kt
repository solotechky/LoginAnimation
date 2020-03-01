package app.zero_one.payges

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.app.ActivityOptionsCompat
import app.zero_one.payges.page.RegisterPage
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupSplashScreen()
        clickListener()
    }

    private fun clickListener() {
        btnRegister.setOnClickListener { openPage() }
    }

    private fun openPage() {
        val mainAnim = androidx.core.util.Pair.create<View, String>(main, "mainAnim")
        val introLayoutAnim = androidx.core.util.Pair.create<View, String>(introLayout, "introLayoutAnim")
        val cardRegisterAnim = androidx.core.util.Pair.create<View, String>(cardRegister, "cardRegisterAnim")

        val pairs = ArrayList<androidx.core.util.Pair<View, String>>()
        pairs.add(mainAnim)
        pairs.add(introLayoutAnim)
        pairs.add(cardRegisterAnim)

        val pairArray: Array<androidx.core.util.Pair<View, String>> = pairs.toTypedArray()
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, *pairArray)
        startActivity(Intent(this, RegisterPage::class.java), options.toBundle())

    }

    private fun setupSplashScreen() {
        val slideDown = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_down)
        val slideUp = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_up)

        waveTop.startAnimation(slideDown)
        waveDown.startAnimation(slideUp)

        slideDown.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
                //dv
            }

            override fun onAnimationEnd(animation: Animation?) {
                val scale = ValueAnimator.ofFloat(0f, 1f)
                scale.addUpdateListener {
                    imgBook.alpha = it.animatedValue as Float
                    imgBook.scaleX = it.animatedValue as Float
                    imgBook.scaleY = it.animatedValue as Float
                }
                scale.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                        //
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        TransitionManager.beginDelayedTransition(introLayout)
                        tvHead.visibility = View.VISIBLE
                        tvCaption.visibility = View.VISIBLE
                        showLoginScreen()
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                        //
                    }

                    override fun onAnimationStart(animation: Animator?) {
                        //
                    }

                })
                scale.start()
            }

            override fun onAnimationStart(animation: Animation?) {
                //slv
            }
        })


    }

    private fun showLoginScreen() {
        Handler().postDelayed(
            {
                val transition = ChangeBounds()
                transition.interpolator = OvershootInterpolator()
                transition.duration = 1000L

                TransitionManager.beginDelayedTransition(main, transition)

                val constraintSet = ConstraintSet()
                constraintSet.clone(main)

                constraintSet.connect(waveDown.id, ConstraintSet.TOP, main.id, ConstraintSet.BOTTOM)

                constraintSet.clear(waveDown.id, ConstraintSet.BOTTOM)
                constraintSet.clear(introLayout.id, ConstraintSet.BOTTOM)


                constraintSet.connect(form.id,ConstraintSet.TOP,introLayout.id,ConstraintSet.BOTTOM)
                constraintSet.connect(cardRegister.id,ConstraintSet.TOP,form.id,ConstraintSet.BOTTOM)

                constraintSet.applyTo(main)

                imgBook.visibility = View.INVISIBLE
                introLayout.gravity = Gravity.NO_GRAVITY

                tvHead.setPadding(48, 60, 48, 0)
                tvHead.text = getString(R.string.welcome)
                tvCaption.text = getString(R.string.login_cap)


            },
            1200
        )
    }


}
