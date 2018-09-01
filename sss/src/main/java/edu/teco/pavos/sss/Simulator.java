package edu.teco.pavos.sss;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Simulator of the simulator
 * @author Jean Baumgarten
 */
public class Simulator {
	
	private String url;
	private ThreadInfo info;
	private HashMap<String, JSONObject> locations;
	private int value = 20;

	/**
	 * Default Constructor
	 * @param info what to simulate
	 * @param url to send data to
	 */
	public Simulator(ThreadInfo info, String url) {
		
		this.url = url;
		this.info = info;
		this.locations = new HashMap<String, JSONObject>();
		int i = 0;
		for (double la = 0; la < 2; la += 0.2) {
			for (double lo = 0; lo < 4; lo += 0.4) {
				String id = "" + this.info.getLat() + "-" + this.info.getLon() + "/loc" + i++;
				JSONObject geo = new JSONObject();
				geo.put("type", "Point");
				JSONArray arr = new JSONArray();
				arr.add(this.info.getLon() + lo);
				arr.add(this.info.getLat() + la);
				geo.put("coordinates", arr);
				this.locations.put(id, geo);
			}
		}
		this.start();
		
	}
	
	private void start() {
		
		for (String id : this.locations.keySet()) {
			
			JSONObject objL = new JSONObject();
			objL.put("@iot.id", "Simulation/Location/" + id);
			objL.put("name", "L" + id);
			objL.put("description", "A Location");
			objL.put("encodingType", "application/vnd.geo+json");
			objL.put("location", this.locations.get(id));
		    String loc = objL.toJSONString();
		    FrostSender.sendToFrostServer(this.url, loc);
		    
		    JSONObject objT = new JSONObject();
		    objT.put("@iot.id", "Simulation/Thing/" + id);
		    objT.put("name", "T" + id);
		    objT.put("description", "A Thing");
		    JSONObject location = new JSONObject();
		    location.put("@iot.id", "Simulation/Thing/" + id);
		    JSONArray arr = new JSONArray();
		    arr.add(location);
	        objT.put("Locations", arr);
		    String t = objT.toJSONString();
		    FrostSender.sendToFrostServer(this.url, t);
		    
		    JSONObject obj = new JSONObject();
			obj.put("@iot.id", "Simulation/Datastream/" + id);
	        obj.put("name", "D" + id);
	        obj.put("description", "A Datastream");
	        obj.put("observationType", "Temp");
			JSONObject uom = new JSONObject();
			uom.put("name", "Degree Celsius");
			uom.put("symbol", "degC");
			uom.put("definition", "http://www.qudt.org/qudt/owl/1.0.0/unit/Instances.html#DegreeCelsius");
	        obj.put("unitOfMeasurement", uom);
	        JSONObject thing = new JSONObject();
	        thing.put("@iot.id", "Simulation/Thing/" + id);
	        obj.put("Thing", thing);
	        JSONObject observerProperty = new JSONObject();
	        observerProperty.put("@iot.id", "Simulation/ObservedProperty");
	        obj.put("ObservedProperty", observerProperty);
	        JSONObject sensor = new JSONObject();
	        sensor.put("@iot.id", "Simulation/Sensor");
	        obj.put("Sensor", sensor);
		    String d = obj.toJSONString();
		    FrostSender.sendToFrostServer(this.url, d);
	        
		}
		
		while (true) {
			
			this.value += this.getRandom();
			
			for (String id : this.locations.keySet()) {
				
				DateTime time = DateTime.now();
				DateTimeFormatter fmt = ISODateTimeFormat.dateTimeNoMillis();
				String t = fmt.print(time);
				
				JSONObject obj = new JSONObject();
		        obj.put("phenomenonTime", t);
		        obj.put("result", this.value);
		        obj.put("resultTime", t);
		        JSONObject dataStream = new JSONObject();
		        dataStream.put("@iot.id", "Simulation/Datastream/" + id);
		        obj.put("Datastream", dataStream);
		        String json = obj.toJSONString();
		        FrostSender.sendToFrostServer(this.url, json);
				
			}
			
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				System.out.println(e.getLocalizedMessage());
			}
			
		}
		
	}
	
	private int getRandom() {
		double r = Math.random();
		if (r < 0.4) {
			return -1;
		} else if (r > 0.7) {
			return 1;
		} else {
			return 0;
		}
	}
	
	/**
	 * preparation for the Data
	 * @param url to send data to
	 */
	public static void prepareAll(String url) {
		
		JSONObject objOP = new JSONObject();
		objOP.put("@iot.id", "Simulation/ObservedProperty");
		objOP.put("name", "TemperatureSimulation");
		objOP.put("description", "TempS");
		objOP.put("definition", "Just for testing purposes");
        String op = objOP.toJSONString();
        FrostSender.sendToFrostServer(url, op);
        
        JSONObject objS = new JSONObject();
        objS.put("@iot.id", "Simulation/Sensor");
        objS.put("name", "Simulated Sensor");
        objS.put("description", "Simlulated temperature sensor");
        objS.put("encodingType", "application/pdf");
        objS.put("metadata", "https://cdn-shop.adafruit.com/datasheets/DHT22.pdf");
        String s = objS.toJSONString();
        FrostSender.sendToFrostServer(url, s);
		
	}

}
