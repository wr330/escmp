package ws;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.buaa.out.domain.Technicaldocument;
import com.buaa.out.technicaldocument.dao.TechnicaldocumentDao;
@Endpoint
public class SupportResourceEndpoint{
 
	@Resource
	private TechnicaldocumentDao technicaldocumentDao;
	
    @PayloadRoot(localPart="SupportResourceRequest",namespace="http://www.bstek.com/ws")
	//@PayloadRoot(localPart="SupportResourceRequest")
    public @ResponsePayload SupportResourceResponse getUsers(@RequestPayload SupportResourceRequest request) throws Exception{
    	List<Technicaldocument> technicaldocuments = new ArrayList<Technicaldocument>();
        SupportResourceResponse response=new SupportResourceResponse();
        Map<String, Object> parameter = new HashMap<String,Object>();
        int sendStatus = request.getSendStatus();
        parameter.put("status", sendStatus);
        technicaldocuments = (List<Technicaldocument>) technicaldocumentDao.queryDocument(parameter);
        for(Technicaldocument detail : technicaldocuments ){
        	detail.setSendStatus(1);
        	technicaldocumentDao.updateData(detail);
        }
        response.setTechnicaldocuments(technicaldocuments);
        return response;
    }
	/*@PayloadRoot(localPart="SupportResourceRequest",namespace="http://www.bstek.com/ws")
    public @ResponsePayload SupportResourceResponse getUsers(@RequestPayload Element request) throws Exception{
        String statusStr = request.element("sendStatus").getText();
        int sendStatus = Integer.parseInt(statusStr);
        String aircrafttype = request.element("aircrafttype").getText();
        Map<String, Object> parameter = new HashMap<String,Object>();
        List<Technicaldocument> technicaldocuments = new ArrayList<Technicaldocument>();
        SupportResourceResponse response = new SupportResourceResponse();
        parameter.put("status", sendStatus);
        parameter.put("aircrafttype", aircrafttype);
        technicaldocuments = (List<Technicaldocument>) technicaldocumentDao.queryDocument(parameter);
        response.setTechnicaldocuments(technicaldocuments);
        return response;
	}*/
}