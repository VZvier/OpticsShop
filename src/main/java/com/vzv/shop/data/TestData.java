package com.vzv.shop.data;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class TestData {

	public void insertCountriesToDB() {
		List<String> countries = new LinkedList<>();
		countries.add("China");
		countries.add("Germany");
		countries.add("Italy");
		countries.add("Japan");
		countries.add("Korea");
		countries.add("Poland");
		countries.add("Romania");
		countries.add("Ukraine");
	}

	public static String formatPrice(String price){
		if (!price.contains(",") && !price.contains(".")){
			price = price + ",00";
		} else {
			if (price.contains(".")) {
				price = price.replace(".", ",");
			} else {
				price = (price.indexOf(",") > price.length() - 3)
						? price + StringUtils.repeat("0", price.length() - price.indexOf(",") - 1) : price;
			}
		}
		return price;
	}

}