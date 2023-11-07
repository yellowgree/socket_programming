# socket_programming
컴퓨터 네트워크 및 실습 시간에 진행한 계산기 소켓 프로그래밍 실습 코드

•	프로토콜 : CalcProtocol

•	프로토콜 용도:


      o	클라이언트와 서버 간의 산술 연산 요청 및 응답 처리

  
      o	클라이언트가 서버로 산술 연산 요청을 보내고, 서버가 연산을 수행하고 그 결과를 클라이언트에게 반환


  
•	프로토콜 작동 방식


      o	클라이언트가 연산 요청 메시지를 서버로 전송


  
      o	서버는 요청 메시지를 해석하고 연산을 수행


  
      o	연산 결과가 정상적으로 반환될 경우 "Answer" 메시지와 결과를 클라이언트에게 반환



  
      o	연산 중 에러가 발생할 경우 "Error" 메시지와 에러 유형을 클라이언트에게 반환
  

![image](https://github.com/yellowgree/socket_programming/assets/112637518/2cba4c09-8be4-4470-a40b-c2a09b205066)



•	프로토콜 요소 




•	연산 요청 메시지 (Client to Server):


      o	형식: <Operation> <Operand1> <Operand2>

    
      o	종류(4가지): add, sub, mul, div

  
      o	예제: add 5 3

  
      o	설명: 클라이언트는 서버에게 원하는 산술 연산을 요청하며, 연산 종류와 피연산자를 전송

  
•	연산 결과 메시지 (Server to Client):

  
      o	형식: Answer: <Result> 또는 Error: <ErrorType> - <meaning>

  
      o	예제 (성공): Answer: 8

  
      o	예제 (에러): Error: division by zero - Division by zero is not allowed

  
      o	설명: 서버는 연산 결과를 성공적으로 계산한 경우 "Answer"와 결과를 반환하며, 연산 중 발생한 에러의 경우 "Error"와 에러 유형 및 의미를 반환



  
•	프로토콜 에러 유형:
      o	error: invalid operation - Invalid operation requested (잘못된 연산 요청)

  
      o	error: too few operands - Insufficient operands for the operation (부족한 피연산자)

  
      o	error: too many operands - Too many operands for the operation (너무 많은 피연산자)

  
      o	error: division by zero - Division by zero is not allowed (나누기 연산 오류)


  

•	종료 프로토콜 : bye 
