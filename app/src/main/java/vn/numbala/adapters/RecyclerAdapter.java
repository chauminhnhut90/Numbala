package vn.numbala.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import vn.numbala.adapters.viewholder.BaseViewHolder;

/**
 * Created by chauminhnhut on 5/12/16.
 */
public abstract class RecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public abstract BaseViewHolder onCreateViewHolderOutside(ViewGroup parent, int viewType);

    public abstract void onBindViewHolderOutside(BaseViewHolder holder, int position);

    public abstract int getItemCountOutside();


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHolderOutside(parent, viewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        onBindViewHolderOutside(holder, position);
    }

    @Override
    public int getItemCount() {
        return getItemCountOutside();
    }

}
