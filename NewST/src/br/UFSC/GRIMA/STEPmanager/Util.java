package br.UFSC.GRIMA.STEPmanager;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jsdai.lang.ASdaiModel;
import jsdai.lang.SdaiException;
import jsdai.lang.SdaiModel;
import jsdai.lang.SdaiRepository;
import jsdai.lang.SdaiSession;
import jsdai.lang.SdaiTransaction;

public class Util {
	private SdaiRepository repository;
	private SdaiTransaction transaction;
	private SdaiSession session;
	private SdaiModel model;

	public static String getUserPath() {
		String path = null;

		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream("res/Util.properties"));
			path = properties.getProperty("path");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	public ASdaiModel openFile21(String sourcePath) throws SdaiException {
		java.util.Properties prop = new java.util.Properties();
		prop.setProperty("repositories", "C:\\repositories.tmp");
		SdaiSession.setSessionProperties(prop);
		session = SdaiSession.openSession();

		transaction = session.startTransactionReadWriteAccess();

		repository = session.importClearTextEncoding("ImportRepository",
				sourcePath, null);

		if (!repository.isActive()) {
			repository.openRepository();
		}
		return repository.getModels();
	}
	public ASdaiModel openFileXML(String sourcePath) throws IOException, SdaiException 
	{
		
			FileInputStream fileStream = new FileInputStream(sourcePath);
			InputStream fromStream = new BufferedInputStream(fileStream);
			
			try {
				java.util.Properties prop = new java.util.Properties();
				prop.setProperty("repositories", "C:\\repositories.tmp");
				SdaiSession.setSessionProperties(prop);
				session = SdaiSession.openSession();
				transaction = session.startTransactionReadWriteAccess();
				repository = session.createRepository("", null);
				repository.openRepository();
				
				repository.importXml(fromStream);
			}
			finally {
			fromStream.close();
			}
			if (!repository.isActive()) {
				repository.openRepository();
			}
			return repository.getModels();
	}
	public void closeProject() throws SdaiException {
//		session.getActiveTransaction().endTransactionAccessCommit();
		transaction.endTransactionAccessCommit();
		repository.closeRepository();
		repository.deleteRepository();
		session.closeSession();
	}

	public SdaiRepository getRepository() {
		return repository;
	}

	public SdaiTransaction getTransaction() {
		return transaction;
	}

	public SdaiSession getSession() {
		return session;
	}

	public SdaiModel getModel() {
		return model;
	}

}
