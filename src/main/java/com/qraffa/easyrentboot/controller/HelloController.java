package com.qraffa.easyrentboot.controller;

import com.qraffa.easyrentboot.model.ReturnModel;
import com.qraffa.easyrentboot.model.StatusEnum;
import com.qraffa.easyrentboot.model.entity.TestTable;
import com.qraffa.easyrentboot.model.exception.ExceptionModel;
import com.qraffa.easyrentboot.model.req.file.PostFileReq;
import com.qraffa.easyrentboot.model.req.hello.GetExceptionReq;
import com.qraffa.easyrentboot.model.req.hello.GetHelloReq;
import com.qraffa.easyrentboot.model.req.hello.PostObjectReq;
import com.qraffa.easyrentboot.model.res.file.PostFileRes;
import com.qraffa.easyrentboot.model.res.hello.GetHelloRes;
import com.qraffa.easyrentboot.model.res.hello.PostObjectRes;
import com.qraffa.easyrentboot.model.res.testTable.GetTestTableRes;
import com.qraffa.easyrentboot.service.FileService;
import com.qraffa.easyrentboot.service.TestTableService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    @Autowired
    private TestTableService testTableService;
    @Autowired
    private FileService fileService;

    @GetMapping("/hello")
    public ReturnModel getHello(GetHelloReq req){
        // 1. 获取返回数据，可以是数据库查询等，各种业务逻辑
        // 2. 构建返回数据（数据格式转换），仅仅做数据转换
        // 3. 套入返回模板返回


        // 接收格式在model->req下定义
        // 返回格式在model->res下定义
        // 如本接口接收一个message并放入GetHelloReq
        // 返回时需要返回 "接收到的消息" -> originMessage  + "回应的消息" -> returnedMessage


        // 过程演示

        // 1. 获取返回数据，可以是数据库查询等，各种业务逻辑
        System.out.println(req.toString());
        String originMessage = req.getMessage();
        String returnedMessage = "This is a returned message.";

        // 2. 构建返回数据（数据格式转换），仅仅做数据转换
        GetHelloRes res = new GetHelloRes();
        res.setOriginMessage(originMessage);
        res.setReturnedMessage(returnedMessage);

        // 3. 套入返回模板返回

        // 等价于
        // ReturnModel model = new ReturnModel();
        // model.setCode(StatusEnum.OK.getCode());
        // model.setMsg(StatusEnum.OK.getMsg());
        // model.setData(res);

        // 等价于 return new ReturnModel().withStatus(StatusEnum.OK).withData(res);
        return new ReturnModel().withOkData(res);
    }

    @PostMapping("/object")
    @ResponseBody
    // 注意Post请求从Body中读取参数的时候需要为对象加上
    // @RequestBody
    public ReturnModel postObject(@RequestBody PostObjectReq req){
        System.out.println(req);
        // 构建返回数据
        PostObjectRes res = new PostObjectRes();
        BeanUtils.copyProperties(req, res);  // 复制参数1的属性到参数2

        // 套入统一返回模板
        return new ReturnModel().withOkData(res);
    }

    @GetMapping("/test_table")
    public ReturnModel getTestTable(){
        // 查询数据
        List<TestTable> msgList = testTableService.queryAll();

        // 构建返回数据
        List<GetTestTableRes> resList = new ArrayList<>();

        for(int i=0;i<msgList.size();i++){
            TestTable msg = msgList.get(i);
            GetTestTableRes res = new GetTestTableRes();
            BeanUtils.copyProperties(msg, res);// 复制参数1的属性到参数2
            resList.add(res);
        }

        // 套入统一返回模板
        return new ReturnModel().withOkData(resList);
    }

    /**
     * 上传文件
     * @param req
     * @return
     */
    @PostMapping("file")
    public ReturnModel postFile(PostFileReq req) throws Exception{
        // 以下内容在FileService中修改
        // 设置文件存放路径
        // private final String SaveFilePath = "/Users/jabin/code/server/spring_upload";
        // 设置静态文件服务器的ip:端口
        // private final String Host = "http://localhost/static";

        // 获取传入文件
        MultipartFile excelFile = req.getFile();
        // 校验传入文件有效性
        if (excelFile==null || excelFile.isEmpty()){
            throw new RuntimeException("文件传入错误");
        }

        // 存储文件
        String fileName = String.format("%s_%s",new Date().getTime(),excelFile.getOriginalFilename());
        String url = fileService.saveFile(excelFile,fileName);

        // 设置返回的url
        PostFileRes res = new PostFileRes();
        res.setFileUrl(url);

        // 构建返回模板
        return  new ReturnModel().withOkData(res);
    }

    @GetMapping("exception")
    public ReturnModel getException(GetExceptionReq req) throws Exception{
        switch (req.getType()){
            case 0:
                throw new ExceptionModel(StatusEnum.UNAUTHORIZED);
            default:
            throw new ExceptionModel(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 返回错误码错误信息示例
     * @return
     */
    @GetMapping("fail")
    public ReturnModel getFail(){
        // 在StatusEnum里面定义好状态码和对应的消息
        // 返回的时候直接调用

        // 构建返回模板
        // 等价于
        // ReturnModel model = new ReturnModel();
        // model.setCode(StatusEnum.UNAUTHORIZED.getCode());
        // model.setMsg(StatusEnum.UNAUTHORIZED.getMsg());
        // model.setData(null);
        return new ReturnModel().withStatus(StatusEnum.UNAUTHORIZED).withData(null);
    }
}
