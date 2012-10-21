package br.usp.ime.academicdevoir.migracao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory oldSessionFactory = buildOldSessionFactory();
	private static final SessionFactory newSessionFactory = buildNewSessionFactory();
	
	public static SessionFactory getOldSessionFactory() {
		return oldSessionFactory;
	}
	
	public static SessionFactory getNewSessionFactory() {
		return newSessionFactory;
	}
	
	private static SessionFactory buildOldSessionFactory() {
		return buildSessionFactory("hibernate_old.cfg.xml");
	}
	
	private static SessionFactory buildNewSessionFactory() {
		return buildSessionFactory("hibernate.cfg.xml");
	}
	
	private static SessionFactory buildSessionFactory(String cfgFile) {
		try {
			Configuration cfg = new Configuration();
			cfg.configure(cfgFile);
			return cfg.buildSessionFactory();
		} catch (Throwable e) {
			System.err.println("Criação inicial do objeto SessionFactory falhou: " + e);
			throw new ExceptionInInitializerError(e);
		}
	}
}
