package com.jzd.jzshop.entity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.zhy.http.okhttp.utils.L;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.utils.StringUtils;

public class GoodsEntity implements Serializable {

    /**
     * result : {"title":"参数、优惠券、赠品、秒杀、活动","subtitle":"睡衣女秋冬季仿真丝睡裙长袖性感丝绸冰丝加大加肥胖MM宽松家居服","thumb":["http://gd3.alicdn.com/imgextra/i3/TB1q5iqSFXXXXa1aXXXYXGcGpXX_M2.SS2"],"show_price":99,"productprice":"199.00","show_commission":0,"commission_percent":null,"commission":0,"showsales":"1","sales":3,"unit":"件","dispatch":"包邮","delivery_addr":"","content":"","add_cart":1,"spec_titles":["颜色分类","尺码"],"params":[{"title":"品牌","value":" 纤寐姿"},{"title":"适用场景","value":" 睡衣"},{"title":"面料主材质","value":" 聚酯纤维（涤纶）"},{"title":"成分含量","value":" 95%以上"},{"title":"主面料克重","value":" 200g及以下"},{"title":"图案","value":" 纯色"},{"title":"服装款式细节","value":" 印花"},{"title":"领型","value":" 衬衫领"},{"title":"袖长","value":" 长袖"},{"title":"裙长","value":" 长裙"},{"title":"适用季节","value":" 秋季"},{"title":"家居服风格","value":" 性感"},{"title":"尺码","value":" 2XL 3XL M L XL"},{"title":"面料俗称","value":" 真丝"},{"title":"颜色分类","value":" 长裙银色 长裙蓝色 长裙紫色 长裙粉色 长裙黑色 中裙银色 中裙蓝色 中裙紫色 中裙粉色 中裙黑色 中裙01花色 中裙条纹唐老鸭 中裙条纹财犬 中裙04花色"}],"shareinfo":{"title":"参数、优惠券、赠品、秒杀、活动","imgUrl":"http://gd3.alicdn.com/imgextra/i3/TB1q5iqSFXXXXa1aXXXYXGcGpXX_M2.SS2","desc":"睡衣女秋冬季仿真丝睡裙长袖性感丝绸冰丝加大加肥胖MM宽松家居服","link":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=goods.detail&id=210"},"coupon_data":[{"coupon_id":"imYNXPekC_MXp_fyGYZfTAATt2UiMQ","backtype":"1","enough":"150.00","backmoney":8.5,"timestr":"自领取日后30天有效","canget":0},{"coupon_id":"pnQNmDhlC_AXR_SCPcRYJCWWeWftMQ","backtype":"0","enough":"100.00","backmoney":30,"timestr":"自领取日后30天有效","canget":0}],"gift_info":[{"gift_id":"nIg3ba3qxjA3RKa66WGWj75rMw","title":"无线充","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/H6Q606aPM3601QP1q0371Aq606z3C2QP.jpg","goods_info":[{"gid":"byJdpSJ2A_fXg_uHGUQWZXo2H2poWmyMYoMQ","title":"肯索亚iPhoneXS无线充苹果11Promax充电器8plus手机通用华为mate30p小米三星快充桌面立式支架qi功能发射无限","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/H6Q606aPM3601QP1q0371Aq606z3C2QP.jpg","price":"156.00"}]}],"shop_info":{"id":"zYPW4aw_IB5X_Hs6AVKAqa1WmjaP9nQMw","name":"博世金乾盛专卖店","logo":""},"activity":[{"id":"dispatch","data":[{"type":0,"enough":0},{"type":1,"enough":399}]}],"seckillinfo":[],"presell_info":[]}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{

        private String title;
        private String subtitle;
        private String gid;
        private String show_price;
        private String productprice;
        private int show_commission;
        private Object commission_percent;
        private double commission;
        private String showsales;
        private int sales;
        private String unit;
        private String dispatch;
        private String delivery_addr;
        private String content;
        private int add_cart;
        private ShareinfoBean shareinfo;
        private ShopInfoBean shop_info;
        private int is_favorite;
        private List<String> thumb;
        private List<String> spec_titles;
        private List<ParamsBean> params;
        private List<CouponDataBean> coupon_data;
        private List<GiftInfoBean> gift_info;
        private List<ActivityBean> activity;
        private SeckillinfoBean seckillinfo;
        private PresellinfoBean presell_info;
        private List<CommentBean> comment;
        private String chat_id; //消息所有者ID

        public String getChat_id() {
            return chat_id;
        }

        public void setChat_id(String chat_id) {
            this.chat_id = chat_id;
        }



        //选择规格后的商品价格 数量
        private String price;
        private String total;
        private String url;


        public List<CommentBean> getComment() {
            return comment;
        }

        public void setComment(List<CommentBean> comment) {
            this.comment = comment;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public int getIs_favorite() {
            return is_favorite;
        }

        public void setIs_favorite(int is_favorite) {
            this.is_favorite = is_favorite;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getShow_price() {
            return show_price;
        }

        public void setShow_price(String show_price) {
            this.show_price = show_price;
        }

        public String getProductprice() {
            return productprice;
        }

        public void setProductprice(String productprice) {
            this.productprice = productprice;
        }

        public int getShow_commission() {
            return show_commission;
        }

        public void setShow_commission(int show_commission) {
            this.show_commission = show_commission;
        }

        public Object getCommission_percent() {
            return commission_percent;
        }

        public void setCommission_percent(Object commission_percent) {
            this.commission_percent = commission_percent;
        }

        public double getCommission() {
            return commission;
        }

        public void setCommission(double commission) {
            this.commission = commission;
        }

        public String getShowsales() {
            return showsales;
        }

        public void setShowsales(String showsales) {
            this.showsales = showsales;
        }

        public int getSales() {
            return sales;
        }

        public void setSales(int sales) {
            this.sales = sales;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getDispatch() {
            return dispatch;
        }

        public void setDispatch(String dispatch) {
            this.dispatch = dispatch;
        }

        public String getDelivery_addr() {
            return delivery_addr;
        }

        public void setDelivery_addr(String delivery_addr) {
            this.delivery_addr = delivery_addr;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getAdd_cart() {
            return add_cart;
        }

        public void setAdd_cart(int add_cart) {
            this.add_cart = add_cart;
        }

        public ShareinfoBean getShareinfo() {
            return shareinfo;
        }

        public void setShareinfo(ShareinfoBean shareinfo) {
            this.shareinfo = shareinfo;
        }

        public ShopInfoBean getShop_info() {
            return shop_info;
        }

        public void setShop_info(ShopInfoBean shop_info) {
            this.shop_info = shop_info;
        }

        public List<String> getThumb() {
            return thumb;
        }

        public void setThumb(List<String> thumb) {
            this.thumb = thumb;
        }

        public List<String> getSpec_titles() {
            return spec_titles;
        }

        public void setSpec_titles(List<String> spec_titles) {
            this.spec_titles = spec_titles;
        }

        public List<ParamsBean> getParams() {
            return params;
        }

        public void setParams(List<ParamsBean> params) {
            this.params = params;
        }

        public List<CouponDataBean> getCoupon_data() {
            return coupon_data;
        }

        public void setCoupon_data(List<CouponDataBean> coupon_data) {
            this.coupon_data = coupon_data;
        }

        public List<GiftInfoBean> getGift_info() {
            return gift_info;
        }

        public void setGift_info(List<GiftInfoBean> gift_info) {
            this.gift_info = gift_info;
        }

        public List<ActivityBean> getActivity() {
            return activity;
        }

        public void setActivity(List<ActivityBean> activity) {
            this.activity = activity;
        }

        public SeckillinfoBean getSeckillinfo() {
            return seckillinfo;
        }

        public void setSeckillinfo(SeckillinfoBean seckillinfo) {
            this.seckillinfo = seckillinfo;
        }

        public PresellinfoBean getPresell_info() {
            return presell_info;
        }

        public void setPresell_info(PresellinfoBean presell_info) {
            this.presell_info = presell_info;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public static class ShareinfoBean implements Serializable {
            /**
             * title : 参数、优惠券、赠品、秒杀、活动
             * imgUrl : http://gd3.alicdn.com/imgextra/i3/TB1q5iqSFXXXXa1aXXXYXGcGpXX_M2.SS2
             * desc : 睡衣女秋冬季仿真丝睡裙长袖性感丝绸冰丝加大加肥胖MM宽松家居服
             * link : http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=goods.detail&id=210
             */

