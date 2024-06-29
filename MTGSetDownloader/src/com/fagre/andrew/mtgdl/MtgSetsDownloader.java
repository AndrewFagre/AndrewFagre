package com.fagre.andrew.mtgdl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import ssl.TrustedCertficatesStore;

public class MtgSetsDownloader {
	static String version ="2.0" ;
	static Logger myLogger = Logger.getLogger("logging.properties");
	static Handler handler;
	static void initLogging() throws SecurityException, IOException{
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tr] %3$s %4$s:  %5$s %n");
		                                     
		handler = new FileHandler("mtg_wtf_sets.log", 10000000, 100);
		SimpleFormatter sf = new SimpleFormatter();		
		handler.setFormatter(sf);
		myLogger.addHandler(handler);
		myLogger.info("Attempting to setup logging.");
	}
	
	public static void main(String[] args) {
		try {
			initLogging();
		} catch (SecurityException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		myLogger.info("Logging initialized, welcome to you're doom (Frisky Dingo)." + version);
		myLogger.info("Line Two");
		String siteForCert = "https://mtg.wtf";
	//	String urlForDownload = "https://docs.gimp.org/2.10/en/introduction.html";
		String[] links = new String[] {
				"https://mtg.wtf/deck/afc/aura-of-courage.html",
				"https://mtg.wtf/deck/afc/draconic-rage.html",
				"https://mtg.wtf/deck/afc/dungeons-of-death.html",
				"https://mtg.wtf/deck/afc/planar-portal.html",
				"https://mtg.wtf/deck/mic/coven-counters.html",
				"https://mtg.wtf/deck/mic/undead-unleashed.html"

		};

		String save = null;
		File saveToDir = new File("D:\\WebDownloads\\mtg");
		try {
			new TrustedCertficatesStore("D:\\WebDownloads", siteForCert);
		} catch (Exception e1) {
			myLogger.info("An Exception Occured"+e1.getMessage());
			e1.printStackTrace();
		}
		// start for here
		String linkToGrab = "";
		for (int i = 0; i < links.length; i++) {
			try {
				String dataString = links[i];
				//linkToGrab = urlForDownload.concat(dataString);
				linkToGrab = links[i];
				myLogger.info(linkToGrab);

				save = dataString.substring(dataString.lastIndexOf("/") + 1, dataString.length());
				myLogger.info("Save is : " + save);
				String file = linkToGrab;
				URL url = new URL(file);
				InputStream in = url.openStream();
				myLogger.info("Attempting to replace any ? in the file name.");
				save = save.replace("?", "");
				myLogger.info("Save is now : " + save);
				FileOutputStream fos = new FileOutputStream(
						new File(saveToDir.getAbsolutePath().concat("/").concat(save)));
				int length = -1;
				byte[] buffer = new byte[1024];

				while ((length = in.read(buffer)) > -1) {
					fos.write(buffer, 0, length);
				} // end while
				fos.close();
				in.close();
				myLogger.info("file is downloaded");
			} catch (IOException e) {
				myLogger.info("Link failed : " + linkToGrab);
				myLogger.info("Save is : " + save);
				myLogger.info(e.getMessage());
				e.printStackTrace();
				continue;
			} catch (Exception e) {
				myLogger.info("Link failed : " + linkToGrab);
				myLogger.info("Save is : " + save);
				myLogger.info(e.getMessage());
				e.printStackTrace();
				continue;
			} // end try catch
		} // end for here

		return;
	}// end main
}

