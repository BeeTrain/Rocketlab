package io.rocketlab.utils.provider.resources

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.ArrayRes
import androidx.annotation.BoolRes
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.annotation.IntegerRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.annotation.XmlRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import io.rocketlab.utils.provider.activity.ActivityContextProvider
import org.xmlpull.v1.XmlPullParser

private const val RESOURCE_TYPE_DRAWABLE = "drawable"

class ResourcesProvider(
    private val applicationContext: Context,
    private val activityContextProvider: ActivityContextProvider
) {

    private val context: Context
        get() = resolveContext()

    private fun resolveContext(): Context {
        return activityContextProvider.activityContext ?: applicationContext
    }

    fun getString(@StringRes resId: Int): String {
        return context.getString(resId)
    }

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }

    fun getQuantityString(@PluralsRes resId: Int, quantity: Int, vararg formatArgs: Any): String {
        return context.resources.getQuantityString(resId, quantity, *formatArgs)
    }

    fun getStringsArray(@ArrayRes arrayRes: Int): Array<String> {
        return context.resources.getStringArray(arrayRes)
    }

    fun getStringsList(resIds: List<Int>) = resIds.map(::getString)

    fun getIntArray(@ArrayRes resId: Int): IntArray {
        return context.resources.getIntArray(resId)
    }

    fun getColor(@ColorRes resId: Int): Int {
        return ContextCompat.getColor(context, resId)
    }

    fun getAttrColor(resId: Int): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(resId, typedValue, true)
        return typedValue.data
    }

    fun getDrawable(resId: Int): Drawable? {
        return ContextCompat.getDrawable(context, resId)
    }

    fun getDrawable(id: Int, tintColorAttrRes: Int): Drawable? {
        val drawable = getDrawable(id) ?: return null

        val tintColor = getAttrColor(tintColorAttrRes)
        val colorFilter = PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        drawable.colorFilter = colorFilter

        return drawable
    }

    fun getDrawableByResourceName(drawableName: String): Drawable? {
        val drawableId = getDrawableResByIdResourceName(drawableName)
        return drawableId?.let { getDrawable(it) }
    }

    fun getDrawableResByIdResourceName(drawableName: String): Int? {
        val drawableId = context.resources.getIdentifier(drawableName, RESOURCE_TYPE_DRAWABLE, context.packageName)
        return drawableId.takeIf { it != 0 }
    }

    fun getDimension(resId: Int): Float {
        return context.resources.getDimension(resId)
    }

    fun getInteger(@IntegerRes resId: Int): Int {
        return context.resources.getInteger(resId)
    }

    fun getXml(@XmlRes resId: Int): XmlPullParser {
        return context.resources.getXml(resId)
    }

    fun getFont(@FontRes resId: Int): Typeface {
        return ResourcesCompat.getFont(context, resId) ?: Typeface.DEFAULT
    }

    fun getBoolean(@BoolRes resId: Int): Boolean {
        return context.resources.getBoolean(resId)
    }
}