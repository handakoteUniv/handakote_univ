void setup(){
  pinMode(4, OUTPUT);
  digitalWrite(4, LOW);
  pinMode(5, OUTPUT);
  digitalWrite(5, LOW);
  pinMode(6, OUTPUT);
  digitalWrite(6, LOW);
  pinMode(7, OUTPUT);
  digitalWrite(7, LOW);
  
  Serial.begin(9600);
}

void loop(){
  static char buffer[80];
  if (readline(Serial.read(), buffer, 80) > 0) {
    switch (buffer[0]){
      case 'a':
        digitalWrite(4, LOW);
        break;
        
      case 'A':
        digitalWrite(4, HIGH);
        break;
        
      case 'b':
        digitalWrite(5, LOW);
        break;
        
      case 'B':
        digitalWrite(5, HIGH);
        break;
      
      case 'c':
        digitalWrite(6, LOW);
        break;
        
      case 'C':
        digitalWrite(6, HIGH);
        break;
    
      case 'd':
        digitalWrite(7, LOW);
        break;
        
      case 'D':
        digitalWrite(7, HIGH);
        break;
    }
  }
}

int readline(int readch, char *buffer, int len){
  static int pos = 0;
  int rpos;
  if (readch > 0) {
    switch (readch) {
      case '\n':
        rpos = pos;
        pos = 0;
        return rpos;
      default:
        if (pos < len-1) {
          buffer[pos++] = readch;
          buffer[pos] = 0;
      }
    }
  }
  return -1;
}
