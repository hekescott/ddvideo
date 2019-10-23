package com.baby.app.modules.channel.page;

import android.graphics.Rect;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.service.bean.channel.ChannelDataBean;
import com.android.baselibrary.service.bean.channel.ChannelTagBean;
import com.android.baselibrary.service.bean.channel.ChannelTagDataBean;
import com.android.baselibrary.service.bean.channel.TagClassBean;
import com.android.baselibrary.util.ScreenUtil;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.baby.app.R;
import com.baby.app.application.IBaseFragment;
import com.baby.app.modules.channel.adapter.TagRightAdapter;
import com.baby.app.modules.channel.adapter.TagTopAdapter;
import com.baby.app.modules.channel.presenter.ChannelPresenter;
import com.baby.app.modules.channel.view.ChannelView;

import java.util.ArrayList;
import java.util.List;

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class ChannelTagFragment extends IBaseFragment implements ChannelView {

    RecyclerView tagLayout;
    RecyclerView classifyLayout;
    View confirmBtn;
//    @BindView(R.id.c_t_bottom_recycle_view)
//    RecyclerView bottomRecyclerView;

    private TagTopAdapter mTagLeftAdapter;
    private TagRightAdapter mTagRightAdapter;
//    private TagBottomAdapter mTagBottomAdapter;

    private ChannelPresenter mChannelPresenter;
    //分类
    private List<TagClassBean.Data> tagClassBeanList = new ArrayList<>();
    private SparseArray<List<ChannelTagBean>> tagSubClassBeanSparse = new SparseArray<>();//所有分类标签数据
    //标签
    private List<ChannelTagBean> tagSubClassBeanList = new ArrayList<>();
    private List<ChannelTagBean> tagBottomClassBeanList = new ArrayList<>();

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_channel_tag;
    }

//    @Override
//    protected void onTitleClickListen(TitleBuilder.TitleButton clicked) {
//        switch (clicked) {
//            case RIGHT:
//                if (tagBottomClassBeanList.size() > 0) {
//                    TagListActivity.startTagList(tagBottomClassBeanList);
//                } else {
//                    ToastUtil.showToast("请选择标签");
//                }
//
//                break;
//        }
//    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        setToolBarVisible(View.GONE);
    }

    @Override
    public void initUiAndListener(View view) {
        mChannelPresenter = new ChannelPresenter(this);
        tagLayout = view.findViewById(R.id.tagLayout);
        classifyLayout = view.findViewById(R.id.classifyLayout);
        confirmBtn = view.findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tagBottomClassBeanList.size() > 0) {
                    TagListActivity.startTagList(tagBottomClassBeanList);
                } else {
                    ToastUtil.showToast("请选择标签");
                }
            }
        });
        initTopRecycleView();
//        initRightRecycleView();
        initBottomRecycleView();

        mChannelPresenter.fetchTagClassData();
    }

    private void initTopRecycleView() {
        tagLayout.setLayoutManager(new GridLayoutManager(mContext, 4));
        tagLayout.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                       RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = ScreenUtil.dip2px(mContext, 10);
                outRect.bottom = ScreenUtil.dip2px(mContext, 18);
            }
        });
        mTagLeftAdapter = new TagTopAdapter(R.layout.item_tag_top_layout, tagClassBeanList);
        mTagLeftAdapter.getStringSets().add("全部");
        tagLayout.setAdapter(mTagLeftAdapter);
        mTagLeftAdapter.setmTagLeftAdapterLisenter(new TagTopAdapter.TagLeftAdapterLisenter() {
            @Override
            public void onClick(TagClassBean.Data data) {
                mChannelPresenter.selectTagsByType(data.getId());
            }
        });
    }

    private void initBottomRecycleView() {
        classifyLayout.setLayoutManager(new GridLayoutManager(mContext, 3));
        classifyLayout.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                       RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ScreenUtil.dip2px(mContext, 18);
            }
        });
        mTagRightAdapter = new TagRightAdapter(R.layout.item_tag_right_layout, tagSubClassBeanList);
        mTagRightAdapter.setTagRightAdapterLisenter(new TagRightAdapter.TagRightAdapterLisenter() {
            @Override
            public void selectedSubClass(ChannelTagBean tagSubClassBean) {
                tagBottomClassBeanList.add(tagSubClassBean);
                mTagLeftAdapter.setClassifyChecked(tagBottomClassBeanList, tagSubClassBeanSparse);
                mTagLeftAdapter.notifyDataSetChanged();
                confirmBtn.setVisibility(View.VISIBLE);
            }

            @Override
            public void unSelectedSubClass(ChannelTagBean tagSubClassBean) {
                tagBottomClassBeanList.remove(tagSubClassBean);
                mTagLeftAdapter.setClassifyChecked(tagBottomClassBeanList, tagSubClassBeanSparse);
                mTagLeftAdapter.notifyDataSetChanged();
                if (tagBottomClassBeanList.isEmpty()) {
                    confirmBtn.setVisibility(View.GONE);
                }
//                mTagBottomAdapter.notifyDataSetChanged();
            }
        });

        classifyLayout.setAdapter(mTagRightAdapter);
    }

//    private void initBottomRecycleView() {
//
//        bottomRecyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
//        bottomRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
//            RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                outRect.bottom = ScreenUtil.dip2px(mContext, 10);
//            }
//        });
//        mTagBottomAdapter = new TagBottomAdapter(R.layout.item_tag_bottom_layout,
//        tagBottomClassBeanList);
//        mTagBottomAdapter.setTagBottomAdapterLisenter(new TagBottomAdapter
//        .TagBottomAdapterLisenter() {
//            @Override
//            public void deleteSubClass(ChannelTagBean tagSubClassBean) {
//                tagBottomClassBeanList.remove(tagSubClassBean);
//                mTagBottomAdapter.notifyDataSetChanged();
//
//                mTagRightAdapter.getStringSets().remove(tagSubClassBean.getId()+"");
//                mTagRightAdapter.notifyDataSetChanged();
//            }
//        });
//        bottomRecyclerView.setAdapter(mTagBottomAdapter);
//    }

    @Override
    public void refresh(ChannelDataBean channelDataBean) {

    }

    @Override
    public void refreshTagClass(TagClassBean tagClassBean) {
        mTagLeftAdapter.addData(tagClassBean.getData());
        mChannelPresenter.selectTagsByType(-1);
    }

    @Override
    public void refreshChannelTag(int id, ChannelTagDataBean channelTagDataBean) {
        tagSubClassBeanList.clear();
        if (channelTagDataBean != null && channelTagDataBean.getData() != null) {
            tagSubClassBeanSparse.put(id, channelTagDataBean.getData());
            mTagRightAdapter.addData(channelTagDataBean.getData());
        } else {
            mTagRightAdapter.notifyDataSetChanged();
        }
    }
}
