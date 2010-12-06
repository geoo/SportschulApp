package de.sportschulApp.client;

import java.util.Date;

import com.google.gwt.user.client.Cookies;

public class CookieManager {

	public static void deleteAllCookies() {
		Cookies.removeCookie("SportschuleUserName");
		Cookies.removeCookie("SportschuleUserRight");
	}

	public static String getUsername() {
		return Cookies.getCookie("SportschuleUserName");
	}

	public static void setLanguageCookie(String language){
		final long DURATION = 1000 * 60 * 60 * 24 * 360;
		Date expires = new Date(System.currentTimeMillis() + DURATION);
		Cookies.setCookie("SportschuleLanguage", language, expires);
	}

	/*
	 * Erstellt den Cookie f�r den User
	 * 
	 * */
	public static void setUserCookie(String username, String userRight){
		final long DURATION = 1000 * 60 * 60 * 24 * 1;
		Date expires = new Date(System.currentTimeMillis() + DURATION);

		Cookies.removeCookie("SportschuleUserName");
		Cookies.removeCookie("SportschuleUserRight");
		Cookies.setCookie("SportschuleUserName", username, expires);
		Cookies.setCookie("SportschuleUserRight", userRight, expires);
	}

}
