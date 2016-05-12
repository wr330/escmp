package ws;
import com.bstek.bdf2.webservice.client.WebServiceClient;
import com.buaa.out.domain.Technicaldocument;
public class WebserviceInvokeTest {
    public static void main(String[] args) throws Exception{
        String uri="http://localhost:8083/escmp/dorado/webservice/supportResourceWebService";
         
        //设置Webservice客户端要调用的目标Webservice的地址
        WebServiceClient client=new WebServiceClient(uri);
         
        //设置要调用的目标Webservice的WS-Security认证所需要的username及password，以及是否对用户名及密码进行加密传输
        //client.setUsernameToken("zxzx", "@zxzx123456", true);
         
        /**
         * 设置要调用的目标Webservice的Http Basic认证所需要的username及password，
         * Http Basic认证当中所使用的密码为加密之前的密码，而非被加密之后的密码
         * */
        //client.setHttpAuthenticationCredentials("admin", "admin");
         
        SupportResourceRequest request= new SupportResourceRequest();
        request.setSendStatus(0);
         
        //设置在调用目标Webservice过程当中，需要将Javabean与XML进行相互序列化的Javabean类的class
        client.setMarshallerClasses(new Class[]{SupportResourceRequest.class,SupportResourceResponse.class});
         
        //发送调用请求并返回调用结果
        SupportResourceResponse response = (SupportResourceResponse)client.sendAndReceive(request);
         
        //输出返回结果
        for(Technicaldocument technicaldocument:response.getTechnicaldocuments()){
            System.out.println("documenttype:" + technicaldocument.getContenttype() + "  aircrafttype:" + technicaldocument.getAircrafttype());
        }
    }
}