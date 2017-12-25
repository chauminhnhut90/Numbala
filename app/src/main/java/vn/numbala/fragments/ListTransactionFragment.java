package vn.numbala.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import vn.numbala.R;
import vn.numbala.activities.DetailActivity;
import vn.numbala.adapters.RecyclerAdapter;
import vn.numbala.adapters.viewholder.BaseViewHolder;
import vn.numbala.adapters.viewholder.TransactionViewHolder;
import vn.numbala.models.TransactionModel;
import vn.numbala.models.resObj.ListTransactionResObj;
import vn.numbala.models.resObj.SVResObj;
import vn.numbala.utils.AppApplication;
import vn.numbala.utils.ConfigUtils;
import vn.numbala.utils.Utils;

/**
 * Create By: Nhut Chau(nchau@kolabs.co)
 * Time: 12/2/17 2:09 PM.
 * Project Name: Numbala
 */

public class ListTransactionFragment extends BaseFragment {

    private TextView tvSum, tvFee;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerAdapter adapter;
    private ArrayList<TransactionModel> data;
    private int index = -1;
    private long total = 0;
    private long totalFee = 0;

    private int pag = 0;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    private int day = 0, month = 0, year = 0;
    private String search = "";
    private boolean flagReload = false;

    public void setIndex(int index) {
        this.index = index;
    }

    public void setFilterInfo(int day, int month, int year, String search) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.search = search;
    }

    public void setFlagReload() {
        this.flagReload = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_list_transaction_fragment, container, false);
        setUpView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.loadData();
    }

    public void loadData() {
        if (data == null) {
            data = new ArrayList<>();
            initAdapter();
        }

        recyclerView.setAdapter(adapter);

        if (data.size() == 0 || this.flagReload) {
            this.reset();
            this.getListTransaction(getTypeByIndex(this.index));
        } else {
            updateUI();
        }
    }

    private void getListTransaction(int type) {

        if (data.size() == 0)
            Utils.showProgressDialog(getContext());

        String key = AppApplication.getInstance().key;
        int typ = type;

        int d = this.day;
        int m = this.month;
        int y = this.year;
        String sea = this.search;
        String imei = AppApplication.getInstance().imei;

        String url = String.format("%s?key=%s&typ=%d&pag=%d&d=%d&m=%d&y=%d&sea=%s&imei", ConfigUtils.DOMAIN_HTTP_API, key, typ, ++pag, d, m, y, sea, imei);
        Utils.logInfo(url);
        try {

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    loading = true;
                    Utils.closeProgressDialog();
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = response.body().string().replace("(", "").replace(")", "");
                        SVResObj resObj = new Gson().fromJson(result, SVResObj.class);
                        if (resObj != null && resObj.status) {
                            ListTransactionResObj listTransactionResObj = new Gson().fromJson(result, ListTransactionResObj.class);

                            for (TransactionModel model : listTransactionResObj.data) {
                                total += Long.parseLong(model.Price);
                                totalFee += (Long.parseLong(model.Price) * Long.parseLong(model.Fee)) / 100;
                                data.add(model);
                            }

                            data.addAll(listTransactionResObj.data);

                            updateUI();
                        } else {
                            Utils.showToast(getContext(), resObj.message);
                        }
                    } else {
                        Utils.showToast(getContext(), "Something wrong...!!!");
                    }

                    loading = true;
                    Utils.closeProgressDialog();
                }
            });
        } catch (Exception ex) {
            loading = true;
            Utils.closeProgressDialog();
            ex.printStackTrace();
        }
    }

    private void setUpView(View view) {
        tvSum = view.findViewById(R.id.sum);
        tvFee = view.findViewById(R.id.fee);

        mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

        addDivider();
        addOnScrollListener();
    }

    private void initAdapter() {

        adapter = new RecyclerAdapter() {

            @Override
            public BaseViewHolder onCreateViewHolderOutside(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_row_transaction, parent, false);
                return new TransactionViewHolder(itemView);
            }

            @Override
            public void onBindViewHolderOutside(BaseViewHolder holder, final int position) {
                TransactionViewHolder viewHolder = (TransactionViewHolder) holder;
                final TransactionModel model = data.get(position);

                viewHolder.stt.setText(String.valueOf(position + 1));
                viewHolder.web.setText(model.Web);
                viewHolder.id.setText("ID: " + model.ID.replace("ID", ""));
                viewHolder.date.setText(model.Date_created);

                long price = Long.parseLong(model.Price);
                viewHolder.money.setText(Utils.format(price) + " đ");

                int fee = Integer.parseInt(model.Fee);
                viewHolder.fee.setText(getString(R.string.fee) + " " + Utils.format(price * fee / 100) + " đ");

                if (position % 2 == 0) {
                    viewHolder.row.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                } else {
                    viewHolder.row.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.bg_trans));
                }

                if (index == 1) {
                    viewHolder.imvStatus.setImageResource(R.drawable.ic_success_tab);

                } else if (index == 2) {
                    viewHolder.imvStatus.setImageResource(R.drawable.ic_wait_tab);

                } else if (index == 3) {
                    viewHolder.imvStatus.setImageResource(R.drawable.ic_cancle_tab);

                } else {

                    if (model.Status_num.equals("1")) {
                        viewHolder.imvStatus.setImageResource(R.drawable.ic_wait_tab);

                    } else if (model.Status_num.equals("2")) {
                        viewHolder.imvStatus.setImageResource(R.drawable.ic_success_tab);

                    } else {
                        viewHolder.imvStatus.setImageResource(R.drawable.ic_cancle_tab);
                    }
                }

                viewHolder.row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), DetailActivity.class);
                        intent.putExtra(DetailActivity.KEY_MODEL, data.get(position));
                        startActivity(intent);

                    }
                });
            }

            @Override
            public int getItemCountOutside() {
                if (data != null)
                    return data.size();
                return 0;
            }
        };
    }

    private void addDivider() {
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void addOnScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 5) {
                            loading = false;
                            getListTransaction(getTypeByIndex(index));
                        }
                    }
                }
            }
        });
    }

    private void updateUI() {
        ((Activity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvSum.setText(Utils.format(total) + " đ");
                tvFee.setText(getString(R.string.fee) + " " + Utils.format(totalFee) + " đ");
                adapter.notifyDataSetChanged();
            }
        });
    }

    private int getTypeByIndex(int index) {
        int type = -1;
        switch (index) {
            case 0:
                type = 2;
                break;

            case 1:
                type = 4;
                break;

            case 2:
                type = 3;
                break;

            case 3:
                type = 5;
                break;
        }

        return type;
    }

    public void reset() {
        data.clear();
        total = 0;
        totalFee = 0;
        pag = 0;
        loading = true;
        flagReload = false;
    }
}
