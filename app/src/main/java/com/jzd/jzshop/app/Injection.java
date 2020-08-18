package com.jzd.jzshop.app;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.data.http.HttpDataSource;
import com.jzd.jzshop.data.local.LocalDataSource;
import com.jzd.jzshop.data.http.HttpDataSourceImpl;
import com.jzd.jzshop.data.http.ApiService;
import com.jzd.jzshop.data.local.LocalDataSourceImpl;
import com.jzd.jzshop.utils.net_utils.RetrofitClient;


/**
 * 注入全局的数据仓库，可以考虑使用Dagger2。（根据项目实际情况搭建，千万不要为了架构而架构）
 * Created by jzd on 2019/3/26.
 */
public class Injection {
    public static Repository provideDemoRepository() {
        //网络API服务
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        //网络数据源
        HttpDataSource httpDataSource = HttpDataSourceImpl.getInstance(apiService);
        //本地数据源
        LocalDataSource localDataSource = LocalDataSourceImpl.getInstance();
        //两条分支组成一个数据仓库
        return Repository.getInstance(httpDataSource, localDataSource);
    }
}
