package eapli.base.warehousemanagement.application;

import eapli.base.warehousemanagement.domain.AgvDock;
import eapli.base.warehousemanagement.domain.Aisle;
import eapli.base.warehousemanagement.domain.Position;
import eapli.base.warehousemanagement.domain.Row;
import eapli.base.warehousemanagement.domain.Warehouse;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ImportWarehousePlantService {

//     private final String filePath = "E:\\ISEP\\LAPR4\\repo\\lei21_22_s4_2na_02\\projects\\eapli.base\\base.core\\src\\main\\java\\eapli\\base\\warehousemanagement\\resources\\warehouse1.json";
    // private final String filePath = System.getProperty("user.dir") + "/projects/eapli.base/base.core/src/main/java/eapli/base/warehousemanagement/resources/warehouse1.json";
    private final String fileName = "warehouse1.json";



    /**
     * {@inheritdoc}
     */
    public Warehouse importWarehousePlant() {
        Warehouse warehouse = new Warehouse();
        try {
            String fileExt = this.getFilePathExtension(this.fileName);

            switch (fileExt) {
                case ".json":
                warehouse = this.importJson();

                    break;
                default:
                    System.out.println("Invalid file extension, use .json files");
                    warehouse = null;
                    break;
            }
        } catch (IntegrityViolationException | ConcurrencyException | IOException | IllegalArgumentException | ParseException ex) {
            System.out.println("Failed to import warehouse plant " + ex);
            warehouse = null;
        }
        return warehouse;
    }

    /**
     * get the extension of the file
     *
     * @param filePath
     * @return
     */
    public String getFilePathExtension(String filePath) {

        String ext = "";
        try {
            ext = filePath.substring(filePath.lastIndexOf("."));

        } catch (Exception e) {
            ext = "";
        }

        return ext;
    }

    public Warehouse importJson() throws IOException, ParseException, NullPointerException {

        ClassLoader classLoader = getClass().getClassLoader();
//        try (InputStream inputStream = classLoader.getResourceAsStream(fileName);
//             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
//             BufferedReader reader = new BufferedReader(streamReader)) { String line; while ((line = reader.readLine()) != null) { System.out.println(line); } }
//        catch (IOException e) { e.printStackTrace(); }



        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("warehouse1.json");
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }




        Object obj = new JSONParser().parse(textBuilder.toString());

        JSONObject jsonWarehouse = (JSONObject) obj;

        

        // get the warehouse

        String wDesc = (String) jsonWarehouse.get("Warehouse");
        int wLenght = (int) (long) jsonWarehouse.get("Length");
        int wWidth = (int) (long) jsonWarehouse.get("Width");
        int wSquare = (int) (long) jsonWarehouse.get("Square");
        String unit = (String) jsonWarehouse.get("Unit");
        ArrayList<Aisle> aisles = new ArrayList<>();
        ArrayList<AgvDock> agvDocks = new ArrayList<>();


        Warehouse warehouse = new Warehouse(
            wDesc,
            wLenght,
            wWidth,
            wSquare,
            unit,
            aisles,
            agvDocks
            );
        

        JSONArray jrAisles = (JSONArray) jsonWarehouse.get("Aisles");

        for (int i = 0; i < jrAisles.size(); i++) {

            JSONObject joAisle = (JSONObject) jrAisles.get(i);
            // get aisle

            int aisleId = (int) (long) joAisle.get("Id");

            Position aisleBegin = new Position(
                (int) (long) ((JSONObject) joAisle.get("begin")).get("lsquare"),
                (int) (long) ((JSONObject) joAisle.get("begin")).get("wsquare"));

            Position aisleEnd = new Position(
                    (int) (long) ((JSONObject) joAisle.get("end")).get("lsquare"),
                    (int) (long) ((JSONObject) joAisle.get("end")).get("wsquare"));

            Position aisleDepth = new Position(
                    (int) (long) ((JSONObject) joAisle.get("depth")).get("lsquare"),
                    (int) (long) ((JSONObject) joAisle.get("depth")).get("wsquare"));

            String aisleAccessib = (String) joAisle.get("accessibility");

            ArrayList<Row> rows = new ArrayList<>();


            Aisle aisle = new Aisle(
                aisleId,
                aisleBegin,
                aisleEnd,
                aisleDepth,
                aisleAccessib,
                rows
            );


            JSONArray jrRows = (JSONArray) joAisle.get("rows");

            for (int j = 0; j < jrRows.size(); j++) {

                JSONObject joRow = (JSONObject) jrRows.get(j);
                // get row

                int rId = (int) (long) joRow.get("Id");

                Position rowBegin = new Position(
                        (int) (long) ((JSONObject) joRow.get("begin")).get("lsquare"),
                        (int) (long) ((JSONObject) joRow.get("begin")).get("wsquare"));

                Position rowEnd = new Position(
                        (int) (long) ((JSONObject) joRow.get("end")).get("lsquare"),
                        (int) (long) ((JSONObject) joRow.get("end")).get("wsquare"));

                int rShelves = (int) (long) joRow.get("shelves");


                Row row = new Row(
                    rId,
                    rowBegin,
                    rowEnd,
                    rShelves
                );


                aisle.addRow(row);

            }

            warehouse.addAisle(aisle);
        }

        JSONArray jrAGVDocks = (JSONArray) jsonWarehouse.get("AGVDocks");

        for (int k = 0; k < jrAGVDocks.size(); k++) {

            JSONObject joAgvDock = (JSONObject) jrAGVDocks.get(k);
            // get agv docks

            String agId = (String) joAgvDock.get("Id");

            Position agvDockBegin = new Position(
                    (int) (long) ((JSONObject) joAgvDock.get("begin")).get("lsquare"),
                    (int) (long) ((JSONObject) joAgvDock.get("begin")).get("wsquare"));

            Position agvDockEnd = new Position(
                    (int) (long) ((JSONObject) joAgvDock.get("end")).get("lsquare"),
                    (int) (long) ((JSONObject) joAgvDock.get("end")).get("wsquare"));

            Position agvDockDepth = new Position(
                    (int) (long) ((JSONObject) joAgvDock.get("depth")).get("lsquare"),
                    (int) (long) ((JSONObject) joAgvDock.get("depth")).get("wsquare"));

            String agAccessibi = (String) joAgvDock.get("accessibility");


            AgvDock agvDock = new AgvDock(
                agId,
                agvDockBegin,
                agvDockEnd,
                agvDockDepth,
                agAccessibi
            );



            warehouse.addAgvDock(agvDock);

        }

        // System.out.println(warehouse.toString());

        return warehouse;
    }

}
