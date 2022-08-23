package xiaozaiyi.crowd.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.response.AlipayTradePagePayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xiaozaiyi.crowd.config.AliPayResourceConfig;
import xiaozaiyi.crowd.feign.IMysqlClientFeign;
import xiaozaiyi.crowd.feign.IRedisClientFeign;
import xiaozaiyi.crowd.service.Payservice;
import xiaozaiyi.crowd.util.CustomUtils;
import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.utils.PayUtils;
import xiaozaiyi.crowd.vo.OrderVO;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-15   22:12
 */
@Service
@Slf4j
public class PayServiceImpl implements Payservice {

    @Autowired
    private AliPayResourceConfig aliPayResourceConfig;

    @Autowired
    private IMysqlClientFeign iMysqlClientFeign;

    @Autowired
    private IRedisClientFeign iRedisClientFeign;

    @Override
    public R<String> creatOrder(OrderVO orderVO,HttpServletRequest request) {
        try {
            // 1.计算订单总价
            BigDecimal orderAmount = orderVO.getSupportPrice()
                    .multiply(BigDecimal.valueOf(orderVO.getReturnCount()))
                    .add(BigDecimal.valueOf(orderVO.getFreight()));
            orderVO.setOrderAmount(orderAmount);
            // 2.生成订单
            String order = UUID.randomUUID().toString().replace("-", "").toUpperCase();
            String addressId = orderVO.getAddressId();
            String orderNum = order + addressId;
            orderVO.setOrderNum(orderNum);

            //1.创建支付宝客户端
            AlipayTradePagePayResponse alipayTradePagePayResponse
                    = PayUtils.aliPay(aliPayResourceConfig, orderVO.getOrderNum(),
                    orderVO.getOrderAmount().toString(), orderVO.getProjectName());
            String responseBody = alipayTradePagePayResponse.getBody();
            log.info("支付宝支付页面响应体：{}", responseBody);
            String id = CustomUtils.getJwt2Value(request);
            String key = "pay_" + id;
            String orderVOStr = JSON.toJSONString(orderVO);
            //2.将订单信息存入 redis
            iRedisClientFeign.setRedisKeyValueWithTimeout(key, orderVOStr, 15, TimeUnit.MINUTES);
            return R.data(responseBody);
        } catch (Exception e) {
            log.error("创建订单失败！{}", e.getMessage());
            return R.fail("创建订单失败！");
        }
    }

    @Override
    public R<String> returnUrlMethod(HttpServletRequest request) {
        try {
            Map<String, String> params = new HashMap<String, String>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }

            boolean signVerified = AlipaySignature.rsaCheckV1(params,
                    aliPayResourceConfig.getAlipayPublicKey(),
                    aliPayResourceConfig.getCharset(),
                    aliPayResourceConfig.getSignType()); //调用SDK验证签名

            if (signVerified) {
                //商户订单号
                String orderNum = new String(request.getParameter("out_trade_no").
                        getBytes("ISO-8859-1"), "UTF-8");

                //支付宝交易号
                String payOrderNum = new String(request.getParameter("trade_no")
                        .getBytes("ISO-8859-1"), "UTF-8");

                //付款金额
                String orderAmount = new String(request.getParameter("total_amount")
                        .getBytes("ISO-8859-1"), "UTF-8");

                // 保存到数据库
                String id = CustomUtils.getJwt2Value(request);
                String key = "pay_" + id;
                R<String> redisValueByKey = iRedisClientFeign.getRedisValueByKey(key);
                if (!redisValueByKey.isSuccess()) {
                    return R.fail("订单不存在！");
                }
                String orderVOStr = redisValueByKey.getData();
                OrderVO orderVO = JSON.parseObject(orderVOStr, OrderVO.class);
                orderVO.setOrderNum(orderNum);
                orderVO.setPayOrderNum(payOrderNum);
                orderVO.setOrderAmount(new BigDecimal(orderAmount));
                R<OrderVO> orderVOR = iMysqlClientFeign.creatOrder(orderVO);
                if (!orderVOR.isSuccess()) {
                    return R.fail("订单创建失败！");
                }
                // 删除redis中的订单信息
                iRedisClientFeign.removeRedisValueByKey(key);
                return R.data("支付成功！");
            } else {
                return R.fail("验签失败");
            }
        } catch (Exception e) {
            log.error("支付宝returnUrlMethod失败：{}", e.getMessage());
            return R.fail("验签失败");
        }
    }

    @Override
    public R<String> notifyUrlMethod(HttpServletRequest request) {
        try {

            Map<String, String> params = new HashMap<String, String>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }

            boolean signVerified = AlipaySignature.rsaCheckV1(params,
                    aliPayResourceConfig.getAlipayPublicKey(),
                    aliPayResourceConfig.getCharset(),
                    aliPayResourceConfig.getSignType()); //调用SDK验证签名

            //——请在这里编写您的程序（以下代码仅作参考）——

            /* 实际验证过程建议商户务必添加以下校验：
            1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
            2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
            3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
            4、验证app_id是否为该商户本身。
            */
            if (signVerified) {//验证成功
                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").
                        getBytes("ISO-8859-1"), "UTF-8");

                //支付宝交易号
                String trade_no = new String(request.getParameter("trade_no")
                        .getBytes("ISO-8859-1"), "UTF-8");

                //交易状态
                String trade_status = new String(request.getParameter("trade_status")
                        .getBytes("ISO-8859-1"), "UTF-8");

                log.info("支付宝异步通知：out_trade_no:{},trade_no:{},trade_status:{}", out_trade_no, trade_no, trade_status);

                if (trade_status.equals("TRADE_FINISHED")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                } else if (trade_status.equals("TRADE_SUCCESS")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //付款完成后，支付宝系统发送该交易状态通知
                }

                return R.success("验证成功");
            } else {//验证失败
                return R.fail("验证失败");
                //调试用，写文本函数记录程序运行情况是否正常
                //String sWord = AlipaySignature.getSignCheckContentV1(params);
                //AlipayConfig.logResult(sWord);
            }

        } catch (Exception e) {
            log.error("支付宝notifyUrlMethod失败：{}", e.getMessage());
            return R.fail("支付宝notifyUrlMethod失败");
        }
    }
}
