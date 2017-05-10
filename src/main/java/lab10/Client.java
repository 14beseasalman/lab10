package lab10;

import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.lightcouch.CouchDbClient;
import org.lightcouch.Response;

import com.opencsv.CSVReader;

public class Client {
	static CouchDbClient dbClient ;
	public static void main(String[] args) throws Exception {
		System.out.println("WOOT");
		dbClient = new CouchDbClient(); 

		GeoLiteCity city = dbClient.find(GeoLiteCity.class);
		System.out.println(city);
		//loadDatainDB();

	}
	public static void saveToDB(GeoLiteCity city){
		Response response = dbClient.save(city);
		System.out.println(response);
	}
	public static void loadDatainDB() throws Exception {
		File csvFile = new File("/home/woot/workspace/lab10/GeoLiteCity-Location.csv");
        CSVReader reader = new CSVReader(new FileReader(csvFile));
        String [] nextLine;

        // skip the header rows
        reader.readNext();
        reader.readNext();

        List<String[]> data = reader.readAll();

        for (int i = 0; i < data.size() - 1; i++) {
            nextLine = data.get(i);

            GeoLiteCity city = new GeoLiteCity();
            city.setLocationID(Integer.parseInt(nextLine[0]));
            System.out.println(nextLine[0]);
            city.setCountry(nextLine[1]);
            city.setRegion(nextLine[2]);
            city.setCity(nextLine[3]);
            city.setPostalCode(nextLine[4]);
            city.setLatitude(Float.parseFloat(nextLine[5]));
            city.setLongitude(Float.parseFloat(nextLine[6]));
            String metroCode = nextLine[7];
            if (metroCode.isEmpty()) {
                city.setMetroCode(-1);
            } else {
                city.setMetroCode(Integer.parseInt(metroCode));
            }

            String areaCode = nextLine[8];
            if (areaCode.isEmpty()) {
                city.setAreaCode(-1);
            } else {
                city.setAreaCode(Integer.parseInt(areaCode));
            }
            saveToDB(city);
        }
	}
	}
