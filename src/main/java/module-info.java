module com.example.devprojectcode {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;


    opens com.example.devprojectcode to javafx.fxml;
    exports com.example.devprojectcode;
    exports com.example.devprojectcode.UIControllers;
    opens com.example.devprojectcode.UIControllers to javafx.fxml;
    exports com.example.devprojectcode.Pieces;
    opens com.example.devprojectcode.Pieces to javafx.fxml, com.google.gson;
    opens com.example.devprojectcode.Enums to com.google.gson;
}