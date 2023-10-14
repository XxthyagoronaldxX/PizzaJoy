package com.danthy.pizzafun.domain.data;

import com.danthy.pizzafun.app.utils.JaxbUtil;
import com.danthy.pizzafun.domain.models.RoomModel;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Queue;

@XmlRootElement(name = "roomSavesXmlData")
@NoArgsConstructor
public class RoomSavesXmlData {
    private static  final String FILE_NAME = "RoomSavesXmlData";

    @XmlElementWrapper(name = "roomModelList")
    @XmlElement(name = "roomModel")
    private Queue<RoomModel> roomModelList;

    public static RoomSavesXmlData getFromXml() {
        return JaxbUtil.unmarshaller(RoomSavesXmlData.class, FILE_NAME);
    }

    public void save(RoomModel model) {
        if (roomModelList.size() == 3) {
            roomModelList.poll();
            roomModelList.offer(model);
        } else {
            roomModelList.offer(model);
        }
    }

    public void setToXml() {
        JaxbUtil.marshaller(this, FILE_NAME);
    }
}
