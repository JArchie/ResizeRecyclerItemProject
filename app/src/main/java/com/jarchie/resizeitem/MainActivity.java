package com.jarchie.resizeitem;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jarchie.resizeitem.activity.AdBrowserActivity;
import com.jarchie.resizeitem.adapter.WxArticleAdapter;
import com.jarchie.resizeitem.bean.WxArticleBean;
import com.jarchie.resizeitem.constants.Constant;
import com.jarchie.resizeitem.interfaces.OnItemClickListener;
import com.jarchie.resizeitem.view.LoadingView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：created by Jarchie
 * 时间：2019-11-24 11:18:51
 * 邮箱：jarchie520@gmail.com
 * 说明：项目主页
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecycler;
    private LoadingView mLoadingView;
    private List<WxArticleBean.DataBean.DatasBean> mList = new ArrayList<>();
    private WxArticleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initData();
    }

    //初始化数据
    private void initData() {
        mLoadingView.show();
        OkGo.<String>get(Constant.GET_ARTICAL_LIST)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mLoadingView.hide();
                        if (response.isSuccessful()){
                            String body = response.body();
                            Gson gson = new Gson();
                            WxArticleBean articleBean = gson.fromJson(body,WxArticleBean.class);
                            if (articleBean.getErrorCode()==0){
                                if (articleBean.getData() != null){
                                    WxArticleBean.DataBean dataBean = articleBean.getData();
                                    if (dataBean.getDatas()!=null && dataBean.getDatas().size()>0){
                                        mList.clear();
                                        mList.addAll(dataBean.getDatas());
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mLoadingView.hide();
                        Toast.makeText(MainActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //初始化监听事件
    private void initListener() {
        mAdapter.setOnItemClickListener(new OnItemClickListener<WxArticleBean.DataBean.DatasBean>() {
            @Override
            public void onItemClick(WxArticleBean.DataBean.DatasBean bean, View view, int position) {
                if (bean != null && !TextUtils.isEmpty(bean.getLink())) {
                    Intent intent = new Intent(MainActivity.this, AdBrowserActivity.class);
                    intent.putExtra("link",bean.getLink());
                    startActivity(intent);
                }
            }
        });
    }

    //初始化View
    private void initView() {
        mRecycler = findViewById(R.id.mRecycler);
        mLoadingView = new LoadingView(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        if (mAdapter == null) {
            mAdapter = new WxArticleAdapter(this, mList);
            mRecycler.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

}
