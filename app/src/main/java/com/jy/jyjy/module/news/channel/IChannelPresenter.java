package com.jy.jyjy.module.news.channel;

import com.jy.jyjy.module.base.ILocalPresenter;

/**
 * Created by long on 2016/12/15.
 * 频道 Presenter 接口
 */
public interface IChannelPresenter<T> extends ILocalPresenter<T> {

    /**
     * 交换
     * @param fromPos
     * @param toPos
     */
    void swap(int fromPos, int toPos);
}
