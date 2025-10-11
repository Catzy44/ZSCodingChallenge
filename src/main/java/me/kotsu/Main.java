package me.kotsu;

import me.kotsu.config.AppConfiguration;
import me.kotsu.config.prod.AppConfigurationProd;

public class Main {
	public static void main(String args[]) {
		
		AppConfiguration config = new AppConfigurationProd();
		MainService main = new MainService(config);
		System.out.println(main.start());
		
	}
}
