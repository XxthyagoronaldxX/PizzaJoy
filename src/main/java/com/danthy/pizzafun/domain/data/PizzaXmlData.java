package com.danthy.pizzafun.domain.data;

import com.danthy.pizzafun.app.utils.JaxbUtil;
import com.danthy.pizzafun.domain.models.PizzaModel;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@XmlRootElement(name = "pizzaXmlData")
@Getter
@Setter
@NoArgsConstructor
public class PizzaXmlData {
    @XmlTransient
    private static String FILE_NAME = "PizzaXmlData";

    @XmlElementWrapper(name = "pizzaModelList")
    @XmlElement(name = "pizzaModel")
    private List<PizzaModel> pizzaModelList;

    public static PizzaXmlData getFromXml() {
        return JaxbUtil.unmarshaller(PizzaXmlData.class, FILE_NAME);
    }
}
