package ws;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.buaa.out.domain.Technicaldocument;
@XmlRootElement(name="UserResponse",namespace="http://www.bstek.com/ws")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserResponse {
    private List<Technicaldocument> users;
    public List<Technicaldocument> getUsers() {
        return users;
    }
    public void setUsers(List<Technicaldocument> users) {
        this.users = users;
    }
}