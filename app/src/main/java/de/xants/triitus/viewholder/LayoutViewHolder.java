package de.xants.triitus.viewholder;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by Don on 10.10.2015.
 */
public abstract class LayoutViewHolder extends RecyclerView.ViewHolder {
    public LayoutViewHolder(@NonNull ViewGroup viewGroup, @LayoutRes int layoutRes) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(layoutRes, viewGroup, false));
    }
}
