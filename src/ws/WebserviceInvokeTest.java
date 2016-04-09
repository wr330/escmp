package ws;
import com.bstek.bdf2.webservice.client.WebServiceClient;
public class WebserviceInvokeTest {
    public static void main(String[] args) throws Exception{
        String uri="http://localhost:8080/iefds/dorado/webservice/requestUserWebService";
         
        //设置Webservice客户端要调用的目标Webservice的地址
        WebServiceClient client=new WebServiceClient(uri);
         
        //设置要调用的目标Webservice的WS-Security认证所需要的username及password，以及是否对用户名及密码进行加密传输
        //client.setUsernameToken("admin", "123123", true);
         
        /**
         * 设置要调用的目标Webservice的Http Basic认证所需要的username及password，
         * Http Basic认证当中所使用的密码为加密之前的密码，而非被加密之后的密码
         * */
        //client.setHttpAuthenticationCredentials("admin", "admin");
         
        UserRequest request= new UserRequest();
       // request.setTargetCompany("bstek");
       // request.setUserCount(2);
         
        //设置在调用目标Webservice过程当中，需要将Javabean与XML进行相互序列化的Javabean类的class
        client.setMarshallerClasses(new Class[]{UserRequest.class,UserResponse.class});
         
        //发送调用请求并返回调用结果
        UserResponse response=(UserResponse)client.sendAndReceive(request);
         
        //输出返回结果
        //for(User user:response.getUsers()){
        //    System.out.println("username:"+user.getUsername() +"  company:"+user.getCompanyId());
       // }
    }
}