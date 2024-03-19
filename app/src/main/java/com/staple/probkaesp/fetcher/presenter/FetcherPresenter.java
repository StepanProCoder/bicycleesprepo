package com.staple.probkaesp.fetcher.presenter;

import android.content.Context;
import android.util.Log;

import com.staple.probkaesp.fetcher.interactor.FetcherInteractor;
import com.staple.probkaesp.fetcher.router.FetcherRouter;
import com.staple.probkaesp.fetcher.view.FetcherActivity;

public class FetcherPresenter
{
    private FetcherActivity view;
    private FetcherInteractor interactor;
    private FetcherRouter router;

    public FetcherPresenter(FetcherActivity activity)
    {
        this.view = activity;
        this.router = new FetcherRouter(view);
        this.interactor = new FetcherInteractor(view, () -> {
            router.shiftToHandshake();
        });
    }
    public void onConfigButtonClick()
    {
        Log.d("BTN", "TAPPED");
        router.shiftToSensorSelection();
        interactor.stopTimer();
    }

    public void onRefreshButtonClick()
    {
        interactor.setIsGetOrPost(false);
        interactor.setEraseFlag(true);
    }

}
