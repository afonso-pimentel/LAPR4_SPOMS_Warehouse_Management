package strategies.strategies;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.AgvDataResponse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Simulation {

    public static int iteration = 0;

    public static List<AgvDataResponse> agvDataList;

    public static String agvData(String id){
            return getSimulatedAgv(id);
    }

    public static String getSimulatedAgv(String id){
        String jsonInString ="";
        ObjectMapper mapper = new ObjectMapper();
        AgvDataResponse data = agvDataList.get(iteration);
        data.id = Long.parseLong(id);
        iteration ++;
        if (iteration >= agvDataList.size()){
            iteration = 0;
        }

        try {
            jsonInString = mapper.writeValueAsString(data);
            System.out.println(jsonInString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonInString;

    }


    public static void loadList(InputStream inputStream){
        List<AgvDataResponse> agvData = new ArrayList<>();
        try {
            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();

            // convert JSON array to list of agvs
            StringBuilder textBuilder = new StringBuilder();
            try (Reader reader = new BufferedReader(new InputStreamReader
                    (inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
                int c = 0;
                while ((c = reader.read()) != -1) {
                    textBuilder.append((char) c);
                }
            }

            agvData = Arrays.asList(mapper.readValue(textBuilder.toString(), AgvDataResponse[].class));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        agvDataList = agvData;
    }









}
