package org.codersparks.elite.systemlogger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.input.TailerListenerAdapter;
import org.codersparks.edsc.model.EDSCFilter.FilterException;
import org.codersparks.edsc.model.EDSCSystem;
import org.codersparks.edscclient.EDSCClient;
import org.codersparks.elitelog.client.EliteLogClient;
import org.codersparks.elitelog.model.CurrentSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemLogFileTailerListener extends TailerListenerAdapter {

	private static Logger logger = LoggerFactory
			.getLogger(SystemLogFileTailerListener.class);

	private static final Pattern systemRegex = Pattern
			.compile(".*System:[^\\(]*\\(([^\\)]*).*");
	private static final int DEFAULT_SPHERE_RADIUS = 50;

	private String systemName = null;
	private final EDSCClient edscClient;
	private final EliteLogClient eliteLogClient;
	private final String commanderName;
	private final int sphereRadius;

	public SystemLogFileTailerListener(EDSCClient edscClient, EliteLogClient eliteLogClient, String commanderName, int sphereRadius) {
		this.edscClient = edscClient;
		this.commanderName = commanderName;
		this.eliteLogClient = eliteLogClient;
		this.sphereRadius = sphereRadius;
	}
	
	public SystemLogFileTailerListener(EDSCClient edscClient, EliteLogClient eliteLogClient, String commanderName) {
		this(edscClient, eliteLogClient, commanderName, DEFAULT_SPHERE_RADIUS);
	}
	@Override
	public void fileNotFound() {
		logger.error("System Log File not found");
		super.fileNotFound();
	}

	@Override
	public void fileRotated() {
		logger.info("System Log File rotated");
	}

	@Override
	public void handle(String line) {

		logger.debug("Reading System Log line: " + line);

		Matcher m = systemRegex.matcher(line);
		if (m.matches()) {
			String foundSystemName = m.group(1);

			if (foundSystemName == null || foundSystemName.length() < 1) {
				logger.error("Found system name is null or empty: '"
						+ foundSystemName + "'");

			} else {

				if (!foundSystemName.equals(this.systemName)) {
					logger.info("Found *new* system: " + foundSystemName);
					this.systemName = foundSystemName;

					try {
						EDSCSystem edscSystem = edscClient
								.getSystemsByName(foundSystemName);

						logger.debug("System found in EDSC: " + edscSystem);

						float[] coords = edscSystem.getCoord();

						List<String> systemsOfInterest = edscClient
								.getSystemsWithinSphere(sphereRadius,
										coords[0], coords[1], coords[2]);
						
						logger.debug("Number of systems found for closest systems: " + systemsOfInterest.size());
						
						CurrentSystem currentSystem = new CurrentSystem();
						currentSystem.setCommanderName(commanderName);
						currentSystem.setSystemName(foundSystemName);
						currentSystem.setClosestSystems(systemsOfInterest);
						eliteLogClient.putCurrentSystem(currentSystem);
					} catch (FilterException e) {
						logger.error("Cannot get system from edscClient", e);
					} catch (Exception e) {
						logger.error("Exception caught: " + e.getLocalizedMessage(), e);
					}
				} else {
					logger.debug("Previously found System: " + foundSystemName);
				}
			}
		} else {
			logger.debug("No match found");
		}

	}

	@Override
	public void handle(Exception ex) {
		logger.error("Exception caught: ", ex);

	}

}
