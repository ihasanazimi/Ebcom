package com.example.ebcom.utility.extentions

import android.animation.*
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.InputType
import android.transition.Fade
import android.transition.TransitionManager
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.*
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.RecyclerView
import com.example.ebcom.R
import kotlin.math.ceil


fun View.show() { visibility = View.VISIBLE }

fun View.hide() { visibility = View.GONE }

fun View.invisible() { visibility = View.INVISIBLE }


fun ViewGroup.showParentByAnim() {
    val transition = Fade()
    transition.duration = 500
    transition.addTarget(this)
    TransitionManager.beginDelayedTransition(this, transition)
    this.visibility = View.VISIBLE
}

/** get resources */
fun View.getDrawable(drawableResID: Int): Drawable? =
    ContextCompat.getDrawable(context, drawableResID)?.mutate()

fun View.getColor(colorResID: Int): Int =
    ContextCompat.getColor(context, colorResID)




fun EditText.customRequestFocus() {
    requestFocus()
    val imm: InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun EditText.setReadOnly(value: Boolean, inputType: Int = InputType.TYPE_NULL) {
    isFocusable = !value
    isFocusableInTouchMode = !value
    this.inputType = inputType
}


fun RecyclerView.scrollToTop() {
    if(canScrollVertically(-1))
        smoothScrollToPosition(0)
}



fun View.getColoredDrawable(drawableResID: Int, colorResID: Int, mode: PorterDuff.Mode): Drawable? {
    val drawable = ContextCompat.getDrawable(context, drawableResID)?.mutate()
    val colorFilter = PorterDuffColorFilter(
        ContextCompat.getColor(
            context,
            colorResID
        ), mode
    )
    drawable?.colorFilter = colorFilter
    return drawable
}



fun View.shakeView(x: Float, num: Int, finish: Boolean = false) {

    if (finish) {
        translationX = 0f
    }
    val animatorSet = AnimatorSet()
    val valueAnimator = ObjectAnimator.ofFloat(
        this,
        "translationX",
        dp(x).toFloat()
    )
    animatorSet.playTogether(
        valueAnimator
    )
    valueAnimator.repeatCount = ValueAnimator.INFINITE
    animatorSet.duration = 50
    animatorSet.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            if (!finish)
                shakeView(if (num == 5) 0f else -x, num + 1)
        }
    })
    animatorSet.start()
}



fun View.getColoredDrawable(drawable: Drawable, colorResID: Int, mode: PorterDuff.Mode): Drawable? {
    val colorFilter = PorterDuffColorFilter(
        ContextCompat.getColor(
            context,
            colorResID
        ), mode
    )
    drawable.colorFilter = colorFilter
    return drawable
}



fun View.beHideIf(beInvisible: Boolean) = if (beInvisible) hide() else show()

fun View.beShowIf(beVisible: Boolean) = if (beVisible) show() else hide()

fun View.beGoneIf(beGone: Boolean) = beShowIf(!beGone)




fun View.setPaddingLeft(value: Int) {
    setPadding(value, paddingTop, paddingRight, paddingBottom)
}
fun View.setPaddingTop(value: Int) {
    setPadding(paddingLeft, value, paddingRight, paddingBottom)
}
fun View.setPaddingRight(value: Int) {
    setPadding(paddingLeft, paddingTop, value, paddingBottom)
}
fun View.setPaddingBottom(value: Int) {
    setPadding(paddingLeft, paddingTop, paddingRight, value)
}




fun View.showByFadeIn() {
    animate().alpha(1f).setDuration(150L).withStartAction { show() }.start()
}

fun View.hideFadeOut() {
    animate().alpha(0f).setDuration(150L).withEndAction { hide() }.start()
}





fun EditText.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as
            InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun EditText.showKeyboard() {
    this.requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}



/**
 * first imp this dependency on build.gradle :
 * implementation 'androidx.dynamicanimation:dynamicanimation:1.0.0'
 */
