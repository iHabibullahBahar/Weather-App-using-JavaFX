/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package weatherchecker;
import org.json.*;
import java.text.*;
import java.util.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Habib
 */
public class InterfaceController implements Initializable {
    
    @FXML
    private Label airPressureLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label dateNtimeLabel;

    @FXML
    private Label dayLabel;

    @FXML
    private Label feelsLikeLabel;

    @FXML
    private Label humidityLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private TextField searchCityBar;

    @FXML
    private Label sunRiseLabel;

    @FXML
    private Label sunSetLabel;

    @FXML
    private Label tempLabel;

    @FXML
    private Label weatherConditionLabel;

    @FXML
    private Label windSpeedLabel;
    
    @FXML 
    private Label warningLabel;
    
    //API Key 
    static String APPID = "b702a4286daab5d7af2a7409d0a68275";
    //City Name
    static String city="dhaka";
    boolean validCity=false;
    
    //All the data get from API Call;
    static String cityName="";
    static String countryName ="";
    static String time="";
    static String weatherCondition="";
    static String tempInC="";
    static String tempFeels="";
    static double pressure=0;
    static int humidity=0;
    static String sunRise="";
    static String sunSet="";
    static double windSpeed =0;
    static String date = "";
    static String day ="";
    
    /**
     * Initializes the controller class.
     */
        public void initialize(URL url, ResourceBundle rb) {
        getWeatherReport();
        
    }
   //
    public void searchCity(){
        city = searchCityBar.getText().toLowerCase(); 
        try {
                URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+APPID);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();

                //If the city doesn't exist, this line will trigger an exception.
                InputStreamReader inputStreamReader=new InputStreamReader(httpURLConnection.getInputStream());
                     validCity =true;
            }	 
        catch (Exception e) {
                validCity = false;
        }
        if(validCity)
        {
            getWeatherReport();
            
        }
        else
        {
            warningLabel.setText("Please enter a valid city.");
        }
    }
    
    public void setData()
    {
        tempLabel.setText(tempInC);
        locationLabel.setText(cityName+", "+countryName);
        weatherConditionLabel.setText(weatherCondition);
        feelsLikeLabel.setText(tempFeels+" °C");
        humidityLabel.setText(Integer.toString(humidity)+"%");
        airPressureLabel.setText(Double.toString(pressure)+"");
        dateNtimeLabel.setText(time);
        windSpeedLabel.setText(Double.toString(windSpeed)+" kmh");
        sunRiseLabel.setText(sunRise);
        sunSetLabel.setText(sunSet);
        dateLabel.setText(date);
        dayLabel.setText(day);   
    }
    //This Action will be call after press the search button
    public void searchBtnClick(ActionEvent event){
       warningLabel.setText("");
       searchCity();
    }
    //This function will make capital to first letter of a word.
    public static String capitalize(String message) {
    	
    	//Find the spaces, capitalize what's after.
        char[] charArray = message.toCharArray();
        boolean foundSpace = true;
        for(int i = 0; i < charArray.length; i++) {
          if(Character.isLetter(charArray[i])) {
            if(foundSpace) {
              charArray[i] = Character.toUpperCase(charArray[i]);
              foundSpace = false;
            }
          }
          else 
            foundSpace = true;
        }
        //String.valueOf converts to a string.
       return String.valueOf(charArray);
      }
    public static String KtoF(double temp) {
    	
    	//Convert to string to be able to concat with print statements
        return String.format("%.2f",(temp - 273.15) * 9/5 + 32);
    }
    public static String KtoC(double temp) {
    	
    	//Convert to string to be able to concat with print statements
        return String.format("%.1f",(temp - 273.15));
    }
    public static String KtoCLow(double temp) {
    	
    	//Convert to string to be able to concat with print statements
        return String.format("%.2f",(temp - 273.15-2.3));
    }
    public static String KtoCHigh(double temp) {
    	
    	//Convert to string to be able to concat with print statements
        return String.format("%.2f",(temp - 273.15+2.5));
    }
    public static String getDateTime(long time, String f) throws InterruptedException {
    	
    		//Takes in milliseconds
            Date date = new Date(time);
            //The "f" parameter is to specify what we want from the time stamp.
            DateFormat format = new SimpleDateFormat(f);
            format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
            return format.format(date);
        }  
    public static double getWindSpeed(double windSpeed)
    {
        if(windSpeed == 0)
        {
            windSpeed = 1.9;
        }
        return windSpeed;
          
    }
    public  void getWeatherReport(){
        try {
            //https://api.openweathermap.org/data/2.5/weather?q=khulna&appid=b702a4286daab5d7af2a7409d0a68275
            //Pass in all the parameters since getCity calls getWeatherReport
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+APPID);
            //Create connections to the API
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            //Get the response JSON
            String line="";
            InputStreamReader inputStreamReader=new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            StringBuilder response=new StringBuilder();
            while ((line=bufferedReader.readLine())!=null){
                response.append(line);
            }
            bufferedReader.close();
            
            //Parse through the JSON 
            //JSONObject = {}, JSONArray = []
            JSONObject jsonObject=new JSONObject(response.toString());
                //var cityName =jsonObject.getString("name");
                cityName = capitalize(city);
                countryName = jsonObject.getJSONObject("sys").getString("country");
                time = getDateTime(System.currentTimeMillis() + (jsonObject.getInt("timezone")*1000), "hh:mm a z");
                date = getDateTime(System.currentTimeMillis() + (jsonObject.getInt("timezone")*1000), "dd MMMM yyyy");
                day = getDateTime(System.currentTimeMillis() + (jsonObject.getInt("timezone")*1000), "E ");
                weatherCondition = capitalize(jsonObject.getJSONArray("weather").getJSONObject(0).getString("description"));
                //var tempInF = KtoF(jsonObject.getJSONObject("main").getDouble("temp"));
                tempInC = KtoC(jsonObject.getJSONObject("main").getDouble("temp"));
                tempFeels = KtoC(jsonObject.getJSONObject("main").getDouble("feels_like"));
                //var tempLow = KtoCLow(jsonObject.getJSONObject("main").getDouble("temp"));
                //var tempHigh = KtoCHigh(jsonObject.getJSONObject("main").getDouble("temp"));
                pressure = (jsonObject.getJSONObject("main").getDouble("pressure"));
                humidity = jsonObject.getJSONObject("main").getInt("humidity");
                
                sunRise = getDateTime((jsonObject.getJSONObject("sys").getLong("sunrise")+jsonObject.getLong("timezone"))*1000, "hh:mm a");
                sunSet = getDateTime((jsonObject.getJSONObject("sys").getLong("sunset")+jsonObject.getLong("timezone"))*1000, "hh:mm a");
                windSpeed = getWindSpeed((jsonObject.getJSONObject("wind").getDouble("speed")));
            //Set it back to show the rest
            setData();
        }
        catch (Exception e){
            System.out.println("Error in Making Get Request");
        }
        
        //Boolean in parameter gives out different print statements weather or not the city they input was a real city.
        
    }
    
}
