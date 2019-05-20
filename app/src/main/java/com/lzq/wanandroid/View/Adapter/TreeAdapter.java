package com.lzq.wanandroid.View.Adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzq.wanandroid.Contract.FlowTagCallBack;
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
    private FlowTagCallBack callBack;

    public TreeAdapter(int layoutResId, @Nullable List<Data> data, FlowTagCallBack callBack) {
        super(layoutResId, data);
        this.callBack = callBack;
    }

    @Override
    protected void convert(BaseViewHolder helper, final Data item) {
        ButterKnife.bind(this, helper.itemView);
        helper.addOnClickListener(R.id.rv_tree_flow);
        if (item.getName() != null) {
            helper.setText(R.id.rv_tree_tv_title, item.getName());
        }
        if (item.getChildren() != null) {
            final String[] childName = new String[item.getChildren().size()];
            for (int i = 0; i < item.getChildren().size(); i++) {
                childName[i] = item.getChildren().get(i).getName();
            }
            final LayoutInflater mInflater = LayoutInflater.from(helper.itemView.getContext());
            mFlowLayout.setAdapter(new TagAdapter<String>(childName) {
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
                    callBack.getTreeArticles(item.getChildren().get(position).getId(),position, item.getTitle(), childName);
                    return true;
                }
            });
        }
    }
}
