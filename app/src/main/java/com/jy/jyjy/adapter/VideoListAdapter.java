package com.jy.jyjy.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.jy.jyjy.R;
import com.jy.jyjy.local.table.VideoInfo;
import com.jy.jyjy.module.video.player.VideoPlayerActivity;
import com.jy.jyjy.utils.DefIconFactory;
import com.jy.jyjy.utils.ImageLoader;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;

import java.util.List;

/**
 * Created by long on 2016/10/11.
 */

public class VideoListAdapter extends BaseQuickAdapter<VideoInfo> {

    public VideoListAdapter(Context context) {
        super(context);
    }

    public VideoListAdapter(Context context, List<VideoInfo> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_video_list;
    }

    @Override
    protected void convert(final BaseViewHolder holder, final VideoInfo item) {
        final ImageView ivPhoto = holder.getView(R.id.iv_photo);
        ImageLoader.loadFitCenter(mContext, item.getCover(), ivPhoto, DefIconFactory.provideIcon());
        holder.setText(R.id.tv_title, item.getTitle());
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoPlayerActivity.launch(mContext, item);
            }
        });
    }

}
