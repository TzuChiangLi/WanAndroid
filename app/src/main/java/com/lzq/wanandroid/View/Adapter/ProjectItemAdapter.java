package com.lzq.wanandroid.View.Adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzq.wanandroid.Model.ProjectItem;
import com.lzq.wanandroid.R;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectItemAdapter extends BaseQuickAdapter<ProjectItem.DataBean.Datas, BaseViewHolder> {
    @BindColor(R.color.bg_rv_item_tv)
    int bg_rv_item_tv;
    private View mView;
    private List<ProjectItem.DataBean.Datas> mList = new ArrayList<>();

    public ProjectItemAdapter(View view, @Nullable List<ProjectItem.DataBean.Datas> data) {
        super(data);
        mView = view;
        ButterKnife.bind(view);
    }

    public ProjectItemAdapter(View view, int layoutResId, @Nullable List<ProjectItem.DataBean.Datas> data) {
        super(layoutResId, data);
        mView = view;
        ButterKnife.bind(view);
        mList = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectItem.DataBean.Datas item) {
        if (TextUtils.isEmpty(item.getAuthor())) {
            return;
        } else {
            helper.setText(R.id.rv_article_img_title, item.getTitle().replace("&quot;", "\"").replace("&ldquo;", "\"").replace("&rdquo;", "\""));
            helper.setText(R.id.rv_article_img_date, item.getNiceDate());
            helper.setText(R.id.rv_article_img_desc, StringEscapeUtils.unescapeHtml4(item.getDesc()));
            Glide.with(mView.getContext())
                    .load(item.getEnvelopePic())
                    .into((ImageView) helper.getView(R.id.rv_article_img));
            helper.setBackgroundColor(R.id.rv_article_img_title, bg_rv_item_tv);
            helper.setBackgroundColor(R.id.rv_article_img_date, bg_rv_item_tv);
            helper.setBackgroundColor(R.id.rv_article_img_desc, bg_rv_item_tv);
            helper.setBackgroundColor(R.id.rv_article_img, bg_rv_item_tv);
            helper.addOnClickListener(R.id.rv_article_imgbtn_save);
            if (item.isCollect()) {
                helper.setImageResource(R.id.rv_article_imgbtn_save, R.mipmap.collect_yes);
            } else {
                helper.setImageResource(R.id.rv_article_imgbtn_save, R.mipmap.collect_normal);
            }
        }
    }

    public void addData(List<ProjectItem.DataBean.Datas> data) {
        if (data.size() != 0 || data != null) {
            for (int i = 0; i < data.size(); i++) {
                mList.add(data.get(i));
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public List<ProjectItem.DataBean.Datas> getData() {
        return mList;
    }
}