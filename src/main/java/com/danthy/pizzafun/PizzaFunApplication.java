package com.danthy.pizzafun;

import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.logic.GetIt;
import javafx.application.Application;
import javafx.stage.Stage;

public class PizzaFunApplication extends Application {
    @Override
    public void start(Stage stage)  {
        stage.setTitle("PizzaFun");
        ApplicationProperties.init();
        GetItSetup.setup(stage);
        GetIt.getInstance().find(ScreenManager.class).start();
    }

    public static void main(String[] args) {
        launch(args);
        //RoomModel roomModel = JaxbUtil.unmarshaller(RoomModel.class, "FirstGameRoomConfig");
        //ItemXmlData.getFromXml().getItemModelList().forEach(System.out::println);
        //PizzaXmlData.getFromXml().getPizzaModelList().forEach(System.out::println);

        // PizzaXmlData.getFromXml().getPizzaModelList().forEach(System.out::println);

        // RoomModel test =  PostConstruct.genRoomModel("test");
        // RoomXmlAdapter roomXmlAdapter = new RoomXmlAdapter(test);

        //JaxbUtil.marshaller(roomXmlAdapter, "RoomXmlAdapter");
        //RoomXmlAdapter test2 = JaxbUtil.unmarshaller(RoomXmlAdapter.class, "FirstGameRoomConfig");
        //System.out.println(test2.getName());
        //ItemXmlData.getFromXml().getItemModel().forEach(System.out::println);
    }
}