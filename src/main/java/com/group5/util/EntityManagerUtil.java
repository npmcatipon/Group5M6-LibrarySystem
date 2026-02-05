package com.group5.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerUtil {
	
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
	
	public static EntityManager createEntityManager() {
		return emf.createEntityManager();
	}
	
	public static boolean isOpen(EntityManager em) {
		return em != null && em.isOpen();
	}
	
	public static void close(EntityManager em) {
		if (isOpen(em)) {
			em.close();
		}
	}
	
	public static void shutdown() {
		if (emf.isOpen()) {
			emf.close();
		}
	}

}
