package com.idisfkj.hightcopywx.find.model;

import java.util.List;

/**
 * Created by fvelement on 2017/10/10.
 */

public interface EncourageModel {
    void getEncourageData(GetEncourageListener getEncourageListener,int page);
    interface GetEncourageListener{
        void onGetEncourageSuccess(List<EncourageEntity> encourageEntityList);
        void onGetEncourageFail();
    }
}
