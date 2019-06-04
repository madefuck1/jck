package com.soufang.base;

/**
 * @Auther: chen
 * @Date: 2019/3/13
 * @Description:
 */
public class AliPayConfig {

    //appid
    public static String app_id = "2016101502178772";
    //商户私钥 PKCS8格式RSA2私钥
    public static String private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDC2YZDK9FTeUfDdDu+nY7dwmm0Gp8arNlw20KN9pWbrVXQqsh08hFyfRsm4jsAqGYRGydLntI5MEvGGKtTeM3JMuY0L2WcvnPjIbHJ0tqJSkv9w01/OZP69HFEPITl1xVuClYx/whBHVkPhCS+LpQ6MGpkb1G3hUNw8Mfkq6k+QKmcMP/KIZM0AQpXyeu69oLWA8zwYRpa8kicovszptAyanPaJuSKhcXOpTpGs/Vb9l+653YvwHHdCk+VPqSCij/7lIC7TLhNBXootdiI/lKDb+eS/qnfFo+je3GpfoKP6GadC0DVmvGHR83YWR8+wHBQPCy6zKv8bmQNtrNJf4SBAgMBAAECggEAKqlPwTM90KX0NXIkhpaeqiUf2aQeg8umjBQlnUmI9h3cdSR/YUOC2GtV34yW2I0GU+Z7Ew6B/q15WbUbBdqkS9IOHwzllY0JeIhvEb5NxNN0keoJ9LoZkU+l4QfTHTObaI1w099/4inXzxLirJUsNRVWpgjNfn36BydgzMtc8dj6E4LWtynNGpxKP959vef2y+jF9zZk0VRNJJ7nLn5P7xKFIoxn6lbrMjOId1BSeS2zfOr0+D74p6epF319DOjqLS2b3gRLuvOqyTvQMSwkN45yHMSwPGKGESA6supaLRY5ZFUApF/Mlw2Bf05vrvrU6bXxvHljzux5dNnou1YgRQKBgQD63zhdSWsjv4MlcB61bQVNk/oYT4ZggeNj6qEjEVSJlYVO4eAJsTPn98eo/eSOsjoETm9pQAX2msRAk2JJ3iDLwb/vHG4qZSZKjCVZuzhomYwcceyqkOeRE3k6I8hi/83yqhw9bCKBg1++Lyfn+VcaUafZ9FSBL+/r4uDdWE02qwKBgQDG1SWyb0pnT+25TH4ijCQdFfFWSRsqwB1IVqkf60XwqoR6dmcaYpdUaIGRThO3xucTCCi7SuWWRtppJ6xaK9fjVngLdpTgKoAwAX93qux6m/dsqE7xI2xOQ64CunLKNDKHmYsauTGgnd9PLZdbCOvhUpzlmVRa9ryDdHDKu9GhgwKBgEdHt18iJjQBDUVM5xzLfi89tVjE97xiC3bvSpuJKAjzl+4C1cJLPCFT0MIeaZt13nAszRENpOQds1dZyRqN56mdQmixHkgjd07FJTOqs2tc/WdzozgPdgxNiStQ0XX3ks+5L87v85ny9vg1s9QAj5Ql0VhnSL9wHG5ch2G83iuDAoGAI21E6zcTHbdLFxH/A27O3HPVbVdXRNW4IfR2694Cty/arOs90aCxb/Oy6k3sC3mgIG76uuxMXes/7S6JcgOP0szMVNaLDinkbylFKfBFdE8n1a9jaWmzWXqLnabU3xxPyINeGqPWqNSYPkZUSn8bcClFFU+tjQubaRytICI45rsCgYEA7cK9n4+axVnHN73WZrQdXG6hA78ygPBPWMY5a1MbjkL6OU81vtsirVAOjABMm0HhIxNQKST8MNDJsbweqg3IabIO8FgQyn+/ZZpiy6ueaAjd6L2wnGEow6s/KgOfIseUL0zypI3EpnU+FMz7aQL8oOimwuIdug9m2acTavOSRMQ=";
    //支付宝公钥 https://openhome.alipay.com/platform/keyManage.htm
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk+wTkd/Y3htpdyUvFGA5OTkTl3rQe7Xhqu5qLTp2DJNFuGLCzN4L/iybsQqX/D1x6M4MOFIC4tzAxUijVZZ/Xvrkye0DrjWIqW1k3E3KwDrpPglUGvXxrXyC4BR9w79O3ZhacMNa5bNnLIhFVwUV3mrex3DbEKwItvFf6Xc6wSxpcVLKqB4ocMyyWPN0gzi1Rk4EApV2QkIO3JJKYsrEybQMCtJ5/8oDyVE+DKEcVqs7d7FGQP8OYFKqOZRpc8yUGx1UbojDNFvb30sfQuanXAuFNiiOGp/zsxBo3VdisiYn5W0Q7jH40aE2L68Ms0dYvipf9m1eAoHOTWx779OmDwIDAQAB";
    //异步回调
    public static String notify_url = "http://27.54.235.89:8081/app/aliPay/notify";
    //同步回调
    public static String return_url = "http://27.54.235.89:8081/app/aliPay/return";
    //签名方式
    public static String sign_type = "RSA2";
    //支付宝网关
    public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";
    // 7.编码
    public static String CHARSET = "UTF-8";
    // 8.返回格式
    public static String FORMAT = "json";

    public static String SELLER_ID = "2088121263025000";

}
