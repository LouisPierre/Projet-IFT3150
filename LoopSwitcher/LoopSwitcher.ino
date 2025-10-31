  int loopState;

  enum buttonPin {
    BUTTON_1 = 14,
    BUTTON_2 = 15,
    BUTTON_3 = 16
  };

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

  bool buttonState [3] = {LOW, LOW, LOW};
  bool lastButtonState [3] = {LOW, LOW, LOW};
  bool validButtonPress [3] = {true, true, true};

//--------------------------SETUP-----------------------------

void setup() {
  for(int i=RELAY_1; i<=RELAY_8; i++){
    pinMode(i, OUTPUT);
  }

  for(int i=BUTTON_1; i<=BUTTON_3; i++){
    pinMode(i, INPUT);
  }

  loopState = BYPASS;
  
}

//-------------------------MAIN LOOP--------------------------

void loop() {

  buttonState[1] = digitalRead(BUTTON_2);

  if(buttonState[1] == LOW && !validButtonPress[1]){
    validButtonPress[1] = true;
  }

  if(buttonState[1] == HIGH && lastButtonState[1] == LOW && validButtonPress[1]){
    loopState = (loopState + 1) % 5;  // cycles through states

    changeState(loopState);

    validButtonPress[1] = false;
  }

  lastButtonState[1] = buttonState[1];

}

void changeState(int state){

  switch (state) {
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
