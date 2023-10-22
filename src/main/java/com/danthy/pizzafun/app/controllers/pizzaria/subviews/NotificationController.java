package com.danthy.pizzafun.app.controllers.pizzaria.subviews;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.ReactOn;
import com.danthy.pizzafun.app.events.mediator.NotifyEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class NotificationController implements IController {
    @FXML
    public ImageView notificationBackgroundImg;

    @FXML
    public Label notifyTitleLabel;

    @Override
    public void initComponents() {
        Rectangle clip = new Rectangle();
        clip.setArcWidth(10);
        clip.setArcHeight(10);

        clip.heightProperty().bind(notificationBackgroundImg.fitHeightProperty());
        clip.widthProperty().bind(notificationBackgroundImg.fitWidthProperty().subtract(15));

        clip.setStyle("-fx-border-width: 24px;-fx-border-color: black;-fx-border-radius: 10px;");
        notificationBackgroundImg.setClip(clip);
    }

    @ReactOn(NotifyEvent.class)
    public void reactOnNotifyEvent(IEvent event) {
        NotifyEvent notifyEvent = (NotifyEvent) event;

        notifyTitleLabel.setText(notifyEvent.notifyType().getMessage());
    }
}
