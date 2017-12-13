package com.excalibur.followproject.fragment.zhuye;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.excalibur.followproject.R;
import com.excalibur.followproject.bean.BaseBean;
import com.excalibur.followproject.bean.Book;
import com.excalibur.followproject.bean.Zhangjie;
import com.excalibur.followproject.bean.ZhangjieEntitiys;
import com.excalibur.followproject.http.BookApi;
import com.excalibur.followproject.http.HttpUtils;
import com.excalibur.followproject.http.ZhangjieApi;
import com.excalibur.followproject.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShuChengFragment extends Fragment {

    Unbinder unbinder;

    private Gson mGson = new Gson();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_shucheng, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void laodZhangjieIndex(String url){
        HttpUtils.getRetrofit().create(ZhangjieApi.class)
                .getZhangjieLiebiao(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhangjieEntitiys>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.toast(getContext(), "网络连接失败，请检查网络设置",ToastUtil.RELEASE_LEVEL);
                    }

                    @Override
                    public void onNext(ZhangjieEntitiys zhangjieEntitiys) {
                        if(zhangjieEntitiys.isStatus()){
                            List<Zhangjie> list = zhangjieEntitiys.getEntityIns();
                        }else{
                            ToastUtil.toast(getContext(), zhangjieEntitiys.getError_info(),ToastUtil.RELEASE_LEVEL);
                        }
                    }
                });
    }

    private void loadData(){
        HttpUtils.getRetrofit().create(BookApi.class)
                .getShuChengBooks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.toast(getContext(), "网络连接失败，请检查网络设置",ToastUtil.RELEASE_LEVEL);
                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        if(baseBean.isStatus()){
                            Type type = new TypeToken<Map<String,String>>(){}.getType();
                            Map<String,String> map = mGson.fromJson(baseBean.getData(),type);

                            List<Book> newBooks = mGson.fromJson(map.get("hot"),new TypeToken<List<Book>>(){}.getType());
                            List<Book> hotBooks = mGson.fromJson(map.get("xinshu"),new TypeToken<List<Book>>(){}.getType());
                            List<Book> guessBooks = mGson.fromJson(map.get("guess"),new TypeToken<List<Book>>(){}.getType());
                            List<Book> geleiBooks = mGson.fromJson(map.get("gelei"),new TypeToken<List<Book>>(){}.getType());
                        }else{
                            ToastUtil.toast(getContext(), baseBean.getError_info(),ToastUtil.RELEASE_LEVEL);
                        }
                    }
                });
    }
}
