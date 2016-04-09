package ws;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
@Endpoint
public class UserServiceEndpoint{
 
    @PayloadRoot(localPart="UserRequest",namespace="http://www.bstek.com/ws")
    public @ResponsePayload UserResponse getUsers(@RequestPayload UserRequest request){
        String targetCompanyId=request.getTest();
        UserResponse response=new UserResponse();
     

        //response.setUsers();
        return response;
    }
}