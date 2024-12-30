package com.example.sladkovsem;
import com.google.gson.GsonBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
public class HelloController implements Initializable {
    @FXML
    private Label DEG;
    @FXML
    private Label F_L;
    @FXML
    private Label HY;
    @FXML
    private Label PR;
    @FXML
    private Label SPEED;
    @FXML
    private Label TEMP;
    @FXML
    private Label TEMPmax;
    @FXML
    private Label TEMPmin;
    @FXML
    private ComboBox<String> mainBox;
    @FXML
    private ResourceBundle resourceBundle;
    @FXML
    private URL url;
    String []items ={"","","",""};

    public List<String> SEARCH(String ciTY){
        Gson gson = new Gson();
        List<CITY> cities = null;
        try {
            String apiKey = "28349b697c86b2e25d3beea6beff59cd";
            String city = ciTY;
            String apiUrl = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=4&appid=" + apiKey + "&units=metric";
            System.out.println(ciTY);
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            Type citieslistTYPE = new TypeToken<List<CITY>>(){}.getType();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            cities = gson.fromJson(reader,citieslistTYPE);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return cities.stream().map(CITY::getName).collect(Collectors.toList());
    }

@FXML
void loadItems(ActionEvent event){}
    @Override
    public void initialize(URL url2, ResourceBundle resourceBundle) {
        mainBox.getItems().addAll(items);
        mainBox.setOnAction( actionEvent -> {

            String data =  mainBox.getSelectionModel().getSelectedItem().toString();
            TEMP.setText(data);
            Gson gson = new Gson();
            List<CITY> cities1 = null;
            try {
                String apiKey = "28349b697c86b2e25d3beea6beff59cd";
                String apiUrl = "http://api.openweathermap.org/geo/1.0/direct?q=" + data + "&limit=1&appid=" + apiKey + "&units=metric";
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                Type citieslistTYPE = new TypeToken<List<CITY>>(){}.getType();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                cities1 = gson.fromJson(reader,citieslistTYPE);
                reader.close();
                List<Double> LAT = cities1.stream().map(CITY::getLAT).collect(Collectors.toList());
                List<Double> LON = cities1.stream().map(CITY::getLON).collect(Collectors.toList());
                System.out.println(LAT);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        mainBox.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String old, String newv) {
                List<String> list = SEARCH(newv);
                System.out.println(list);
                mainBox.getItems().clear();
                mainBox.getItems().addAll(list);
            }

        });


    }

}

class LocalName{  String tag; String cityname;
}
class CITY{
     String name;
     List<LocalName> local_name;
     double lat;
     double lon;
     String country;
     String state;
    public CITY(String name, List<LocalName> local_name, double lat, double lon, String country, String state){
        this.name = name ; this.local_name= local_name; this.lat=lat; this.lon=lon; this.country=country; this.state=state;
    }
    public String getName() {return name;}
    public double getLAT() {return lat;}
    public double getLON() {return lon;}
}
