package com.jiyun.everywheretrip.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.bean.main.BanMiCirCuitBean;
import com.jiyun.everywheretrip.utils.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by $sl on 2019/5/13 9:51.
 */
public class MyBanMiCircuitAdapter extends RecyclerView.Adapter<MyBanMiCircuitAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<BanMiCirCuitBean.ResultBean.RoutesBean> mList;

    public MyBanMiCircuitAdapter(Context context, ArrayList<BanMiCirCuitBean.ResultBean.RoutesBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_banmidetails_circuit, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        BanMiCirCuitBean.ResultBean.RoutesBean routesBean = mList.get(i);
        viewHolder.mTvCity.setText(routesBean.getCity());
        viewHolder.mTvIntro.setText(routesBean.getIntro());
        viewHolder.mTvPrice.setText(routesBean.getPrice());
        viewHolder.mTvPriceInCents.setText(routesBean.getPriceInCents()+"人购买");
        viewHolder.mTvTitle.setText(routesBean.getTitle());
        ImageLoader.setImage(mContext, routesBean.getCardURL(), viewHolder.mIvCardURL, R.drawable.zhanweitu_home_kapian_xxxhdpi);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cardURL)
        ImageView mIvCardURL;
        @BindView(R.id.tv_intro)
        TextView mTvIntro;
        @BindView(R.id.tv_city)
        TextView mTvCity;
        @BindView(R.id.tv_price)
        Button mTvPrice;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_priceInCents)
        TextView mTvPriceInCents;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    private OnItemClickListener OnItemClickListener;

    public void setOnItemClickListener(MyBanMiCircuitAdapter.OnItemClickListener onItemClickListener) {
        OnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnClickListener(BanMiCirCuitBean.ResultBean.RoutesBean listBean);
    }
}
