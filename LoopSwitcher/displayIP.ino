#include <Wire.h>
#include <LiquidCrystal_I2C.h>
#include <WiFi.h>

LiquidCrystal_I2C lcd(0x27, 16, 2);

void setup() {
  lcd.init();
  lcd.backlight();
  lcd.setCursor(0, 0);
  lcd.print("LattePanda IOTA");
  lcd.setCursor(0, 1);
  lcd.print("IP: ");
  lcd.print(getLocalIP());
}

void loop() {
}

String getLocalIP() {
  return "192.168.137.1";
}
