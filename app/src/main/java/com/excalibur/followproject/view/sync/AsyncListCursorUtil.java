package com.excalibur.followproject.view.sync;

import android.support.v7.util.AsyncListUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.excalibur.followproject.bean.Book;

import java.util.List;

public class AsyncListCursorUtil extends AsyncListUtil<AsyncListAdapter.Data> {

    //批量加载数据的个数
    private static final int TILE_SIZE = 9;

    public AsyncListCursorUtil(final RecyclerView recyclerView) {
        super(AsyncListAdapter.Data.class, TILE_SIZE, new DataCallback<AsyncListAdapter.Data>() {
            List<Book> bookList;
            @Override
            public int refreshData() {
                return bookList.size();
            }

            @Override
            public void fillData(AsyncListAdapter.Data[] data, int startPosition, int itemCount) {
                for (int i = 0; i < itemCount; i++) {
                    Book book = bookList.get(i + startPosition);
                    AsyncListAdapter.Data item = data[i];
                    if (item == null) {
                        item = new AsyncListAdapter.Data();
                        data[i] = item;
                    }
                    item.name = book.getName();
                    item.author = book.getAuthor();
                    item.img = book.getImg();
                }
            }

        }, new ViewCallback() {
            @Override
            public void getItemRangeInto(int[] outRange) {
                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                // 如果使用了其他 LayoutManager 注意区别
                if (manager instanceof GridLayoutManager) {
                    GridLayoutManager mgr = (GridLayoutManager) manager;
                    outRange[0] = mgr.findFirstVisibleItemPosition();
                    outRange[1] = mgr.findLastVisibleItemPosition();
                }
            }

            @Override
            public void onDataRefresh() {
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onItemLoaded(int position) {
                recyclerView.getAdapter().notifyItemChanged(position);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                onRangeChanged();
            }
        });
    }
}
