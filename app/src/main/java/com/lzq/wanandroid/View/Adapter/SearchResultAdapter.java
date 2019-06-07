package com.lzq.wanandroid.View.Adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Model.SearchResult;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.Utils.StringUtils;

import java.util.List;

import butterknife.BindColor;

public class SearchResultAdapter extends BaseQuickAdapter<SearchResult.DataBean.Datas, BaseViewHolder> {
    @BindColor(R.color.bg_rv_item_tv)
    int bg_rv_item_tv;
    private int type;

    public SearchResultAdapter(int type, @Nullable List<SearchResult.DataBean.Datas> data) {
        super(data);
        this.type = type;
    }

    public SearchResultAdapter(int type, int layoutResId, @Nullable List<SearchResult.DataBean.Datas> data) {
        super(layoutResId, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchResult.DataBean.Datas item) {
        switch (type) {
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
                    if (item.isCollect()) {
                        helper.setImageResource(R.id.rv_article_imgbtn_save, R.mipmap.collect_yes);
                    } else {
                        helper.setImageResource(R.id.rv_article_imgbtn_save, R.mipmap.collect_normal);
                    }
                }
                break;
        }

    }

}