@SuppressLint("ClickableViewAccessibility")
fun View.implementSpringAnimationTrait(){
    val scaleXAnim = SpringAnimation(this,DynamicAnimation.SCALE_X,0.91f)
    val scaleYanim = SpringAnimation(this,DynamicAnimation.SCALE_Y,0.91f)

    setOnTouchListener { view, event ->

        when(event.action){
            MotionEvent.ACTION_DOWN ->{

                scaleXAnim.spring.stiffness = SpringForce.STIFFNESS_MEDIUM
                scaleXAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                scaleXAnim.start()

                scaleYanim.spring.dampingRatio = SpringForce.STIFFNESS_MEDIUM
                scaleYanim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                scaleYanim.start()
            }

            MotionEvent.ACTION_UP , MotionEvent.ACTION_CANCEL ->{
                scaleXAnim.cancel()
                scaleYanim.cancel()

                val reverseScaleXAnim = SpringAnimation(this,DynamicAnimation.SCALE_X,1f)
                reverseScaleXAnim.spring.stiffness = SpringForce.STIFFNESS_LOW
                reverseScaleXAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                reverseScaleXAnim.start()

                val reverseScaleYAnim = SpringAnimation(this,DynamicAnimation.SCALE_Y,1f)
                reverseScaleYAnim.spring.stiffness = SpringForce.STIFFNESS_LOW
                reverseScaleYAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                reverseScaleYAnim.start()
            }
        }
        false
    }
}



fun View.dp(value: Float): Int {
    return if (value == 0f) {
        0
    } else ceil(context.resources.displayMetrics.density * value.toDouble()).toInt()
}



fun convertDpToPixel(dp : Float , context : Context?) : Float {
    return if (context != null){
        val resource = context.resources
        val metrics = resource.displayMetrics
        dp / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }else{
        val metrics = Resources.getSystem().displayMetrics
        dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}




fun convertDpToPixel2(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)
}




fun setStatusBarTransparent(activity: Activity, view: View) {
    activity.apply {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(view) { root, windowInset ->
            val inset = windowInset.getInsets(WindowInsetsCompat.Type.systemBars())
            root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = inset.left
                bottomMargin = inset.bottom
                rightMargin = inset.right
            }
            WindowInsetsCompat.CONSUMED
        }
    }
}




fun hideSystemUI(window: Window) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    val controllerCompat = WindowInsetsControllerCompat(window, window.decorView)
    controllerCompat.hide(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.navigationBars())
    controllerCompat.systemBarsBehavior =
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
}


fun showSystemUI(window: Window) {
    val wic = WindowInsetsControllerCompat(window, window.decorView)
    wic.isAppearanceLightStatusBars = true
    // And then you can set any background color to the status bar.
    WindowCompat.setDecorFitsSystemWindows(window, true)
    WindowInsetsControllerCompat(window, window.decorView).show(WindowInsetsCompat.Type.systemBars())
}


fun addTurnScreenOnAlwaysFlag(window : Window){
    window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
}

fun clearTurnScreenOnAlwaysFlag(window : Window){
    window.clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
}


fun showToast(ctx : Context , message : String){
    Toast.makeText(ctx,message.trim() , Toast.LENGTH_LONG).show()
}


fun setLightStatusBar(view: View, activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags = view.systemUiVisibility
        flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        view.systemUiVisibility = flags
        activity.window.statusBarColor = Color.WHITE
    }
}


fun clearLightStatusBar(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val window = activity.window
        window.statusBarColor = ContextCompat
            .getColor(activity, R.color.white)
    }
}


/**
 * ================================================
 * Created by JessYan on 2020/4/8 18:43
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
open class AnimatorListenerImpl : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator) {}
    override fun onAnimationEnd(animation: Animator) {}
    override fun onAnimationCancel(animation: Animator) {}
    override fun onAnimationRepeat(animation: Animator) {}
}
