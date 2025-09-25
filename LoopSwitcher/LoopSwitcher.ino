  int loopState = 0;

  enum relayPin {  // Maps the output pins of the arduino
    RELAY_1 = 2,   // to the inputs of the relay board
    RELAY_2 = 3,
    RELAY_3 = 4,
    RELAY_4 = 5,
    RELAY_5 = 6,
    RELAY_6 = 7,
    RELAY_7 = 8,
    RELAY_8 = 9
  };

  enum state{
    BYPASS,
    LOOP_A,
    LOOP_B,
    LOOP_A_B,
    LOOP_B_A
  };

void setup() {
  
  pinMode(2, OUTPUT);
  pinMode(3, OUTPUT);
  pinMode(4, OUTPUT);
  pinMode(5, OUTPUT);
  pinMode(6, OUTPUT);
  pinMode(7, OUTPUT);
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);

  loopState = LOOP_B;
  
}

void loop() {
  switch (loopState) {
    case BYPASS:  
      digitalWrite(RELAY_1, LOW);
      digitalWrite(RELAY_8, LOW);
    break;
    case LOOP_A:  
      digitalWrite(RELAY_1, HIGH);
      digitalWrite(RELAY_2, LOW);
      digitalWrite(RELAY_3, LOW);
      digitalWrite(RELAY_5, LOW);
      digitalWrite(RELAY_6, HIGH);
      digitalWrite(RELAY_8, LOW);
    break;
    case LOOP_B:  
      digitalWrite(RELAY_1, HIGH);
      digitalWrite(RELAY_2, HIGH);
      digitalWrite(RELAY_4, LOW);
      digitalWrite(RELAY_5, HIGH);
      digitalWrite(RELAY_6, HIGH);
      digitalWrite(RELAY_7, LOW);
    break;
    case LOOP_A_B:  // Loop A and then Loop B
      digitalWrite(RELAY_1, HIGH);
      digitalWrite(RELAY_2, LOW);
      digitalWrite(RELAY_3, HIGH);
      digitalWrite(RELAY_4, HIGH);
      digitalWrite(RELAY_5, HIGH);
      digitalWrite(RELAY_6, HIGH);
      digitalWrite(RELAY_7, LOW);
      digitalWrite(RELAY_8, LOW);
    break;
    case LOOP_B_A:  // Loop B and then Loop A
      digitalWrite(RELAY_1, HIGH);
      digitalWrite(RELAY_2, HIGH);
      digitalWrite(RELAY_3, LOW);
      digitalWrite(RELAY_4, LOW);
      digitalWrite(RELAY_5, LOW);
      digitalWrite(RELAY_6, HIGH);
      digitalWrite(RELAY_7, HIGH);
      digitalWrite(RELAY_8, HIGH);
    break;
  }

}
