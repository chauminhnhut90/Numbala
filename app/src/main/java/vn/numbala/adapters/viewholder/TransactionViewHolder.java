package vn.numbala.adapters.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vn.numbala.R;

/**
 * Create By: Nhut Chau(nchau@kolabs.co)
 * Time: 12/2/17 2:38 PM.
 * Project Name: Numbala
 */

public class TransactionViewHolder extends BaseViewHolder {

    public View row;
    public TextView stt, web, id, date, money, fee;
    public ImageView imvStatus;


    public TransactionViewHolder(View view) {
        super(view);

        row = view.findViewById(R.id.row);
        stt = view.findViewById(R.id.stt);
        web = view.findViewById(R.id.web);
        id = view.findViewById(R.id.id);
        date = view.findViewById(R.id.date);
        money = view.findViewById(R.id.money);
        fee = view.findViewById(R.id.fee);

        imvStatus = view.findViewById(R.id.imvStatus);
    }
}