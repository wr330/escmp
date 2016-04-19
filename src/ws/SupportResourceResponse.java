package ws;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.buaa.out.domain.Technicaldocument;
@XmlRootElement(name="SupportResourceResponse",namespace="http://www.bstek.com/ws")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupportResourceResponse {
    private List<Technicaldocument> technicaldocuments;
	public List<Technicaldocument> getTechnicaldocuments() {
		return technicaldocuments;
	}
	public void setTechnicaldocuments(List<Technicaldocument> technicaldocuments) {
		this.technicaldocuments = technicaldocuments;
	}
    
}