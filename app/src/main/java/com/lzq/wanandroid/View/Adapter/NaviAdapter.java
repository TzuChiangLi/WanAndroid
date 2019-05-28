package com.lzq.wanandroid.View.Adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzq.wanandroid.Api.FlowTagCallBack;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NaviAdapter extends BaseQuickAdapter<Data, BaseViewHolder>{
    @BindView(R.id.rv_tree_flow)
    TagFlowLayout mFlowLayout;
    private FlowTagCallBack flowTagCallBack;

    public NaviAdapter(int layoutResId, @Nullable List<Data> data, FlowTagCallBack flowTagCallBack) {
        super(layoutResId, data);
        this.flowTagCallBack = flowTagCallBack;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Data item) {
        ButterKnife.bind(this, helper.itemView);
        if (item.getName() != null) {
            helper.setText(R.id.rv_tree_tv_title, item.getName());
        }
        if (item.getArticles() != null) {
            String[] titleName = new String[item.getArticles().size()];
            for (int i = 0; i < item.getArticles().size(); i++) {
                titleName[i] = item.getArticles().get(i).getTitle();
            }
            final LayoutInflater mInflater = LayoutInflater.from(helper.itemView.getContext());
            mFlowLayout.setAdapter(new TagAdapter<String>(titleName) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) mInflater.inflate(R.layout.flow_tv_item,
                            mFlowLayout, false);
                    tv.setText(s);
                    return tv;
                }
            });
            mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    flowTagCallBack.getTreeLink(item.getArticles().get(position).getLink());
                    return true;
                }
            });
        }


    }

}
