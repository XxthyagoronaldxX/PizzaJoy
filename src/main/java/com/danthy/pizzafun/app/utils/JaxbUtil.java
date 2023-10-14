package com.danthy.pizzafun.app.utils;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class JaxbUtil {
    public static <T> void marshaller(T xmlAdapter, String name) {
        try {
            JAXBContext jaxbContext = org.eclipse.persistence.jaxb.JAXBContext.newInstance(xmlAdapter.getClass());
            Marshaller marshaller =  jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(xmlAdapter, new File(PathUtil.getRootPath() + File.separator
                    + "resources" + File.separator
                    + "xml" + File.separator
                    + name + ".xml"
            ));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T unmarshaller(Class clazz, String name) {
        try {
            String path = PathUtil.getRootPath() + File.separator
                    + "resources" + File.separator
                    + "xml" + File.separator
                    + name + ".xml";

            JAXBContext jaxbContext = org.eclipse.persistence.jaxb.JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (T) unmarshaller.unmarshal(new File(path));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
