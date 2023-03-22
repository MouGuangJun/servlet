package com.servlet.osf.services.server;

import com.servlet.entity.KeyValue;
import com.servlet.osf.OSFContext;
import com.servlet.osf.constant.OSFCode;
import com.servlet.osf.entity.esb.ReqEsbHeader;
import com.servlet.osf.exception.OSFException;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.services.client.SimpleOSFClientService;
import com.servlet.osf.utils.OSFUtils;
import com.servlet.utils.GlobalThreadPool;

import java.util.HashMap;
import java.util.Map;

/**
 * 异步接口，接收到请求就立即返回，业务逻辑处理完后在调用接口通知消费方
 */
public abstract class AsyncOSFServerService extends SimpleOSFServerService implements Runnable {

    // 当前服务和通知对象的服务、场景码映射
    private final static Map<String, KeyValue<String, String>> serverMap = new HashMap<>();

    private ReqServiceMsg request;// 请求信息

    protected OSFContext context;// 上下文信息

    private String serviceName;// 服务名

    private String scenesCode;// 场景编号

    private String oriGlobalSeq;// 原请求全局流水号

    /**
     * 配合{@link AsyncOSFServerService#asyncReqPayload()}使用
     */
    protected Object asyncReqPayload;// 通知原服务端时，请求数据

    static {
        serverMap.put("P08N00070X", KeyValue.valueOf("P01Q0001", "25"));
    }

    @Override
    public void proccess(ReqServiceMsg request, OSFContext context) throws Exception {
        if (GlobalThreadPool.getBlockingQueueSize() >= 30) {
            setFailed(OSFCode.SERVER_BUSY, "server is busy");
            return;
        }

        // 给关键属性赋值
        this.request = request;
        this.context = context;
        ReqEsbHeader esbHeader = request.getEsbHeader();
        serviceName = esbHeader.getSERVICE_NAME();
        scenesCode = esbHeader.getSCENES_CODE();
        oriGlobalSeq = esbHeader.getGLOBAL_SEQ();

        // 设置同步返回的内容
        respPayload = respPayload();

        // 执行异步服务操作
        GlobalThreadPool.getExecutor().submit(this);
    }

    @Override
    public void run() {
        try {
            boolean exec = asyncExec(request, context);
            if (!exec) {
                // 创建客户端
                SimpleOSFClientService client = packClient();
                // 先获取全局流水号
                String globalSeq = OSFUtils.getTxLogNo();
                client.setEsb_SERVICE_REQ_SEQ(globalSeq);
                // 将全局流水号挂载到原报文下
                saveGlobalSeq("G" + globalSeq);

                // 发送请求
                client.sendMessage();
                // 获取响应结果
                System.out.println(client.getRespPayload());
            }
        } catch (Exception e) {
            throw new OSFException(OSFException.ASYNC_EXECUTE_ERROR, e);
        }

    }

    /**
     * 封装客户端请求信息
     *
     * @return 客户端请求信息
     * @throws Exception 一般异常
     */
    protected SimpleOSFClientService packClient() throws Exception {
        // 业务逻辑执行完成后，发送ESB请求通知客户方
        KeyValue<String, String> respServer = serverMap.get(serviceName + scenesCode);
        if (respServer == null) {
            throw new OSFException(OSFException.ASYNC_SERVER_NOT_FOUND,
                    "异步服务返回场景未定义，服务名：[" + serviceName + "]，场景码：[" + scenesCode + "]");
        }

        SimpleOSFClientService client = new SimpleOSFClientService(respServer.getKey(), respServer.getValue());
        client.setReqPayload(asyncReqPayload());
        client.setRespClazz(asyncResponseClazz());

        return client;
    }

    /**
     * 将全局流水号挂载到原报文下
     *
     * @param globalSeq 全局流水号
     */
    protected void saveGlobalSeq(String globalSeq) {
        System.out.println("原流水号：" + oriGlobalSeq);
        System.out.println("新流水号：" + globalSeq);
    }

    /**
     * 同步返回的内容（可以不用设值）
     *
     * @return 返回内容
     */
    protected abstract Object respPayload();

    /**
     * 通知原服务端时，请求数据
     *
     * @return 请求数据
     */
    protected abstract Object asyncReqPayload();

    /**
     * 获取通知原服务端时，响应的数据对应的字节码
     *
     * @return 数据字节码
     */
    public abstract Class<?> asyncResponseClazz();

    /**
     * 异步执行业务逻辑
     *
     * @param request 请求信息
     * @param context 响应信息
     * @return true -> 后续不再发送请求（自己的业务逻辑中去发送）； false -> 后续统一发送请求
     * @throws OSFException OSF异常
     */
    public abstract boolean asyncExec(ReqServiceMsg request, OSFContext context) throws Exception;
}