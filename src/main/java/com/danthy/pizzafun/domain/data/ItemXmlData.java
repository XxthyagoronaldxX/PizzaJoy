package com.danthy.pizzafun.domain.data;

import com.danthy.pizzafun.app.utils.JaxbUtil;
import com.danthy.pizzafun.domain.models.ItemModel;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@XmlRootElement(name = "itemXmlData")
@Getter
@Setter
@NoArgsConstructor
public class ItemXmlData {
    @XmlTransient
    private static String FILE_NAME = "ItemXmlData";

    @XmlElementWrapper(name = "itemModelList")
    @XmlElement(name = "itemModel")
    private List<ItemModel> itemModelList;

    public static ItemXmlData getFromXml() {
        return JaxbUtil.unmarshaller(ItemXmlData.class, FILE_NAME);
    }
}
