module com.example.perfectnumber {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.perfectnumber to javafx.fxml;
    exports com.example.perfectnumber;
}