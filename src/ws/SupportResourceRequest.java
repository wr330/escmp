package ws;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="SupportResourceRequest",namespace="http://www.bstek.com/ws")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupportResourceRequest {
   
    private int sendStatus;
    private String aircrafttype;

	public int getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}
	
	public String getAircrafttype() {
		return aircrafttype;
	}
	public void setAircrafttype(String aircrafttype) {
		this.aircrafttype = aircrafttype;
	}

}