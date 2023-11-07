package calculator;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class CalcServerEx {

    public static String processRequest(String request) {
        StringTokenizer st = new StringTokenizer(request, " ");
        int tokenCount = st.countTokens();

        //에러 프로토콜의 유형 정의
        if (tokenCount < 3) {
            return createError("too few operands", "Insufficient operands for the operation");
        } else if (tokenCount > 3) {
            return createError("too many operands", "Too many operands for the operation");
        }

        String operation = st.nextToken();
        int op1 = Integer.parseInt(st.nextToken());
        int op2 = Integer.parseInt(st.nextToken());
        double result = 0;  //나눗셈 연산을 위해 결과는 double 실수형으로 선언

        switch (operation) {
            case "add":   //더하기 프로토콜 연산
                result = op1 + op2;
                break;
            case "sub":   //빼기 프로토콜 연산
                result = op1 - op2;
                break;
            case "mul":   //곱하기 프로토콜 연산
                result = op1 * op2;
                break;
            case "div":   //나누기 프로토콜 연산
                if (op2 == 0) {
                	//에러 프로토콜의 유형 정의
                    return createError("division by zero", "Division by zero is not allowed");
                } else {
                    result = (double) op1 / op2;
                   }
                break;
            default:
            	//에러 프로토콜의 유형 정의
                return createError("invalid operation", "Invalid operation requested");
        }
        //최종 정답 프로토콜
        return "Answer: " + result;
    }

    //최종 에러 프로토콜
    private static String createError(String errorType, String errorMessage) {
        return "error: " + errorType + " - " + errorMessage;
    }

    public static void main(String[] args) {
        ServerSocket listener = null;

        try {
            listener = new ServerSocket(9999);
            System.out.println("연결중.....");

            while (true) {
                Socket socket = listener.accept();
                System.out.println("연결 성공!");
                Runnable clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (listener != null)
                    listener.close();
            } catch (IOException e) {
                System.out.println("클라이언트와 통신 오류");
            }
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

                while (true) {
                    String inputMessage = in.readLine();
                    if (inputMessage.equalsIgnoreCase("bye")) {
                        System.out.println("클라이언트에서 연결을 종료");
                        break;
                    }
                    System.out.println("클라이언트 요청: " + inputMessage);
                    String response = processRequest(inputMessage);
                    out.write(response + "\n");
                    out.flush();
                }
            } catch (IOException e) {
                System.out.println("클라이언트와 통신 중 오류");
            }
        }
    }
}
