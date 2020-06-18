package projet.vues;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.MeshView;
import projet.data.Zone;

public class Graphique {
	
	final LineChart linechart; 
	  
	 
	 public Graphique(HashMap<Integer, Float> anomalies) {
	 final NumberAxis xAxis = new NumberAxis(1880, 2020, 25);
     final NumberAxis yAxis = new NumberAxis();
     linechart = new LineChart(xAxis,yAxis);
     xAxis.setLabel("Année");
     yAxis.setLabel("Température");
     linechart.setTitle("Anomalies de température");
     XYChart.Series serie= new XYChart.Series();
     serie.setName("anomalie");
	 
	 for (Map.Entry<Integer, Float> entry : anomalies.entrySet()) {
		 if(entry.getValue() < Float.MAX_VALUE) {
			 serie.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
		 System.out.println(entry.getKey()+ ",test" + entry.getValue());
		 }
		 
		 else {
			 serie.getData().add(new XYChart.Data(entry.getKey(), 0));
		 }
	 }
	 System.out.println(serie.getData());
	 linechart.getData().add(serie);


	 System.out.println(linechart.getData());
    
	}
	
	 public void updateValues(HashMap<Integer, Float> anomalies) {
		 linechart.getData().clear();
		 XYChart.Series series= new XYChart.Series();
		 series.setName("anomalies");
		 for (Map.Entry<Integer, Float> entry : anomalies.entrySet()) {
			 series.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
			 System.out.println(entry.getKey()+ ",test" + entry.getValue());
			 
	    
		 }
		 linechart.getData().add(series);
	 }
	 
	 public void setValues(HashMap<Integer, Float> anomalies) {
		 				linechart.getData().clear();
			            XYChart.Series serie= new XYChart.Series();
			            serie.setName("anomalie");
						 
						 for (Map.Entry<Integer, Float> entry : anomalies.entrySet()) {
							 if(entry.getValue() < Float.MAX_VALUE) {
								 serie.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
							 System.out.println(entry.getKey()+ ",test" + entry.getValue());
							 }
							 
							 else {
								 serie.getData().add(new XYChart.Data(entry.getKey(), 0));
							 }
						 }
						 System.out.println(serie.getData());
						linechart.getData().add(serie);
					
				
						 System.out.println(linechart.getData());
	 }
	 
	 
	 public LineChart getLineChart(){
		 return linechart; 
	 }
	
	
}
