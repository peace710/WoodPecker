package me.peace.ui;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;
import android.widget.TextView;

import java.util.ArrayList;

import me.peace.crashpecker.R;
import me.peace.utils.StringStyleUtils;

public class TraceAdapter extends RecyclerView.Adapter<TraceAdapter.CrashViewHolder> {
    private static final String TAG = TraceAdapter.class.getSimpleName();
    private static final String TRACE_AT = "at ";
    private ArrayList<String> traces;
    private ArrayList<String> keys;
    private int selectPosition = -1;

    public TraceAdapter(ArrayList<String> traces,ArrayList<String> keys) {
        this.traces = traces;
        this.keys = keys;
    }

    @NonNull
    @Override
    public CrashViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.crash_info_item,parent,
            false);
        return new CrashViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CrashViewHolder crashViewHolder, int position) {
        String trace = traces.get(position).trim();
        controlSpaceState(crashViewHolder,trace.startsWith(TRACE_AT));
        controlTextState(crashViewHolder,trace.startsWith(TRACE_AT));
        crashViewHolder.trace.setText(handleHighLightText(crashViewHolder,trace,position));
        controlTextSelected(crashViewHolder,position);
        controlCrashDescription(crashViewHolder,position);
    }

    @Override
    public int getItemCount() {
        return traces == null ? 0 : traces.size();
    }

    private void controlSpaceState(CrashViewHolder holder,boolean isShow){
        if (isShow){
            holder.space.setVisibility(View.VISIBLE);
        }else{
            holder.space.setVisibility(View.GONE);
        }
    }

    private void controlTextState(CrashViewHolder holder,boolean isShow){
        Resources res = holder.trace.getResources();
        if (isShow){
            holder.trace.setTypeface(null, Typeface.NORMAL);
            holder.trace.setTextColor(res.getColor(R.color.trace_text_color));
        }else{
            holder.trace.setTypeface(null,Typeface.BOLD);
            holder.trace.setTextColor(res.getColor(R.color.trace_text_bold_color));
        }
    }

    private CharSequence handleHighLightText(CrashViewHolder holder,String trace,int position){
        if (trace.startsWith(TRACE_AT)){
            for (String key : keys){
                if (trace.contains(key)){
                    int index = trace.indexOf("(");
                    if (index > 0){
                        if (selectPosition == -1){
                            selectPosition = position;
                        }
                        return highLightText(holder,trace,index);
                    }
                    break;
                }
            }
        }
        return trace;
    }

    private CharSequence highLightText(CrashViewHolder holder,String trace,int index){
        String start = trace.substring(0,index);
        SpannableStringBuilder builder = new SpannableStringBuilder(start)
            .append(StringStyleUtils.format(holder.trace.getContext(),trace.substring(index),
                R.style.HighLightTextAppearance));
        return builder.subSequence(0,builder.length());
    }

    private void controlCrashDescription(CrashViewHolder holder,int position){
        if (0 == position){
            Resources res = holder.trace.getResources();
            holder.trace.setTextColor(res.getColor(R.color.color_yellow));
        }
    }

    private void controlTextSelected(CrashViewHolder holder,int position){
        if (selectPosition == position){
            holder.itemView.setSelected(true);
        }else{
            holder.itemView.setSelected(false);
        }
    }

    static class CrashViewHolder extends RecyclerView.ViewHolder{
        private Space space;
        private TextView trace;

        public CrashViewHolder(@NonNull View itemView) {
            super(itemView);
            space = (Space)itemView.findViewById(R.id.space);
            trace = (TextView)itemView.findViewById(R.id.trace);
        }
    }
}
