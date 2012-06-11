package org.openlab.charts;

import jofc2.model.Chart 
import jofc2.model.elements.LineChart 

/**
 * Controller for testing the ofchart plugin
 * @author markus.list
 *
 */
public class DemoChartController {
	
	def demoChart = {
			render new Chart("DemoChart")
			.addElements(new LineChart()
			.addValues(9, 8, 7, 6, 5, 4, 3, 2, 1))
	}
}
