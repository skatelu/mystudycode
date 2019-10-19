package com.szkingdom.test;

import com.szkingdom.dao.DaoFundAsset;
import com.szkingdom.data.DataFundAsset;

public class TestMybatis {

    public static void main(String[] args) throws Exception{
        DataFundAsset dataFundAsset = new DataFundAsset();
        dataFundAsset.setFundId(1035348);
        dataFundAsset.setCustId(6100033305L);
        dataFundAsset.setMoneyType('0');
        DaoFundAsset daoFundAsset = new DaoFundAsset();
        try {
            DataFundAsset resultDataFundAsset = daoFundAsset.selectUnique(dataFundAsset);

            System.out.println(resultDataFundAsset.toString());

            if (resultDataFundAsset != null) {
                //回写数据
                //resultDataFundAsset.setFundAvl(500.65);

                //daoFundAsset.update(resultDataFundAsset);
            }
        } catch (Exception e) {
            throw new Exception("TABLE_FundAsset 更新操作异常", e);
        }
    }

}
