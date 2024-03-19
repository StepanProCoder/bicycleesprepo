package com.staple.probkaesp.handshaker.presenter;

import com.staple.probkaesp.datamodels.HandshakeData;
import com.staple.probkaesp.handshaker.interactor.HandshakeInteractor;
import com.staple.probkaesp.handshaker.router.HandshakeRouter;
import com.staple.probkaesp.handshaker.view.HandshakeActivity;

public class HandshakePresenter
{
    private HandshakeInteractor interactor;
    private HandshakeActivity view;
    private HandshakeRouter router;

    public HandshakePresenter(HandshakeActivity activity)
    {
        view = activity;
        router = new HandshakeRouter(view);
        interactor = new HandshakeInteractor();
    }
    public void onSubmitButtonDidClick(HandshakeData handshakeData)
    {
        interactor.postHandshake(handshakeData, view);
        router.shiftToLottie();
    }
}
