package org.codersparks.eddn.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.codersparks.eddn.model.EDDNCommodityDataMessage;
import org.codersparks.elitelog.client.EliteLogClient;
import org.codersparks.elitelog.utils.CommodityUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EDDNMessageConsumer implements Runnable {

	private static final Logger logger = LoggerFactory
			.getLogger(EDDNMessageConsumer.class);

	private DateTime currentSystemCheckTimestamp = null;
	private BlockingQueue<EDDNCommodityDataMessage> queue;
	private int checkPeriodMinutes;
	private EliteLogClient eliteLogClient;

	public EDDNMessageConsumer(BlockingQueue<EDDNCommodityDataMessage> queue,
			EliteLogClient eliteLogClient, int checkPeriodMinutes) {
		this.queue = queue;
		this.checkPeriodMinutes = checkPeriodMinutes;
		this.eliteLogClient = eliteLogClient;
	}

	@Override
	public void run() {

		List<String> systemsOfInterest = null;

		List<String> excludedSystems = new ArrayList<String>();

		int interestedCount = 0;

		while (true) {

			DateTime updateCutoff = new DateTime()
					.minusMinutes(checkPeriodMinutes);

			logger.debug("Update cutoff: " + updateCutoff);

			if (systemsOfInterest == null
					|| currentSystemCheckTimestamp.isBefore(updateCutoff)) {

				logger.info("Updating list of systems of interest");
				try {
					systemsOfInterest = eliteLogClient.getSystemsOfInterest();
					logger.debug("Systems of Interest: " + systemsOfInterest);
					currentSystemCheckTimestamp = new DateTime();
					logger.debug("TimeStamp: " + currentSystemCheckTimestamp);

				} catch (Exception e) {
					logger.error(
							"Exception caught: " + e.getLocalizedMessage(), e);
				}

				logger.debug("Clearing excluded list");
				excludedSystems.clear();

			}

			EDDNCommodityDataMessage message;
			try {
				logger.debug("Attempting to read message from queue...");
				message = queue.poll(checkPeriodMinutes, TimeUnit.MINUTES);
				if (message == null) {
					logger.debug("Queue polling timed out...");
				} else {
					logger.debug("Message: " + message.toString());

					String systemName = message.getSystemName();
					boolean interested = false;
					if (! excludedSystems.contains(systemName)) {
						for (String s : systemsOfInterest) {
							if (systemName.equalsIgnoreCase(s)) {
								interested = true;
								++interestedCount;
								break;
							}
						}
					}

					if (interested) {
						logger.info("Interested in commodity, loading to elitelog");
						try {
							eliteLogClient.postCommodityData(CommodityUtils.convertEDDNCommodity(message));
						} catch (Exception e) {
							logger.error("Exception caught when posting data to elitelog", e);
						}
					} else {
						logger.debug("Not interested in commodity");
						excludedSystems.add(systemName);
					}
					logger.info("Interested count: " + interestedCount);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("InterruptedException caught", e);
			}

		}
	}

}
