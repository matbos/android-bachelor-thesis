package pl.mbos.bachelor_thesis.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pl.mbos.bachelor_thesis.objs.ISimpleData;
import pl.mbos.bachelor_thesis.objs.PowerEEG;

public class DataFormatter {
	
	public static String formatSimpleData(String heading, List<ISimpleData> data) {
		if(data == null) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		builder.append(" <table class=\"bordered\">" + 
				"		<thead>" + 
				"			<tr>" + 
				"				<td colspan=\"3\">");
		builder.append(heading);
		builder.append("</td>" + 
				"			</tr>" + 
				"			<tr>" + 
				"				<td>User ID</td>" + 
				"				<td>Value</td>" + 
				"				<td>Collection time stamp</td>" + 
				"			</tr>" + 
				"		</thead>" + 
				"		<tbody>");
		for(ISimpleData d : data) {
			builder.append("<tr><td>");
			builder.append(d.getUserId());
			builder.append("</td><td>");
			builder.append(d.getValue());
			builder.append("</td><td>");
			builder.append(formatDate(d.getCollectionDate()));
			builder.append("</td></tr>");
		}
		builder.append("</tbody>" + 
				"	</table>");
		return builder.toString();
	}
	
	public static String formatComplexData(String heading, List<PowerEEG> data) {
		if(data == null) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		builder.append(" <table class=\"bordered\">" + 
				"		<thead>" + 
				"			<tr>" + 
				"				<td colspan=\"10\">");
		builder.append(heading);
		builder.append("</td>" + 
				"			</tr>" + 
				"			<tr>" + 
				"				<td>User ID</td>" + 
				"				<td>LowAlpha</td>" +
				"				<td>HighAlpha</td>" +
				"				<td>LowBeta</td>" +
				"				<td>HighBeta</td>" +
				"				<td>LowGamma</td>" +
				"				<td>MidGamma</td>" +
				"				<td>Theta</td>" +
				"				<td>Delta</td>" +
				"				<td>Collection time stamp</td>" +
				"			</tr>" + 
				"		</thead>" + 
				"		<tbody>");
		for(PowerEEG d : data) {
			builder.append("<tr><td>");
			builder.append(d.getUserId());
			builder.append("</td><td>");
			builder.append(d.getLowAlpha());
			builder.append("</td><td>");
			builder.append(d.getHighAlpha());
			builder.append("</td><td>");
			builder.append(d.getLowBeta());
			builder.append("</td><td>");
			builder.append(d.getHighBeta());
			builder.append("</td><td>");
			builder.append(d.getLowGamma());
			builder.append("</td><td>");
			builder.append(d.getMidGamma());
			builder.append("</td><td>");
			builder.append(d.getTheta());
			builder.append("</td><td>");
			builder.append(d.getDelta());
			builder.append("</td><td>");
			builder.append(formatDate(d.getCollectionDate()));
			builder.append("</td></tr>");
		}
		builder.append("</tbody>" + 
				"	</table>");
		return builder.toString();
	}
	
	private static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS d-M-yyyy");
		return sdf.format(date);
	}
	
}
