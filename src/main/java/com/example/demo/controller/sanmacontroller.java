package com.example.demo.controller;

import com.example.demo.util.ExceptionTools;
import com.example.demo.util.TxtR;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.httpclient.HttpClient;


import java.io.*;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by dell on 2020/8/24.
 */

@Api(description = "集团三码本地客户端进程API接口")
@RestController
public class sanmacontroller {

    private static final Logger LOGGER = LoggerFactory.getLogger(sanmacontroller.class);


//    @Autowired
//     private RestTemplate restTemplate;

//    @Autowired
//    public sanmacontroller(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }



    @RequestMapping("/test")
    public   String test(){
        return "hello world！";

    }


    /**
     * oa 代办的
     * @return
     */
    @ApiOperation(value="发送oa 代办的", notes="入参文件为本地：【D:\\dd.log】", produces="application/json")
//    @RequestMapping(value="/oa", method=RequestMethod.POST)
    @GetMapping(value="/oa")
    public   List<String> get1oA(){
       return  MDMDATADPROVIDERSFEEDBACK_SVCNAMETest();

    }

    /**
     * 强关联的重投的
     */
    @RequestMapping("/strongid")
    public  String strongId(){
        return  "";
    }







    private  String uniCode="";

    private  String mmgid="10.11182019072605413";//


    public  List<String>  MDMDATADPROVIDERSFEEDBACK_SVCNAMETest(){
        List<String>  stringList = new ArrayList<>();
        List<String> list = TxtR.readTxtFile("D:\\dd.log");
        System.out.println("读取的入参大小"+list.size());
        for(int i=0;i<list.size();i++) {
            String mmgid1= mmgid+i;
            String mss =getSoapResponseData(getUniCode(list.get(i),mmgid1));
            stringList.add(mss);
            System.err.println("读====>"+mss);
        }
        return  stringList;

    }


    private  String  getUniCode(String uniCode,String mmgid1) {


        String oadaibangStr = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'>\n" +
                "<soapenv:Header>\n" +
                "      <Esb>\n" +
                "         <Route>\n" +
                "            <Sender>10.1118</Sender>\n" +
                "            <Time>20161103170221</Time>\n" +
                "            <ServCode>10.1309.20.unifyReceiveBacklog.SynReq</ServCode>\n" +
                "            <MsgId>"+mmgid1+"</MsgId>\n" +
                "            <AuthCode/>\n" +
                "            <TransId/>\n" +
                "            <AuthType/>\n" +
                "            <Version/>\n" +
                "            <MsgType/>\n" +
                "            <CarryType>0</CarryType>\n" +
                "            <ServTestFlag>0</ServTestFlag>\n" +
                "         </Route>\n" +
                "         </Business>\n" +
                "      </Esb>\n" +
                "   </soapenv:Header>\n" +
                "<soapenv:Body>\n" +
                "<synXml>\n" +
                "<appUniCode>OSS_3MatResAsset</appUniCode>\n" +
                "  <items>\n" +
                "     <item type=\"2\">\n" +
                "         <uniCode>" + uniCode + "</uniCode>\n" +
                "         <userUnicode>W0074487@SD</userUnicode>\n" +
                "         <sendTime>1478163741086</sendTime>\n" +
                "      </item>\n" +
                "   </items>\n" +
                "</synXml>\n" +
                "</soapenv:Body>\n" +
                "</soapenv:Envelope>\n";

        return  oadaibangStr;

    }
    /**
     * 获得请求报文
     * 1、建立http连接
     * 2、发送http请求
     * 3、返回回应报文
     *
     * @param msg 发送报文
     * @return
     */
    private String getSoapResponseData(String msg) {
        if (StringUtils.isEmpty(msg)) {
            LOGGER.warn("【MSS接口，发送请求的入参报文为空，请检查！】");
            return "";
        }

//        String response = restTemplate.getForObject("http://10.128.88.2:9090/oip/proxy?bizsectype=009", String.class, msg);
//        System.out.println("返回的"+response);
//        return response;

        String bak = "";
        BufferedReader br = null;
        String soapResponseData = "";
        try {
            //msg是拼装好的报文。
            HttpClient httpClient = new HttpClient();
            //这是临时地址
            PostMethod postMethod = new PostMethod("http://10.128.88.2:9090/oip/proxy?bizsectype=009");
            //拼装报文
            // 然后把Soap请求数据添加到PostMethod中
            byte[] b = msg.getBytes("utf-8");
            InputStream is = new ByteArrayInputStream(b, 0, b.length);
            org.apache.commons.httpclient.methods.RequestEntity re = new InputStreamRequestEntity(is, b.length,
                    "text/xml; charset=utf-8");
            postMethod.setRequestEntity(re);

            // 最后生成一个HttpClient对象，并发出postMethod请求
            LOGGER.info("发送请求报文");
            postMethod.setRequestHeader("SOAPAction", "");
            int statusCode = httpClient.executeMethod(postMethod);
            LOGGER.info("获取MSS接口的返回报文");
            if (statusCode == 200) {
                LOGGER.info("报文获取成功");
                InputStream inputStream  = postMethod.getResponseBodyAsStream();
                br = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String str= "";
                while((str = br.readLine()) != null){
                    stringBuffer.append(str);
                }
                soapResponseData = stringBuffer.toString();
                soapResponseData = soapResponseData.replace("&lt;", "<");
                soapResponseData = soapResponseData.replace("&gt;", ">");
                soapResponseData = soapResponseData.replace("&quot;", "\"");
                soapResponseData = soapResponseData.replace("&nbsp;", " ");
                soapResponseData = soapResponseData.replace("&amp;", "&");
                soapResponseData = soapResponseData.replace("&#60;", "<");
                soapResponseData = soapResponseData.replace("&#62;", ">");
                LOGGER.info("获得的返回报文是：" + soapResponseData);
                return soapResponseData;
            }
        } catch (IOException e) {
            LOGGER.info("发送http请求发生异常：" + ExceptionTools.getStackTraceAsString(e));
        }finally {
            try {
                if(br!=null) {
                    br.close();
                }
            } catch (IOException e) {
                return soapResponseData;
            }
        }
        return bak;
    }

}
