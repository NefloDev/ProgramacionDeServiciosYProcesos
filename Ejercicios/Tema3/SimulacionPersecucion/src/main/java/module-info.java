module org.example.simulacionpersecucion {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.simulacionpersecucion to javafx.fxml;
    exports org.example.simulacionpersecucion;
}