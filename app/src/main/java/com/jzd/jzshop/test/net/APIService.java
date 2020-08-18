package com.jzd.jzshop.test.net;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * @author LXB
 * @description:
 * @date :2019/12/26 17:46
 */
public interface APIService {

    @Multipart
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.info&mod=avatar")
    Observable<HttpResponse<List<String>>> uploadMultipleTypeFile(@Part("reqData") String reqData, @Part("sign") String sign,
                                                                  @Part("avatar") String des, @PartMap Map<String, RequestBody> params);

}