            private String title;
            private String imgUrl;
            private String desc;
            private String link;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }

        public static class ShopInfoBean implements Serializable {
            /**
             * id : zYPW4aw_IB5X_Hs6AVKAqa1WmjaP9nQMw
             * name : 博世金乾盛专卖店
             * logo :
             */

            private String id;
            private String name;
            private String logo;
            private String type;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }
        }

        public static class ParamsBean implements Serializable{
            /**
             * title : 品牌
             * value :  纤寐姿
             */

            private String title;
            private String value;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class CouponDataBean implements Serializable{
            /**
             * coupon_id :
             * backtype : 1 imYNXPekC_MXp_fyGYZfTAATt2UiMQ
             * enough : 150.00
             * backmoney : 8.5
             * timestr : 自领取日后30天有效
             * canget : 0
             */

            private String coupon_id;
            private String backtype;
            private String enough;
            private double backmoney;
            private String timestr;
            private int canget;
            private String merch_name;


            public String getMerch_name() {
                return merch_name;
            }

            public void setMerch_name(String merch_name) {
                this.merch_name = merch_name;
            }

            public String getCoupon_id() {
                return coupon_id;
            }

            public void setCoupon_id(String coupon_id) {
                this.coupon_id = coupon_id;
            }

            public String getBacktype() {
                return backtype;
            }

            public void setBacktype(String backtype) {
                this.backtype = backtype;
            }

            public String getEnough() {
                return enough.equals("0")?"不限制": String.format("满%s即可使用",enough);
            }

            public void setEnough(String enough) {
                this.enough = enough;
            }

            public double getBackmoney() {
                return backmoney;
            }
            public String getBackmoneyText() {
                int dEnough = (int) Double.parseDouble(enough);
                int dBackmoney = (int) backmoney;
                return backtype.equals("0")? "满" + dEnough +"-"+dBackmoney+"元": "满" + dEnough +" "+backmoney+"折";
//                return backmoney+(backtype.equals("0")?"元":"折");
            }

            public void setBackmoney(double backmoney) {
                this.backmoney = backmoney;
            }

            public String getTimestr() {
                return String.format("有效期：%s",timestr);
            }

            public void setTimestr(String timestr) {
                this.timestr = timestr;
            }

            public int getCanget() {
                return canget;
            }

            public void setCanget(int canget) {
                this.canget = canget;
            }
        }

        public static class GiftInfoBean implements Serializable {
            /**
             * gift_id : nIg3ba3qxjA3RKa66WGWj75rMw
             * title : 无线充
             * thumb : http://test.gtt20.com/attachment/images/2/2019/10/H6Q606aPM3601QP1q0371Aq606z3C2QP.jpg
             * goods_info : [{"gid":"byJdpSJ2A_fXg_uHGUQWZXo2H2poWmyMYoMQ","title":"肯索亚iPhoneXS无线充苹果11Promax充电器8plus手机通用华为mate30p小米三星快充桌面立式支架qi功能发射无限","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/H6Q606aPM3601QP1q0371Aq606z3C2QP.jpg","price":"156.00"}]
             */

            private String gift_id;
            private String title;
            private String thumb;
            private List<GoodsInfoBean> goods_info;

            public String getGift_id() {
                return gift_id;
            }

            public void setGift_id(String gift_id) {
                this.gift_id = gift_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public List<GoodsInfoBean> getGoods_info() {
                return goods_info;
            }

            public void setGoods_info(List<GoodsInfoBean> goods_info) {
                this.goods_info = goods_info;
            }

            public static class GoodsInfoBean implements Serializable {
                /**
                 * gid : byJdpSJ2A_fXg_uHGUQWZXo2H2poWmyMYoMQ
                 * title : 肯索亚iPhoneXS无线充苹果11Promax充电器8plus手机通用华为mate30p小米三星快充桌面立式支架qi功能发射无限
                 * thumb : http://test.gtt20.com/attachment/images/2/2019/10/H6Q606aPM3601QP1q0371Aq606z3C2QP.jpg
                 * price : 156.00
                 */

                private String gid;
                private String title;
                private String thumb;
                private double price;

                public String getGid() {
                    return gid;
                }

                public void setGid(String gid) {
                    this.gid = gid;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getThumb() {
                    return thumb;
                }

                public void setThumb(String thumb) {
                    this.thumb = thumb;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }
            }
        }

        public static class ActivityBean implements Serializable{
            /**
             * id : dispatch
             * data : [{"type":0,"enough":0},{"type":1,"enough":399}]
             */

            private String id;
            private List<DataBean> data;


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public List<DataBean> getData() {
                return data;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }



            public static class DataBean implements Serializable {
                /**
                 * type : 0
                 * enough : 0
                 */

                private int type;
                private String enough;
                private String money;
                private String id;
                private static Map<String,String> lable=new HashMap<>();
                static {
                    lable.put("enoughs","满减");
                    lable.put("dispatch","包邮");
                    lable.put("enoughdeduct","满减");
                }

                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#D90804"));

                /**
                 * id = enoughs
                 * enough：全场满N元
                 * money：减N元
                 *
                 *
                 * id = enoughdeduct
                 * enough：商户满N元
                 * money：减N元
                 *
                 * id = dispatch
                 * type：包邮方式。0：商品全包邮；1：全场满N元包邮；2:本店满N元包邮；3：单品满N（商品单位）包邮；4：单品满N元包邮
                 * enough：数值
                 * @return
                 */
                @SuppressLint("DefaultLocale")
                public SpannableString getActivityText(String id,String unit){
                    SpannableString spannableString=null;
                    int start=3;
                    int end=start+enough.length()+1;
                    if(id.equals("enoughs")){
                        spannableString=new SpannableString(String.format("全场满¥%S元减%S元",enough,money));
                        end=start+enough.length()+1;
                    }else if(id.equals("enoughdeduct")){
                        spannableString=new SpannableString(String.format("商户满¥%S元减%S元",enough,money));
                        end=start+enough.length()+1;
                    }else if(id.equals("dispatch")){
                        switch (type){
                            case 0:
                                spannableString = new SpannableString("商品全包邮");
                                end=3;
                                break;
                            case 1:
                                spannableString = new SpannableString(String.format("全场满¥%S元包邮",enough));
                                break;
                            case 2:
                                spannableString = new SpannableString(String.format("本店满¥%S元包邮",enough));
                                break;
                            case 3:
                                spannableString = new SpannableString(String.format("单品满%S%s包邮",enough,unit));
                                end+=1;
                                break;
                            case 4:
                                spannableString = new SpannableString(String.format("单品满¥%S元包邮",enough));
                                break;
                        }
                    }else {
                    }
                    if (spannableString != null) {
                        spannableString.setSpan(colorSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        return spannableString;
                    }else {
                        return null;
                    }
                }


                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                /**
                 * enoughs：满减
                 * dispatch：包邮
                 */
                public String getActivityLable(){
                    return lable.get(getId());
                }

                public String getMoney() {
                    return money;
                }

                public void setMoney(String money) {
                    this.money = money;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getEnough() {
                    return enough;
                }

                public void setEnough(String enough) {
                    this.enough = enough;
                }
            }
        }

        public static class CommentBean implements Serializable{
            private String headimg;
            private String nickname;
            private String time;
            private String spec_title;
            private String content;
            private List<String> images;
            private String reply_content;
            private List<String> reply_images;
            private String append_content;
            private List<String> append_images;
            private String append_reply_content;
            private List<String> append_reply_images;


            public String getHeadimg() {
                return headimg;
            }

            public void setHeadimg(String headimg) {
                this.headimg = headimg;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getSpec_title() {
                return spec_title;
            }

            public void setSpec_title(String spec_title) {
                this.spec_title = spec_title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public List<String> getImages() {
                return images;
            }

            public void setImages(List<String> images) {
                this.images = images;
            }

            public String getReply_content() {
                return reply_content;
            }

            public void setReply_content(String reply_content) {
                this.reply_content = reply_content;
            }

            public List<String> getReply_images() {
                return reply_images;
            }

            public void setReply_images(List<String> reply_images) {
                this.reply_images = reply_images;
            }

            public String getAppend_content() {
                return append_content;
            }

            public void setAppend_content(String append_content) {
                this.append_content = append_content;
            }

            public List<String> getAppend_images() {
                return append_images;
            }

            public void setAppend_images(List<String> append_images) {
                this.append_images = append_images;
            }

            public String getAppend_reply_content() {
                return append_reply_content;
            }

            public void setAppend_reply_content(String append_reply_content) {
                this.append_reply_content = append_reply_content;
            }

            public List<String> getAppend_reply_images() {
                return append_reply_images;
            }

            public void setAppend_reply_images(List<String> append_reply_images) {
                this.append_reply_images = append_reply_images;
            }
        }


        public static class SeckillinfoBean implements Serializable{
            private String tag;
            private int percent;
            private long start_time;
            private long end_time;

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public int getPercent() {
                return percent;
            }

            public void setPercent(int percent) {
                this.percent = percent;
            }

            public long getStart_time() {
                return start_time;
            }

            public void setStart_time(long start_time) {
                this.start_time = start_time;
            }

            public long getEnd_time() {
                return end_time;
            }

            public void setEnd_time(long end_time) {
                this.end_time = end_time;
            }
        }

        public static class PresellinfoBean implements Serializable {

        }


    }
}