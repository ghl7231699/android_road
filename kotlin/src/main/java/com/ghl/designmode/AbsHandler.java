package com.ghl.designmode;

public abstract class AbsHandler {
    protected AbsHandler successor;

    public abstract void handlerRequest(String condition);
}
