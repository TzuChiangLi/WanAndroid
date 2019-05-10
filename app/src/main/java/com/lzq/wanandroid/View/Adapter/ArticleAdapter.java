package com.lzq.wanandroid.View.Adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.R;

import java.util.List;

import butterknife.BindColor;
import butterknife.ButterKnife;

public class ArticleAdapter extends BaseQuickAdapter<Data, BaseViewHolder> {
    @BindColor(R.color.bg_rv_item_tv)
    int bg_rv_item_tv;

    public ArticleAdapter(View view, @Nullable List<Data> data) {
        super(data);
    }

    public ArticleAdapter(View view, int layoutResId, @Nullable List<Data> data) {
        super(layoutResId, data);
        ButterKnife.bind(view);
    }

    @Override
    protected void convert(BaseViewHolder helper, Data item) {
        if (TextUtils.isEmpty(item.getAuthor())) {
            return;
        } else {
            helper.setText(R.id.rv_article_title, item.getTitle());
            helper.setText(R.id.rv_article_author, item.getAuthor());
            helper.setText(R.id.rv_article_super_chapter, item.getSuperChapterName());
            helper.setText(R.id.rv_article_chapter, "/" + item.getChapterName());
            helper.setText(R.id.rv_article_date, item.getNiceDate());
            helper.setText(R.id.rv_article_chapter_title, "分类：");
            helper.setText(R.id.rv_article_author_title, "作者：");


            helper.setBackgroundColor(R.id.rv_article_title, bg_rv_item_tv);
            helper.setBackgroundColor(R.id.rv_article_author, bg_rv_item_tv);
            helper.setBackgroundColor(R.id.rv_article_super_chapter, bg_rv_item_tv);
            helper.setBackgroundColor(R.id.rv_article_chapter, bg_rv_item_tv);
            helper.setBackgroundColor(R.id.rv_article_date, bg_rv_item_tv);
            helper.setBackgroundColor(R.id.rv_article_author_title, bg_rv_item_tv);
            helper.setBackgroundColor(R.id.rv_article_chapter_title, bg_rv_item_tv);
        }
    }

}