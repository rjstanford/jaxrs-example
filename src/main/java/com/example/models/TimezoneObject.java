package com.example.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TimezoneObject {

		private String timezone;
		private int useless;
		public String getTimezone() {
			return timezone;
		}
		public void setTimezone(String timezone) {
			this.timezone = timezone;
		}
		public int getUseless() {
			return useless;
		}
		public void setUseless(int useless) {
			this.useless = useless;
		}
	
		
		
}
