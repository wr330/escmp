package ws;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="UserRequest",namespace="http://www.bstek.com/ws")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserRequest {
   
    private String test;

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}


     
}