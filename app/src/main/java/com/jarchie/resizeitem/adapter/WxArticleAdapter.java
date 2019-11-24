package com.jarchie.resizeitem.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jarchie.resizeitem.R;
import com.jarchie.resizeitem.anim.ExpandableViewHoldersUtil;
import com.jarchie.resizeitem.bean.WxArticleBean;
import com.jarchie.resizeitem.interfaces.OnItemClickListener;

import java.util.List;

/**
 * 作者：created by Jarchie
 * 时间：2019-11-24 12:40:51
 * 邮箱：jarchie520@gmail.com
 * 说明：列表数据适配器
 */
public class WxArticleAdapter extends RecyclerView.Adapter<WxArticleAdapter.WxArticleHolder> {

    private Context mContext;
    private List<WxArticleBean.DataBean.DatasBean> mList;

    public WxArticleAdapter(Context context, List<WxArticleBean.DataBean.DatasBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    ExpandableViewHoldersUtil.KeepOneH<WxArticleHolder> keepOne = new ExpandableViewHoldersUtil.KeepOneH<>();

    //点击事件的回调
    private OnItemClickListener<WxArticleBean.DataBean.DatasBean> onItemClickListener;

    //设置回调监听
    public void setOnItemClickListener(OnItemClickListener<WxArticleBean.DataBean.DatasBean> listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public WxArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WxArticleHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final WxArticleHolder holder, final int position) {
        final WxArticleBean.DataBean.DatasBean bean = mList.get(position);
        holder.bind(position, bean);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class WxArticleHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ExpandableViewHoldersUtil.Expandable {

        private WxArticleHolder mHolder;
        public final TextView mTitle;
        public final TextView mSource;
        public final TextView mReferee;
        public final TextView mLink;
        public final ImageView mRight;
        public final RelativeLayout mTopLayout; //折叠View
        public final LinearLayout mBottomLayout; //折叠View

        public WxArticleHolder(@NonNull View itemView) {
            super(itemView);
            mHolder = this;
            mTitle = itemView.findViewById(R.id.mTitle);
            mSource = itemView.findViewById(R.id.mSource);
            mReferee = itemView.findViewById(R.id.mReferee);
            mLink = itemView.findViewById(R.id.mLink);
            mRight = itemView.findViewById(R.id.mRight);
            mTopLayout = itemView.findViewById(R.id.mTopLayout);
            mBottomLayout = itemView.findViewById(R.id.mBottomLayout);
            mTopLayout.setOnClickListener(this);
        }

        //绑定数据
        public void bind(final int pos, final WxArticleBean.DataBean.DatasBean bean) {
            keepOne.bind(this,pos);
            mTitle.setText(Html.fromHtml(TextUtils.isEmpty(bean.getTitle()) ? "暂无" : bean.getTitle())); //标题
            mSource.setText(TextUtils.isEmpty(
                    bean.getSuperChapterName()) ? "暂无" : bean.getSuperChapterName()); //来源
            mReferee.setText(TextUtils.isEmpty(
                    bean.getChapterName()) ? "推荐人：暂无" : "推荐人：" + bean.getChapterName()); //推荐人
            mLink.setText(TextUtils.isEmpty(bean.getLink()) ? "地址：暂无" : "地址：" + bean.getLink());

            mHolder.mBottomLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(bean, mHolder.mBottomLayout, pos);
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mTopLayout:
                    keepOne.toggle(mHolder, mRight);
                    break;
            }
        }

        @Override
        public View getExpandView() {
            return mBottomLayout;
        }
    }

}
