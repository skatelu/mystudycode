package com.example.demo.principle.state;

/**
 * 发放奖品的状态
 */
public class DispenseState extends State {
    @Override
    public void deductMoney() {

    }

    @Override
    public boolean raffle() {
        return false;
    }

    @Override
    public void dispensePrize() {

    }
}
