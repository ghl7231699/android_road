package com.ghl.lib_dirty.state;

public class StateManger {
    private State mState;
    private int time;

    public StateManger(State state) {
        mState = state;
    }

    public State getState() {
        return mState;
    }

    public void setState(State state) {
        mState = state;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void doAction() {
        if (mState != null) {
            mState.doAction(this);
        }
    }
}
