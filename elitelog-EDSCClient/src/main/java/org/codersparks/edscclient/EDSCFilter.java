package org.codersparks.edscclient;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.codersparks.elitelog.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)	
public class EDSCFilter {
	
	private static Logger logger = LoggerFactory.getLogger(EDSCFilter.class);

	/**
	 * The Filter class is designed to be built using the static methods
	 */
	private EDSCFilter() {
	}

	private int knownstatus = 0;

	private String systemname = null;

	private int cr = 5;

	private String date = null;

	private float[][] coordcube = null;

	private EDSCCoordSphere coordsphere = null;

	public int getKnownstatus() {
		return knownstatus;
	}

	public void setKnownstatus(int knownstatus) {
		this.knownstatus = knownstatus;
	}

	public String getSystemname() {
		return systemname;
	}

	public void setSystemname(String systemname) {
		this.systemname = systemname;
	}

	public int getCr() {
		return cr;
	}

	public void setCr(int cr) {
		this.cr = cr;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public float[][] getCoordcube() {
		return coordcube;
	}

	public void setCoordcube(float[][] coordcube) {
		this.coordcube = coordcube;
	}

	public EDSCCoordSphere getCoordsphere() {
		return coordsphere;
	}

	public void setCoordsphere(EDSCCoordSphere coordsphere) {
		this.coordsphere = coordsphere;
	}
	
	@Override
	public String toString() {
		try {
			return JSONUtils.convertToJson(this);
		} catch (Exception e) {
			logger.error("Cannot convert object to JSON string dropping back to default behaviour", e);
			return super.toString();
		}
	}

	public static class EDSCFilterBuilder {

		private EDSCFilter filter;
		private SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		/**
		 * Start constructing a Filter from scratch
		 */
		public EDSCFilterBuilder() {
			filter = new EDSCFilter();
		}

		/**
		 * User the supplied filter as a starting point to build a filter
		 * 
		 * @param filter
		 */
		public EDSCFilterBuilder(EDSCFilter filter) {
			this.filter = filter;
		}

		public EDSCFilterBuilder knownstatus(int knownstatus)
				throws FilterException {

			if (knownstatus < 0 || knownstatus > 2) {
				throw new FilterException(
						"Knownstatus must be 0, 1 or 2, supplied: "
								+ knownstatus);
			}
			this.filter.setKnownstatus(knownstatus);

			return this;
		}

		public EDSCFilterBuilder systemname(String systemname)
				throws FilterException {

			this.filter.setSystemname(systemname);

			return this;
		}

		public EDSCFilterBuilder cr(int cr) throws FilterException {

			if (cr < 0) {
				throw new FilterException(
						"cr (confidence rating) must be greater than or equal to zero, supplied: "
								+ cr);
			}

			this.filter.setCr(cr);

			return this;
		}

		public EDSCFilterBuilder date(String date) throws FilterException {
			this.filter.setDate(date);

			return this;
		}

		public EDSCFilterBuilder date(Date date) throws FilterException {

			String dateString = sdf.format(date);

			return this.date(dateString);

		}

		public EDSCFilterBuilder coordcube(float[][] coordcube) throws FilterException {

			boolean errorFound = false;
			StringBuilder errorBuilder = new StringBuilder("Errors found when setting coordcube:\n");

			if (coordcube.length != 3) {
				errorFound = true;
				errorBuilder
						.append("\t=>Coordcube must have min max for all 3 dimensions, only as entries for ")
						.append(coordcube.length).append(" dimentions");
			}

			char[] dimensions = { 'x', 'y', 'z' };

			for (int i = 0; i < 3; i++) {

				if (coordcube[i].length != 2) {
					errorFound = true;

					if (errorBuilder.length() > 0) {
						errorBuilder.append("\n");
					}

					errorBuilder
							.append("\t=>")
							.append(dimensions[i])
							.append(" does not have exactly a min and max value, length: ")
							.append(coordcube[i].length);

				}
			}
			
			if(errorFound) {
				throw new FilterException(errorBuilder.toString());
			}
			
			this.filter.setCoordcube(coordcube);
			
			return this;
		}
		
		public EDSCFilterBuilder coordsphere(float radius, float origX, float origY, float origZ) {
			this.filter.setCoordsphere(new EDSCCoordSphere(radius, origX, origY, origZ));
			return this;
		}
		
		public EDSCFilter build() {
			return this.filter;
		}
	}

	public static class EDSCCoordSphere {

		private float radius;

		private float[] origin;

		public EDSCCoordSphere() {
		};

		public EDSCCoordSphere(float radius, float origX, float origY, float origZ) {
			this.radius = radius;
			origin = new float[] { origX, origY, origZ };
		}

		public float getRadius() {
			return radius;
		}

		public void setRadius(float radius) {
			this.radius = radius;
		}

		public float[] getOrigin() {
			return origin;
		}

		public void setOrigin(float[] origin) {
			this.origin = origin;
		}

	}

	public static class FilterException extends Exception {

		/**
		 * Generated Serial Version UID
		 */
		private static final long serialVersionUID = -7018592798117370507L;

		public FilterException() {
			super();
		}

		public FilterException(String message) {
			super(message);
		}

		public FilterException(Throwable t) {
			super(t);
		}

		public FilterException(String message, Throwable t) {
			super(message, t);
		}

	}

}
