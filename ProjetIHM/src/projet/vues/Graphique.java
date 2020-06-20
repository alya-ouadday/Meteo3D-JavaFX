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
	  
	 
	 public Graphique(Zone zone) {
	 HashMap<Integer, Float> anomalies = zone.getAnomalies();
	 final NumberAxis xAxis = new NumberAxis(1880, 2020, 25);
     final NumberAxis yAxis = new NumberAxis();
     linechart = new LineChart(xAxis,yAxis);
     xAxis.setLabel("Ann�e");
     yAxis.setLabel("Temp�rature (en C�)");
     linechart.setTitle("Evolution des anomalies de temp�rature sur la zone " + zone + " entre 1880 et 2020");
     XYChart.Series serie= new XYChart.Series();
     serie.setName("anomalie de temp�rature");
	 
	 for (Map.Entry<Integer, Float> entry : anomalies.entrySet()) {
		 if(!entry.getValue().isNaN()) {
			 serie.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
		 }
		 
		 else {
			 serie.getData().add(new XYChart.Data(entry.getKey(), 0));
		 }
	 }
	 linechart.getData().add(serie);
	}
	
	 
	 public LineChart getLineChart(){
		 return linechart; 
	 }
	
	
}
