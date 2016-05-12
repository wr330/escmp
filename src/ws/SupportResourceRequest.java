package ws;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="SupportResourceRequest",namespace="http://www.bstek.com/ws")
//@XmlRootElement(name="SupportResourceRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupportResourceRequest {
   
    private int sendStatus;
    
	public int getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}

}