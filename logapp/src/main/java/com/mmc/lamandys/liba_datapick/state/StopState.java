package com.mmc.lamandys.liba_datapick.state;

public class StopState implements State {
    @Override
    public void doAction(StateManger context) {
        if (context.getTime() == 1) {
            System.out.println("我是停止状态");
        }else{

        }
    }
}
