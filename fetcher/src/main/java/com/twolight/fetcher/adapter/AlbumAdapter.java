package com.twolight.fetcher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twolight.fetcher.SettingRepertory;
import com.twolight.fetcher.R;
import com.twolight.fetcher.model.Album;

/**
 * Created by twolight on 17/3/16.
 */

public class AlbumAdapter extends BaseRecyclerViewAdapter<Album> {
    private OnAlbumItemClickListener mOnAlbumItemClickListener;

    public AlbumAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_folder_list,null);
        return new FolderListHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {
        final Album album = mData.get(position);
        FolderListHolder holder = (FolderListHolder) h;

        SettingRepertory.getInstance().load(mContext,holder.folderCover,album.getImage());
        holder.folderName.setText(album.getName());
        holder.folderCount.setText("("+album.getCount()+")");
        holder.folderSelectedCount.setVisibility(album.getSelectCount() > 0 ? View.VISIBLE : View.INVISIBLE);
        holder.folderSelectedCount.setText(String.valueOf(album.getSelectCount()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnAlbumItemClickListener != null){
                    mOnAlbumItemClickListener.onAlbumItemClick(album);
                }
            }
        });
    }

    public OnAlbumItemClickListener getOnAlbumItemClickListener() {
        return mOnAlbumItemClickListener;
    }

    public void setOnAlbumItemClickListener(OnAlbumItemClickListener onAlbumItemClickListener) {
        mOnAlbumItemClickListener = onAlbumItemClickListener;
    }

    public interface OnAlbumItemClickListener{
        void onAlbumItemClick(Album album);
    }

    public class FolderListHolder extends RecyclerView.ViewHolder {
        public ImageView folderCover;
        public TextView folderName;
        public TextView folderCount;
        public TextView folderSelectedCount;

        public FolderListHolder(View itemView) {
            super(itemView);
            folderCover = (ImageView) itemView.findViewById(R.id.folder_cover);
            folderName = (TextView) itemView.findViewById(R.id.folder_name);
            folderCount = (TextView) itemView.findViewById(R.id.folder_count);
            folderSelectedCount = (TextView) itemView.findViewById(R.id.folder_selected_count);
        }
    }
}
