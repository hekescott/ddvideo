package com.baby.app.modules.channel.adapter;

import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.channel.ChannelTagBean;
import com.android.baselibrary.service.bean.channel.TagClassBean;
import com.android.baselibrary.util.ResourceUtil;
import com.android.baselibrary.util.ScreenUtil;
import com.baby.app.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yongqianggeng on 2018/10/4.
 * 左边
 */

public class TagTopAdapter extends BaseQuickAdapter<TagClassBean.Data, BaseViewHolder> {

    private TagLeftAdapterLisenter mTagLeftAdapterLisenter;

    private List<ChannelTagBean> mClassifyChecked;
    private SparseArray<List<ChannelTagBean>> tagSubClassBeanSparse;
    private Set<String> stringSets = new HashSet<>();

    public Set<String> getStringSets() {
        return stringSets;
    }

    public void setmTagLeftAdapterLisenter(TagLeftAdapterLisenter mTagLeftAdapterLisenter) {
        this.mTagLeftAdapterLisenter = mTagLeftAdapterLisenter;
    }

    public TagTopAdapter(int layoutResId, @Nullable List<TagClassBean.Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final TagClassBean.Data item) {
//        RelativeLayout backView = helper.getView(R.id.tag_class_back_view);
        CheckedTextView tag = helper.getView(R.id.tag);
        ImageView imageView = helper.getView(R.id.imageview_bottom);
//        View redView = helper.getView(R.id.left_red_view);
//        TextView textView = helper.getView(R.id.tag_class_name_view);
        if (stringSets.contains(item.getName())) {
//            redView.setVisibility(View.VISIBLE);
//            redView.setBackgroundColor(Color.parseColor("#FFFA7334"));
//            left_tag_item.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
//            textView.setTextColor(Color.parseColor("#FFFA7334"));
            tag.setChecked(true);
            imageView.setVisibility(View.VISIBLE);
        } else {
//            redView.setVisibility(View.INVISIBLE);
//            redView.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
//            left_tag_item.setBackgroundColor(Color.parseColor("#FFF6F6F6"));
//            textView.setTextColor(Color.parseColor("#FF000000"));
            tag.setChecked(false);
            imageView.setVisibility(View.GONE);
        }

        tag.setText(item.getName());

        if (hasClassifyChecked(item.getId())) {
            Drawable drawable = ResourceUtil.getDrawable(R.mipmap.login_s);
            drawable.setBounds(ScreenUtil.dip2px(-10f), 0, 0, ScreenUtil.dip2px(10f));
            tag.setCompoundDrawables(null, null, drawable
                    , null);
        } else {
            tag.setCompoundDrawables(null, null, null, null);
        }

        tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringSets.clear();
                stringSets.add(item.getName());
                notifyDataSetChanged();
                mTagLeftAdapterLisenter.onClick(item);
            }
        });

    }

    /**
     * 子分类是否有被选中
     *
     * @return
     */
    private boolean hasClassifyChecked(int id) {
        if (mClassifyChecked != null && tagSubClassBeanSparse != null) {
            for (ChannelTagBean tagBean : tagSubClassBeanSparse.get(id,
                    new ArrayList<ChannelTagBean>())) {
                for (ChannelTagBean channelTagBean : mClassifyChecked) {
                    if (tagBean.getId() == channelTagBean.getId()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void setClassifyChecked(List<ChannelTagBean> classifyChecked,
                                   SparseArray<List<ChannelTagBean>> tagSubClassBeanSparse) {
        mClassifyChecked = classifyChecked;
        this.tagSubClassBeanSparse = tagSubClassBeanSparse;
    }

    public interface TagLeftAdapterLisenter {
        void onClick(TagClassBean.Data data);
    }
}
