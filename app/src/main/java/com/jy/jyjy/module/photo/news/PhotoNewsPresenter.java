package com.jy.jyjy.module.photo.news;

import com.jy.jyjy.api.RetrofitService;
import com.jy.jyjy.api.bean.PhotoInfo;
import com.jy.jyjy.module.base.IBasePresenter;
import com.jy.jyjy.module.base.ILoadDataView;
import com.jy.jyjy.widget.EmptyLayout;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by long on 2016/9/5.
 * 图片新闻列表 Presenter
 */
public class PhotoNewsPresenter implements IBasePresenter {

    private String mNextSetId;
    private ILoadDataView mView;


    public PhotoNewsPresenter(ILoadDataView view) {
        this.mView = view;
    }


    @Override
    public void getData(boolean isRefresh) {
        RetrofitService.getPhotoList()
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .compose(mView.<List<PhotoInfo>>bindToLife())
                .subscribe(new Subscriber<List<PhotoInfo>>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
                        mView.showNetError(new EmptyLayout.OnRetryListener() {
                            @Override
                            public void onRetry() {
                                getData(false);
                            }
                        });
                    }

                    @Override
                    public void onNext(List<PhotoInfo> photoList) {
                        mView.loadData(photoList);
                        mNextSetId = photoList.get(photoList.size() - 1).getSetid();
                    }
                });
    }

    @Override
    public void getMoreData() {
        RetrofitService.getPhotoMoreList(mNextSetId)
                .compose(mView.<List<PhotoInfo>>bindToLife())
                .subscribe(new Subscriber<List<PhotoInfo>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.loadNoData();
                    }

                    @Override
                    public void onNext(List<PhotoInfo> photoList) {
                        mView.loadMoreData(photoList);
                    }
                });
    }
}
