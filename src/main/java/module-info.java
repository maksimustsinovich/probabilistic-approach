module by.ustsinovich.probabilisticapproach {
    requires javafx.controls;
    requires javafx.fxml;


    opens by.ustsinovich.probabilisticapproach to javafx.fxml;
    exports by.ustsinovich.probabilisticapproach;
    exports by.ustsinovich.probabilisticapproach.controller;
    opens by.ustsinovich.probabilisticapproach.controller to javafx.fxml;
}