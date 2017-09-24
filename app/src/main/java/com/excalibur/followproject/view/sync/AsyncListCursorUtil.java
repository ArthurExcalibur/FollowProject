package com.excalibur.followproject.view.sync;

import android.app.Activity;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.util.AsyncListUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class AsyncListCursorUtil extends AsyncListUtil<AsyncListAdapter.Data> {

    private static final String TAG = "AsyncListCursorUtil";
    //批量加载数据的个数
    private static final int TILE_SIZE = 2;

    public AsyncListCursorUtil(final Activity activity, final RecyclerView recyclerView) {
        super(AsyncListAdapter.Data.class, TILE_SIZE, new DataCallback<AsyncListAdapter.Data>() {
            Cursor cursor = activity.getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    null,null, null, null);
            @Override
            public int refreshData() {
                Log.d(TAG, "refreshData() called");
                return cursor.getCount();
            }

            @Override
            public void fillData(AsyncListAdapter.Data[] data, int startPosition, int itemCount) {
                Log.d(TAG, "fillData() called with: data = [" + data + "], startPosition = [" + startPosition + "], itemCount = [" + itemCount + "]");
                for (int i = 0; i < itemCount; i++) {
                    cursor.moveToPosition(i + startPosition);
                    AsyncListAdapter.Data item = data[i];
                    if (item == null) {
                        item = new AsyncListAdapter.Data();
                        data[i] = item;
                    }
                    String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));//音乐标题
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));//艺术家
                    item.key = title + "";
                    item.name = artist + "";
                }

                try {
                    // 模拟长时间加载数据的效果
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }, new ViewCallback() {
            @Override
            public void getItemRangeInto(int[] outRange) {
                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                // 如果使用了其他 LayoutManager 注意区别
                if (manager instanceof LinearLayoutManager) {
                    LinearLayoutManager mgr = (LinearLayoutManager) manager;
                    outRange[0] = mgr.findFirstVisibleItemPosition();
                    outRange[1] = mgr.findLastVisibleItemPosition();
                }
                Log.d(TAG, "getItemRangeInto() called with: outRange = [" + outRange[0] + " ~ " + outRange[1] + "]");
            }

            @Override
            public void onDataRefresh() {
                Log.d(TAG, "onDataRefresh() called");
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onItemLoaded(int position) {
                Log.d(TAG, "onItemLoaded() called with: position = [" + position + "]");
                recyclerView.getAdapter().notifyItemChanged(position);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d(TAG, "onScrolled() called with: dx = [" + dx + "], dy = [" + dy + "]");
                onRangeChanged();
            }
        });
    }
}
