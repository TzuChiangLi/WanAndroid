package com.lzq.wanandroid.View.Adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzq.wanandroid.Model.Datas;
import com.lzq.wanandroid.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.ButterKnife;

public class ContentAdapter extends BaseItemDraggableAdapter<Datas, BaseViewHolder>  {
    @BindColor(R.color.bg_rv_item_tv)
    int bg_rv_item_tv;
    private List<Datas> mList = new ArrayList<>();

    public ContentAdapter(View view, @Nullable List<Datas> data) {
        super(data);
        ButterKnife.bind(view);
    }

    public ContentAdapter(View view, int layoutResId, @Nullable List<Datas> data) {
        super(layoutResId, data);
        ButterKnife.bind(view);
        mList = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, Datas item) {
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
            if (item.isCollect()){
                helper.setImageResource(R.id.rv_article_imgbtn_save,R.mipmap.collect_yes);
            }else {
                helper.setImageResource(R.id.rv_article_imgbtn_save,R.mipmap.collect_normal);
            }
        }
    }

    public void addData(List<Datas> data) {
        if (data.size() != 0 || data != null) {
            for (int i = 0; i < data.size(); i++) {
                mList.add(data.get(i));
            }
        }
        notifyDataSetChanged();
    }


    public List<Datas> getData(){
        return mList;
    }
}