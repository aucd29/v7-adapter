/*
 * Copyright 2016. Burke Choi All rights reserved.
 *             http://www.sarangnamu.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
