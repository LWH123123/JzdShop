package com.jzd.jzshop.entity;

/**
 * @author LXB
 * @description:  积分商城调起原生支付 返回支付参数实体
 * @date :2019/12/10 10:14
 */
public class OrderToPayEntityH5 {
    /**
     * alipay_info : app_id=2019110268804807&biz_content=%7B%22timeout_express%22%3A%2215d%22%2C%22total_amount%22%3A%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%5Cu5e73%5Cu53f0%5Cu5e97%5Cu94fa%5Cu79ef%5Cu5206%5Cu5151%5Cu6362%5Cu5355%5Cu53f7EE20191210100052087682%22%2C%22body%22%3A%222%3A20%3AAPP%22%2C%22out_trade_no%22%3A%22EE20191210100052087682%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Ftest.gtt20.com%2Faddons%2Fewei_shopv2%2Fpayment%2Falipay%2Fnotify.php&sign_type=RSA2&timestamp=2019-12-10+10%3A00%3A52&version=1.0&sign=aG%2BlZZv8hnvL4TYGvyvSOGyElD%2B2cTlApNFxZI%2Bzm69uPdZeVDJDcyczqEkWH3oPVRY7uwNQ2GznqZNgH%2FfjZ1Q87dcwdXXAG3OW%2FYhFfUl76rOK1%2FMuI1NBb1p0B1UWqWCDi4ChQgq0RCHGoZaS3bwo9lvfEQpssDt5uMq25M4aG5D7Og%2F6mr9cU68P3O46b0MKqf3uaFw17LQqT2WbdTDm1MNla2QZ64RwIj%2FCVRIn2CcwVvTdRs8HNWqSzy761%2Bdf2jeSTo6thwHWs1RyogOUtLkvz%2Fjh2kdAiopSdrs0vx9BmGyRQ%2BbrlP0OU1VX7EZgTHpJmbWH%2FsSQGnXJ%2Fg%3D%3D
     * return_url : http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=creditshop.log.detail&id=134
     */

    private String alipay_info;
    private String return_url;

    public String getAlipay_info() {
        return alipay_info;
    }

    public void setAlipay_info(String alipay_info) {
        this.alipay_info = alipay_info;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }
}
