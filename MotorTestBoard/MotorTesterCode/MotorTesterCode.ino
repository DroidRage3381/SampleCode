#include <Wire.h>
#include <Servo.h>

Servo motor1;
Servo motor2;

const int pot1 = 0;
const int pot2 = 3;
const int upperOff = 550;
const int lowerOff = 450;
const int MOTOR1 = 11;
const int MOTOR2 = 10;
const int SENSOR = 7;//digital pin 5
int led = 13;//in-built LED on the board

void setup() {
  int pot1Value;
  int pot2Value;

  Serial.begin(9600);
  digitalWrite(led, HIGH);
  pinMode(led, OUTPUT);
  motor1.attach(MOTOR1);
  motor2.attach(MOTOR2);
  motor1.writeMicroseconds(1500);
  motor2.writeMicroseconds(1500);
  bool readyRun = false;

  while (!readyRun) {
    pot1Value = analogRead(pot1);
    pot2Value = analogRead(pot2);

    Serial.print(pot1Value);
    Serial.write(" : ");
    Serial.print(pot2Value);
    Serial.write("\r\n");

    if (inDeadbandRange(pot1Value) && inDeadbandRange(pot2Value)) {
      readyRun = true;
    }
  }
}
 
void loop() {
  int pot1Value;
  int pot2Value;
  int sensorValue;

  digitalWrite(led, LOW);
  pot1Value = analogRead(pot1);
  pot2Value = analogRead(pot2);
  sensorValue = digitalRead(SENSOR);
  setMotorSpeed(motor1, pot1Value);

  Serial.write("    /   ");

  setMotorSpeed(motor2, pot2Value);
  
  readSensor(sensorValue);
  
  Serial.write("\r\n");
}

bool outOfDeadbandRange(int value) {
  return (value < lowerOff || value > upperOff);
}

bool inDeadbandRange(int value) {
  return (value > lowerOff && value < upperOff);
}

void printDebugInfo(int potVal, int pwmVal, bool enabled) {
  Serial.print(potVal);

  if (enabled) {
    Serial.write(" + ");
  } else {
    Serial.write(" - ");
  }

  Serial.print(pwmVal);
}

void setMotorSpeed(Servo motor, int potValue) {

  if (outOfDeadbandRange(potValue)) {
    int pwmVal = map(potValue, 0, 1024, 1000, 2000);
    motor.writeMicroseconds(pwmVal);
    printDebugInfo(potValue, pwmVal, true);
  } else {
    motor.writeMicroseconds(1500);
    printDebugInfo(potValue, 1500, false);
  }

}

int readSensor(int sensor) {
  int sensorRead = pulseIn(sensor, LOW) * 1000 * 1000 * 60;
  Serial.write("Sensor RPM = ");
  Serial.print(sensorRead);
  return sensorRead;
}
