package me.kotsu;

import me.kotsu.config.AppConfiguration;
import me.kotsu.config.prod.AppConfigurationProd;

public class Main {
	public static void main(String args[]) {
		
		try {
			AppConfiguration config = new AppConfigurationProd();
			MainService main = new MainService(config);
			System.out.println(main.start());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
