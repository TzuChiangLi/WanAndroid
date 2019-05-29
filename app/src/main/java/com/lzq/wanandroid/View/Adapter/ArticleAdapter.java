package com.lzq.wanandroid.View.Adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.Utils.StringUtils;

import java.util.List;

import butterknife.BindColor;
import butterknife.ButterKnife;

public class ArticleAdapter extends BaseQuickAdapter<Data, BaseViewHolder> {
    @BindColor(R.color.bg_rv_item_tv)
    int bg_rv_item_tv;
    private int type;

    public ArticleAdapter(View view, int type, @Nullable List<Data> data) {
        super(data);
        this.type = type;
    }

    public ArticleAdapter(View view, int type, int layoutResId, @Nullable List<Data> data) {
        super(layoutResId, data);
        this.type = type;
        ButterKnife.bind(view);
    }

    @Override
    protected void convert(BaseViewHolder helper, Data item) {
        switch (type) {
            case StringUtils.RV_ITEM_NORMAL:
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
                    helper.addOnClickListener(R.id.rv_article_imgbtn_save);
                    if (item.isCollect()) {
                        helper.setImageResource(R.id.rv_article_imgbtn_save, R.mipmap.collect_yes);
                    } else {
                        helper.setImageResource(R.id.rv_article_imgbtn_save, R.mipmap.collect_normal);
                    }
                }
                break;
            case StringUtils.RV_ITEM_IMG:
                if (TextUtils.isEmpty(item.getTitle())) {
                    return;
                } else {
                    helper.setText(R.id.rv_article_img_title, item.getTitle());
                    helper.setText(R.id.rv_article_img_desc, item.getDesc());
                    helper.setText(R.id.rv_article_img_date, item.getNiceDate());

                    helper.setBackgroundColor(R.id.rv_article_img_title, bg_rv_item_tv);
                    helper.setBackgroundColor(R.id.rv_article_img_desc, bg_rv_item_tv);
                    helper.setBackgroundColor(R.id.rv_article_img_date, bg_rv_item_tv);

                }
                break;
        }

    }

}