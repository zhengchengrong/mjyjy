package com.jy.jyjy.module.news.main;

import com.jy.jyjy.local.table.NewsTypeInfo;
import com.jy.jyjy.local.table.NewsTypeInfoDao;
import com.jy.jyjy.module.base.IRxBusPresenter;
import com.jy.jyjy.rxbus.RxBus;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by long on 2016/9/1.
 * 主页 Presenter
 */
public class NewsMainPresenter implements IRxBusPresenter {

    private final INewsMainView mView;
    private final NewsTypeInfoDao mDbDao;
    private final RxBus mRxBus;

    public NewsMainPresenter(INewsMainView view, NewsTypeInfoDao dbDao, RxBus rxBus) {
        mView = view;
        mDbDao = dbDao;
        mRxBus = rxBus;
    }

    @Override
    public void getData(boolean isRefresh) {
        mDbDao.queryBuilder().rx().list()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<NewsTypeInfo>>() {
                    @Override
                    public void call(List<NewsTypeInfo> newsTypeBeen) {
                        mView.loadData(newsTypeBeen);
                    }
                });
    }

    @Override
    public void getMoreData() {
    }

    @Override
    public <T> void registerRxBus(Class<T> eventType, Action1<T> action) {
        Subscription subscription = mRxBus.doSubscribe(eventType, action, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Logger.e(throwable.toString());
            }
        });
        mRxBus.addSubscription(this, subscription);
    }

    @Override
    public void unregisterRxBus() {
        mRxBus.unSubscribe(this);
    }
}
