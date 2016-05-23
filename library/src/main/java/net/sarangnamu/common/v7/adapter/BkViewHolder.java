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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import butterknife.ButterKnife;

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2016. 4. 21.. <p/>
 *
 * T 값에 전달 받은 Data Type 을 지정하면 됨
 */
public abstract class BkViewHolder<DT> extends RecyclerView.ViewHolder {
    private static final Logger mLog = LoggerFactory.getLogger(BkViewHolder.class);

    protected IBkAdapterData mAdapterData;

    // vh 에 generic 이 있으면 문제가 발생하네 ?
    public BkViewHolder(View parent, @LayoutRes int layoutId, int viewType, IBkAdapterData data) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutId, (ViewGroup) parent, false));

        ButterKnife.bind(this, itemView);
        mAdapterData = data;
    }

    public DT getAdapterData(int pos) {
        if (mAdapterData == null) {
            mLog.error("mAdapterData == null");
            return null;
        }

        return (DT) mAdapterData.getData(getClass().getSimpleName(), pos);
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //
    // ABSTRACT
    //
    ////////////////////////////////////////////////////////////////////////////////////

    public abstract void setData(DT data);
}
