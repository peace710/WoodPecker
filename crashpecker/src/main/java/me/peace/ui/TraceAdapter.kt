package me.peace.ui

import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Space
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.peace.crashpecker.R
import me.peace.ui.TraceAdapter.CrashViewHolder
import me.peace.utils.StringStyleUtils
import java.util.*

class TraceAdapter(private val traces: ArrayList<String>?, private val keys: ArrayList<String>?) :
    RecyclerView.Adapter<CrashViewHolder>() {
    private var selectPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CrashViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.crash_info_item, parent,
            false
        )
        return CrashViewHolder(v)
    }

    override fun onBindViewHolder(crashViewHolder: CrashViewHolder, position: Int) {
        val trace = traces!![position].trim { it <= ' ' }
        controlSpaceState(crashViewHolder, trace.startsWith(TRACE_AT))
        controlTextState(crashViewHolder, trace.startsWith(TRACE_AT))
        crashViewHolder.trace.text = handleHighLightText(crashViewHolder, trace, position)
        controlTextSelected(crashViewHolder, position)
        controlCrashDescription(crashViewHolder, position)
    }

    override fun getItemCount(): Int {
        return traces?.size ?: 0
    }

    private fun controlSpaceState(holder: CrashViewHolder, isShow: Boolean) {
        if (isShow) {
            holder.space.visibility = View.VISIBLE
        } else {
            holder.space.visibility = View.GONE
        }
    }

    private fun controlTextState(holder: CrashViewHolder, isShow: Boolean) {
        val res = holder.trace.resources
        if (isShow) {
            holder.trace.setTypeface(null, Typeface.NORMAL)
            holder.trace.setTextColor(res.getColor(R.color.trace_text_color))
        } else {
            holder.trace.setTypeface(null, Typeface.BOLD)
            holder.trace.setTextColor(res.getColor(R.color.trace_text_bold_color))
        }
    }

    private fun handleHighLightText(
        holder: CrashViewHolder,
        trace: String,
        position: Int
    ): CharSequence {
        if (trace.startsWith(TRACE_AT)) {
            for (key in keys!!) {
                if (trace.contains(key)) {
                    val index = trace.indexOf("(")
                    if (index > 0) {
                        if (selectPosition == -1) {
                            selectPosition = position
                        }
                        return highLightText(holder, trace, index)
                    }
                    break
                }
            }
        }
        return trace
    }

    private fun highLightText(holder: CrashViewHolder, trace: String, index: Int): CharSequence {
        val start = trace.substring(0, index)
        val builder = SpannableStringBuilder(start)
            .append(
                StringStyleUtils.format(
                    holder.trace.context, trace.substring(index),
                    R.style.HighLightTextAppearance
                )
            )
        return builder.subSequence(0, builder.length)
    }

    private fun controlCrashDescription(holder: CrashViewHolder, position: Int) {
        if (0 == position) {
            val res = holder.trace.resources
            holder.trace.setTextColor(res.getColor(R.color.color_yellow))
        }
    }

    private fun controlTextSelected(holder: CrashViewHolder, position: Int) {
        holder.itemView.isSelected = selectPosition == position
    }

    class CrashViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val space: Space = itemView.findViewById<View>(R.id.space) as Space
        val trace: TextView = itemView.findViewById<View>(R.id.trace) as TextView

    }

    companion object {
        private val TAG = TraceAdapter::class.java.simpleName
        private const val TRACE_AT = "at "
    }
}