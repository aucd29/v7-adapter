/*
 * Copyright (C) Hanwha S&C Ltd., 2016. All rights reserved.
 *
 * This software is covered by the license agreement between
 * the end user and Hanwha S&C Ltd., and may be
 * used and copied only in accordance with the terms of the
 * said agreement.
 *
 * Hanwha S&C Ltd., assumes no responsibility or
 * liability for any errors or inaccuracies in this software,
 * or any consequential, incidental or indirect damage arising
 * out of the use of the software.
 */

package net.sarangnamu.common.v7.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2016. 4. 21.. <p/>
 *
 * VH 값에 View Holder Type 을 지정하면 됨
 */
public abstract class BkAdapter<VH extends BkViewHolder> extends RecyclerView.Adapter<VH> {
    private static final Logger mLog = LoggerFactory.getLogger(BkAdapter.class);

    protected int mLayoutId;
    protected VH mViewHolder;
    protected String mViewHolderName;

    public BkAdapter(@LayoutRes int layoutId) {
        mLayoutId = layoutId;
    }

    private Class<VH> getGenericClass() {
        return ((Class<VH>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    private void setViewHolderName() {
        if (mViewHolderName == null) {
            mViewHolderName = getGenericClass().getSimpleName();
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            // class 가 abstract 형태일 때와 그렇지 않을 때에 대해서 actual type 을 얻는데 문제가 있다
            // abstract 일 경우 올바르게 가져오나 그렇지 않을 경우 generic symbol 이 그대로 들어오는 문제
            // 를 발견했다. 이게 android 문제인지 ? 는 알아봐야할 사항
            Class types[] = new Class [] { View.class, int.class, int.class, IBkAdapterData.class };
            Class<VH> clazz = getGenericClass();
            Constructor<VH> constructor = clazz.getDeclaredConstructor(types);
            mViewHolder = constructor.newInstance(new Object[] { parent, mLayoutId, viewType, getAdapterData() });

            return mViewHolder;
        } catch (Exception e) {
            mLog.error("ERROR, NEW INSTANCE ERROR");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (holder == null) {
            mLog.error("holder == null");
            return ;
        }

        setViewHolderName();
        IBkAdapterData adapterData = getAdapterData();
        if (adapterData == null) {
            mLog.error("adapterData == null");
            holder.setData(null);
            return ;
        }

        holder.setData(adapterData.getData(mViewHolderName, position));
    }

    @Override
    public int getItemCount() {
        if (getAdapterData() == null) {
            mLog.error("getAdapterData() == null");
            return 0;
        }

        setViewHolderName();

        return getAdapterData().getItemCount(mViewHolderName);
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //
    // ABSTRACT
    //
    ////////////////////////////////////////////////////////////////////////////////////

    protected abstract IBkAdapterData getAdapterData();
}
