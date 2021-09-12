package com.hassanmohammed.movieapp.ui.custom

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.*
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import com.hassanmohammed.movieapp.R
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class CollectionPicker @SuppressLint("ResourceAsColor") constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyle: Int
) :
    LinearLayout(context, attrs, defStyle) {
    private val genresList: List<String> = listOf("#febf9b", "#f47f87", "#6ac68d", "#fbe0a5")
    private val mViewTreeObserver: ViewTreeObserver
    private val mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val simplifiedTags: Boolean
    private var mItems: MutableList<String> = ArrayList()
    private var mRow: LinearLayout? = null

    /**
     * Selected flags
     */
    var checkedItems: HashMap<String, Any> = HashMap()
    private var mClickListener: OnItemClickListener? = null
    private var mWidth = 0
    private var mItemMargin = 10
    private var textPaddingLeft = 5
    private var textPaddingRight = 5
    private var textPaddingTop = 5
    private var texPaddingBottom = 5
    private var mLayoutBackgroundColorNormal: Int = Color.BLUE
    private var mLayoutBackgroundColorPressed: Int = Color.RED
    private var mTextColor = R.color.white
    private var mRadius = 5
    private var mInitialized = false
    var isUseRandomColor: Boolean

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mWidth = w
    }

    private fun drawItemView() {
        if (!mInitialized) {
            return
        }
        clearUi()
        var totalPadding = (paddingLeft + paddingRight).toFloat()
        var indexFrontView = 0
        val itemParams = itemLayoutParams
        for (i in mItems.indices) {
            val item = mItems[i]
            val position = i
            val itemLayout: View = createItemView(item)
            val itemTextView: TextView = itemLayout.findViewById(R.id.item_text)
            itemTextView.isAllCaps = true
            itemTextView.textSize = 10f
            itemTextView.text = item
            itemTextView.setPadding(
                textPaddingLeft, textPaddingTop, textPaddingRight,
                texPaddingBottom
            )
            itemTextView.setTextColor(resources.getColor(mTextColor))
            var itemWidth = (itemTextView.paint.measureText(item) + textPaddingLeft
                    + textPaddingRight)
            itemWidth += (dpToPx(context, 20) + textPaddingLeft
                    + textPaddingRight).toFloat()
            if (mWidth <= itemWidth + totalPadding) {
                totalPadding = (paddingLeft + paddingRight).toFloat()
                indexFrontView = i
                addItemView(itemLayout, itemParams, true, i)
            } else {
                if (i != indexFrontView) {
                    itemParams.rightMargin = mItemMargin
                    totalPadding += mItemMargin.toFloat()
                }
                addItemView(itemLayout, itemParams, false, i)
            }
            totalPadding += itemWidth
        }
        // }
    }

    private fun createItemView(s: String): View {
        val view: View = mInflater.inflate(R.layout.list_item_genre, this, false)
        if (isJellyBeanAndAbove) {
            view.background = getSelector(s)
        } else {
            view.setBackgroundDrawable(getSelector(s))
        }
        return view
    }

    private val itemLayoutParams: LayoutParams
        get() {
            val itemParams = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            itemParams.bottomMargin = mItemMargin / 2
            itemParams.topMargin = 0
            itemParams.rightMargin = mItemMargin
            return itemParams
        }


    private fun clearUi() {
        removeAllViews()
        mRow = null
    }

    private fun addItemView(
        itemView: View, chipParams: ViewGroup.LayoutParams, newLine: Boolean,
        position: Int
    ) {
        if (mRow == null || newLine) {
            mRow = LinearLayout(context)
            mRow!!.gravity = Gravity.START
            mRow!!.orientation = HORIZONTAL
            val params = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            mRow!!.layoutParams = params
            addView(mRow)
        }
        mRow!!.addView(itemView, chipParams)
        animateItemView(itemView, position)
    }

    private fun getSelector(s: String): StateListDrawable {
        return selectorNormal
    }

    @get:SuppressLint("ResourceAsColor")
    private val selectorNormal: StateListDrawable
        get() {
            val states = StateListDrawable()
            var gradientDrawable = GradientDrawable()
            gradientDrawable.setColor(mLayoutBackgroundColorPressed)
            gradientDrawable.cornerRadius = mRadius.toFloat()
            gradientDrawable = GradientDrawable()
            val index: Int = Random().nextInt(genresList.size)
            if (isUseRandomColor) mLayoutBackgroundColorNormal = Color.parseColor(genresList[index])
            gradientDrawable.setColor(mLayoutBackgroundColorNormal)
            gradientDrawable.cornerRadius = mRadius.toFloat()
            states.addState(intArrayOf(), gradientDrawable)
            return states
        }

    fun setSelector(colorCode: Int) {
        mLayoutBackgroundColorNormal = colorCode
        selectorNormal
    }

    @get:SuppressLint("ResourceAsColor")
    private val selectorSelected: StateListDrawable
        private get() {
            val states = StateListDrawable()
            var gradientDrawable = GradientDrawable()
            gradientDrawable.setColor(mLayoutBackgroundColorNormal)
            gradientDrawable.cornerRadius = mRadius.toFloat()
            gradientDrawable = GradientDrawable()
            gradientDrawable.setColor(mLayoutBackgroundColorPressed)
            gradientDrawable.cornerRadius = mRadius.toFloat()
            states.addState(intArrayOf(), gradientDrawable)
            return states
        }
    val items: List<String>
        get() = mItems

    fun setItems(items: List<String>) {
        mItems = items.toMutableList()
        drawItemView()
    }

    fun clearItems() {
        mItems.clear()
    }

    fun setTextColor(color: Int) {
        mTextColor = color
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener?) {
        mClickListener = clickListener
    }

    private val isJellyBeanAndAbove: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN

    private fun animateView(view: View) {
        view.scaleY = 1f
        view.scaleX = 1f
        view.animate()
            .scaleX(1.2f)
            .scaleY(1.2f)
            .setDuration(100)
            .setStartDelay(0)
            .setInterpolator(DecelerateInterpolator())
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    reverseAnimation(view)
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }

            })
            .start()
    }

    private fun reverseAnimation(view: View) {
        view.scaleY = 1.2f
        view.scaleX = 1.2f
        view.animate()
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(100)
            .setListener(null)
            .start()
    }

    private fun animateItemView(view: View, position: Int) {
        var animationDelay: Long = 600
        animationDelay += (position * 30).toLong()
        view.scaleY = 0f
        view.scaleX = 0f
        view.animate()
            .scaleY(1f)
            .scaleX(1f)
            .setDuration(200)
            .setInterpolator(DecelerateInterpolator())
            .setListener(null)
            .setStartDelay(animationDelay)
            .start()
    }

    interface OnItemClickListener {
        fun onClick(s: String?, position: Int)
    }

    companion object {
        private fun dpToPx(context: Context, dp: Int): Int {
            val density: Float = context.resources.displayMetrics.density
            return Math.round(dp.toFloat() * density)
        }
    }

    init {
        val typeArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CollectionPicker)
        mItemMargin =
            typeArray.getDimension(
                R.styleable.CollectionPicker_cp_itemMargin, dpToPx(
                    context, mItemMargin
                ).toFloat()
            )
                .toInt()
        textPaddingLeft =
            typeArray.getDimension(
                R.styleable.CollectionPicker_cp_textPaddingLeft, dpToPx(
                    context, textPaddingLeft
                ).toFloat()
            )
                .toInt()
        textPaddingRight = typeArray.getDimension(
            R.styleable.CollectionPicker_cp_textPaddingRight, dpToPx(
                context, textPaddingRight
            ).toFloat()
        )
            .toInt()
        textPaddingTop =
            typeArray.getDimension(
                R.styleable.CollectionPicker_cp_textPaddingTop, dpToPx(
                    context, textPaddingTop
                ).toFloat()
            )
                .toInt()
        texPaddingBottom = typeArray.getDimension(
            R.styleable.CollectionPicker_cp_textPaddingBottom, dpToPx(
                context, texPaddingBottom
            ).toFloat()
        )
            .toInt()
        mLayoutBackgroundColorNormal = typeArray.getColor(
            R.styleable.CollectionPicker_cp_itemBackgroundNormal,
            mLayoutBackgroundColorNormal
        )
        mLayoutBackgroundColorPressed = typeArray.getColor(
            R.styleable.CollectionPicker_cp_itemBackgroundPressed,
            mLayoutBackgroundColorPressed
        )
        mRadius =
            typeArray.getDimension(R.styleable.CollectionPicker_cp_itemRadius, mRadius.toFloat())
                .toInt()
        mTextColor = typeArray.getColor(R.styleable.CollectionPicker_cp_itemTextColor, mTextColor)
        simplifiedTags = typeArray.getBoolean(R.styleable.CollectionPicker_cp_simplified, false)
        isUseRandomColor = typeArray.getBoolean(R.styleable.CollectionPicker_cp_randomColor, false)
        typeArray.recycle()
        orientation = HORIZONTAL
        gravity = Gravity.START
        mViewTreeObserver = viewTreeObserver
        mViewTreeObserver.addOnGlobalLayoutListener {
            if (!mInitialized) {
                mInitialized = true
                drawItemView()
            }
        }
    }
}