package com.lzq.wanandroid.View.Adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TreeAdapter extends BaseQuickAdapter<Data, BaseViewHolder> {
    @BindView(R.id.rv_tree_flow)
    TagFlowLayout mFlowLayout;

    public TreeAdapter(int layoutResId, @Nullable List<Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Data item) {
        ButterKnife.bind(helper.itemView);
        String[] childName = new String[item.getChildren().size()];
        for (int i = 0; i < item.getChildren().size(); i++) {
            childName[i] = item.getChildren().get(i).getName();
        }
        final LayoutInflater mInflater = LayoutInflater.from();
        mFlowLayout.setAdapter(new TagAdapter<String>(childName) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {

                TextView tv = (TextView) mInflater.inflate(R.layout.flow_tv_item,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });



        helper.setText(R.id.rv_tree_tv_title, item.getName());
        helper.setAdapter(R.id.rv_tree_flow, item);
    }
}
