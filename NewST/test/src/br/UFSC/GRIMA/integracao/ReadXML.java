package br.UFSC.GRIMA.integracao;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import jsdai.lang.SdaiException;
import jsdai.lang.SdaiRepository;
import jsdai.lang.SdaiSession;
import jsdai.lang.SdaiTransaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.STEPmanager.Util;

public class ReadXML {
	SdaiSession session;
	SdaiTransaction transaction;
	SdaiRepository repository;
	Util util;
	@Before
	public void startObjects()
	{
		java.util.Properties prop = new java.util.Properties();
		prop.setProperty("repositories", "C:\\repositories.tmp");
		try {
			SdaiSession.setSessionProperties(prop);
			session = SdaiSession.openSession();
			transaction = session.startTransactionReadWriteAccess();
			repository = session.createRepository("repo", null);
			repository.openRepository();
			System.out.println("SESS = " +session);
			System.out.println("REP = " +repository);
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testImportXML() throws SdaiException, IOException {
		FileInputStream fileStream = new FileInputStream("BOX.xml");
		InputStream fromStream = new BufferedInputStream(fileStream);
		try {
			repository.importXml(fromStream);
		} finally {
			fromStream.close();
		}
	}
	@After
	public void closeProject() throws SdaiException {
		transaction.endTransactionAccessCommit();
		repository.closeRepository();
		repository.deleteRepository();
		session.closeSession();
	}
}
