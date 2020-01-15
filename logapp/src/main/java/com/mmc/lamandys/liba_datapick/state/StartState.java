package com.mmc.lamandys.liba_datapick.state;

public class StartState implements State {
    @Override
    public void doAction(StateManger context) {
        if (context.getTime() == 1) {
            System.out.println("我是开始状态");
        } else {

        }
    }
}
